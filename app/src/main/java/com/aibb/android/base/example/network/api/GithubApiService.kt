package com.aibb.android.base.example.network.api

import com.aibb.android.base.example.network.pojo.GithubRepos
import com.aibb.android.base.networkservice.RetrofitFactory
import com.aibb.android.base.networkservice.RxApi
import io.reactivex.Observable

/**
 * Copyright:   Copyright (c)  All rights reserved.<br>
 * Author:      aibingbing <br>
 * Date:        2020/8/29 <br>
 * Desc:        <br>
 */
object GithubApiService {
    val githubModel = RetrofitFactory.create("https://api.github.com", GithubApi::class.java)

    fun getGithubRespo(): Observable<List<GithubRepos>> {
        return RxApi.createApi(githubModel.getUserGithubRepos())
    }
}