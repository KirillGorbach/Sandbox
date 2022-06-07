package com.sandbox.fragments.room

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RoomViewModel: ViewModel() {
    val users: LiveData<List<UserDB>>
        get() =_users
    private val _users = MutableLiveData<List<UserDB>>()

    private var userDB: UsersDatabase

    init {
        try {
            userDB = App.instance?.getDatabase()!!
        } catch (e: Exception) {
            Log.e("NullPointer!", "Can't access the DAO class!")
            throw e
        }
        getAllUsers()
    }

    fun addUser(name: String, age: Int){
        userDB.getUsersDao().insertUser(UserDB(name = name, age = age))
        getAllUsers()
    }

    private fun getAllUsers() {
        _users.value = userDB.getUsersDao().getAllUsers()
    }

    fun deleteAllUsers() {
        userDB.getUsersDao().deleteAllUsers()
        getAllUsers()
    }

    override fun onCleared() {
        super.onCleared()
        userDB.close()
    }

}