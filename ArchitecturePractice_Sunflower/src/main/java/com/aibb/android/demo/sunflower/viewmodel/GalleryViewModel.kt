package com.aibb.android.demo.sunflower.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.aibb.android.demo.sunflower.data.pojo.UnsplashPhoto
import com.aibb.android.demo.sunflower.data.repository.UnsplashRepository
import kotlinx.coroutines.flow.Flow

class GalleryViewModel internal constructor(private val repository: UnsplashRepository) :
    ViewModel() {
    private var currentQueryValue: String? = null
    private var currentSearchResult: Flow<PagingData<UnsplashPhoto>>? = null

    fun searchPictures(queryString: String): Flow<PagingData<UnsplashPhoto>> {
        currentQueryValue = queryString
        val newResult: Flow<PagingData<UnsplashPhoto>> =
            repository.getSearchResultStream(queryString).cachedIn(viewModelScope)
        currentSearchResult = newResult
        return newResult
    }
}