package com.sandbox.fragments

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.sandbox.R
import com.sandbox.databinding.FragmentAsyncBinding
import com.squareup.picasso.Picasso
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.io.InputStream
import java.net.URL


class AsyncFragment : Fragment() {

    private var _binding: FragmentAsyncBinding? = null
    private val binding get() = _binding!!
    companion object {
        const val IMG_URL =
            "https://ezhepedia.ru/images/thumb/c/cc/%D0%A4%D0%BE%D1%82%D0%BE_%D0%81%D0%B6%D0%B8%D0%BA%D0%B0_%D1%81_%D0%90%D1%80%D0%B1%D1%83%D0%B7%D0%BE%D0%BC.jpg/1152px-%D0%A4%D0%BE%D1%82%D0%BE_%D0%81%D0%B6%D0%B8%D0%BA%D0%B0_%D1%81_%D0%90%D1%80%D0%B1%D1%83%D0%B7%D0%BE%D0%BC.jpg"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAsyncBinding.inflate(inflater, container, false)

        with(binding){

            btnAsynctask.setOnClickListener { loadAsyncTask() }
            btnClear.setOnClickListener { image.setImageResource(R.drawable.no_img) }
            btnHandler.setOnClickListener { loadHandler() }
            btnEventbus.setOnClickListener { loadEventBus() }
            btnGlide.setOnClickListener { loadGlide() }
            btnPicasso.setOnClickListener { loadPicasso() }
            btnRx.setOnClickListener { loadRx() }
            

        }

        return binding.root
    }

    private fun loadRx(){
        Observable.create<Bitmap?> { observableEmitter ->

            val url: String = IMG_URL
            var bitmap: Bitmap? = null
            try {
                val inputStream: InputStream = URL(url).openStream()
                bitmap = BitmapFactory.decodeStream(inputStream)
            }catch (exception: Exception){
                observableEmitter.onError(exception)
            }
            bitmap?.let { observableEmitter.onNext(it) }

        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{
                binding.image.setImageBitmap(it)
            }


//        Thread{
//            val url: String = IMG_URL
//            var bitmap: Bitmap? = null
//            try {
//                val inputStream: InputStream = URL(url).openStream()
//                bitmap = BitmapFactory.decodeStream(inputStream)
//            }catch (exception: Exception){
//                exception.printStackTrace()
//            }
//            bitmap?.let {
//                val handler = Handler(Looper.getMainLooper())
//                handler.post { binding.image.setImageBitmap(it) }
//            }
//        }.start()
    }

    private fun loadPicasso(){
        Picasso
            .with(context)
            .load(IMG_URL)
            .into(binding.image)
    }

    private fun loadGlide(){
        Glide
            .with(this)
            .load(IMG_URL)
            .into(binding.image)
    }

    private fun loadEventBus(){
        Thread{
            val url: String = IMG_URL
            var bitmap: Bitmap? = null
            try {
                val inputStream: InputStream = URL(url).openStream()
                bitmap = BitmapFactory.decodeStream(inputStream)
            }catch (exception: Exception){
                exception.printStackTrace()
            }
            bitmap?.let {
                EventBus.getDefault().post(MsgEvent(bitmap))
            }
        }.start()
    }
    data class MsgEvent(var img: Bitmap)
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onImgDownloaded(event: MsgEvent){
        binding.image.setImageBitmap(event.img)
    }
    override fun onStart() { super.onStart(); EventBus.getDefault().register(this) }
    override fun onStop() { super.onStop(); EventBus.getDefault().unregister(this) }

    private fun loadHandler(){

        runBlocking {
            CoroutineScope(Dispatchers.Default).launch {
                val url: String = IMG_URL
                var bitmap: Bitmap? = null
                try {
                    val inputStream: InputStream = URL(url).openStream()
                    bitmap = BitmapFactory.decodeStream(inputStream)
                }catch (exception: Exception){
                    exception.printStackTrace()
                }
                bitmap?.let {
                    val handler = Handler(Looper.getMainLooper())
                    handler.post { binding.image.setImageBitmap(it) }
                }
            }
        }
//        Thread{
//            val url: String = IMG_URL
//            var bitmap: Bitmap? = null
//            try {
//                val inputStream: InputStream = URL(url).openStream()
//                bitmap = BitmapFactory.decodeStream(inputStream)
//            }catch (exception: Exception){
//                exception.printStackTrace()
//            }
//            bitmap?.let {
//                val handler = Handler(Looper.getMainLooper())
//                handler.post { binding.image.setImageBitmap(it) }
//            }
//        }.start()
    }

    private fun loadAsyncTask(){
        MyAsyncTask {
            binding.image.setImageBitmap(it!!)
        }.execute(IMG_URL)
    }
    private class MyAsyncTask(private val onLoad: (Bitmap?) -> Unit): AsyncTask<String, Void, Bitmap?>(){
        override fun doInBackground(vararg p0: String?): Bitmap? {
            val url: String? = p0[0]
            var bitmap: Bitmap? = null
            try {
                val inputStream: InputStream = URL(url).openStream()
                bitmap = BitmapFactory.decodeStream(inputStream)
            }catch (exception: Exception){
                exception.printStackTrace()
            }
            return bitmap
        }

        override fun onPostExecute(result: Bitmap?) {
            onLoad(result)
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}