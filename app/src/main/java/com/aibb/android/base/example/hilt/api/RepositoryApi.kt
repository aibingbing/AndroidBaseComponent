package com.aibb.android.base.example.hilt.api

import com.aibb.android.base.example.hilt.pojo.Repository
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET

/**
 * Copyright:   Copyright (c)  All rights reserved.<br>
 * Author:      aibingbing <br>
 * Date:        2020/8/29 <br>
 * Desc:        <br>
 */
interface RepositoryApi {
    @GET("users/JakeWharton/repos")
    fun getSquareGithubRepos(): Observable<Response<List<Repository>>>
}