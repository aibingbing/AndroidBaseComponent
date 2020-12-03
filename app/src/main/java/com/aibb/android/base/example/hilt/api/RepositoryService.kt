package com.aibb.android.base.example.hilt.api

import com.aibb.android.base.example.hilt.pojo.Repository
import com.aibb.android.base.networkservice.RetrofitFactory
import com.aibb.android.base.networkservice.RxApi
import io.reactivex.Observable

/**
 * Copyright:   Copyright (c)  All rights reserved.<br>
 * Author:      aibingbing <br>
 * Date:        2020/8/29 <br>
 * Desc:        <br>
 */
object RepositoryService {
    val githubModel = RetrofitFactory.create("https://api.github.com", RepositoryApi::class.java)

    fun getSquareGithubRepository(): Observable<List<Repository>> {
        return RxApi.createApi(githubModel.getSquareGithubRepos())
    }
}