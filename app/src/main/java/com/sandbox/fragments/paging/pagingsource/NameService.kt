package com.sandbox.fragments.paging.pagingsource

import android.util.Log
import com.sandbox.fragments.paging.model.Name

class NameService {
    fun searchNames(pageNumber: Int): List<Name> {
        Log.i("Сервис данных пагинации", "Загружена страница $pageNumber")
        return (pageNumber*100-100..(pageNumber+1)*100-100).map { Name("Item$it") }.toList()
    }
}