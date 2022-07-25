package com.sandbox.fragments.paging

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.sandbox.fragments.paging.model.Name
import com.sandbox.fragments.paging.pagingsource.NamePagingSource
import com.sandbox.fragments.paging.pagingsource.NameService
import kotlinx.coroutines.flow.Flow

class PagingViewModel: ViewModel() {

    var flow: Flow<PagingData<Name>>

    init {
        flow = Pager(
            PagingConfig(pageSize = 10, maxSize = 200)
        ) {
            NamePagingSource(NameService())
        }.flow
            .cachedIn(viewModelScope)
    }

}