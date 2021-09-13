package com.mobile.travelaja.base.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.mobile.travelaja.utility.Utils
import okio.IOException
import opsigo.com.domainlayer.model.ResultList
import retrofit2.HttpException

abstract class PageKeyedPagingSource<T:Any> : PagingSource<Int,T>() {
    companion object {
        const val SIZE = 30
        const val INDEX_KEY = "Index"
        const val SIZE_KEY = "Size"
        private const val STARTING_PAGE_INDEX = 1
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        val page = params.key ?: STARTING_PAGE_INDEX
        return try {
            val data = getResultFromService(page).items
            if (data.isNotEmpty()){
                LoadResult.Page(
                    data = data.toList(),
                    prevKey = null,
                    nextKey = page + 1
                )
            }else {
                LoadResult.Error(Throwable(Utils.EMPTY))
            }

        } catch (e: Throwable) {
            LoadResult.Error(e)
        }catch (e:IOException){
            LoadResult.Error(e)
        }catch (e : HttpException){
            LoadResult.Error(e)
        }
    }

    abstract suspend fun getResultFromService(page : Int): ResultList<T>

    override fun getRefreshKey(state: PagingState<Int, T>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}