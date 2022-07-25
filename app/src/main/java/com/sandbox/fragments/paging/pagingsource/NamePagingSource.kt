package com.sandbox.fragments.paging.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sandbox.fragments.paging.model.Name

class NamePagingSource(
    private val nameService: NameService
) : PagingSource<Int, Name>() {
    override suspend fun load(
        params: LoadParams<Int>
    ): LoadResult<Int, Name> {
        return try {
            // Start refresh at page 1 if undefined.
            val pageNumber = params.key ?: 1
            val response = nameService.searchNames(pageNumber)
            val nextPageNumber = if(pageNumber==3) null else pageNumber+1
            val prevPageNumber = if(pageNumber>1) pageNumber-1 else null
            LoadResult.Page(
                data = response,
                prevKey = prevPageNumber,
                nextKey = nextPageNumber
            )

        } catch (e: Exception) {
            // Handle errors in this block and return LoadResult.Error if it is an
            // expected error (such as a network failure).
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Name>): Int? {
        // Try to find the page key of the closest page to anchorPosition, from
        // either the prevKey or the nextKey, but you need to handle nullability
        // here:
        //  * prevKey == null -> anchorPage is the first page.
        //  * nextKey == null -> anchorPage is the last page.
        //  * both prevKey and nextKey null -> anchorPage is the initial page, so
        //    just return null.
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}
