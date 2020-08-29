package com.aibb.android.base.example.network.api

import com.aibb.android.base.example.network.pojo.GithubRepos
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET

/**
 * Copyright:   Copyright (c)  All rights reserved.<br>
 * Author:      aibingbing <br>
 * Date:        2020/8/29 <br>
 * Desc:        <br>
 */
interface GithubApi {
    @GET("users/JakeWharton/repos")
    fun getUserGithubRepos(): Observable<Response<List<GithubRepos>>>
}