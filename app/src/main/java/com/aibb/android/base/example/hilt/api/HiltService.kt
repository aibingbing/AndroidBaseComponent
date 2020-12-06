package com.aibb.android.base.example.hilt.api

import com.aibb.android.base.example.hilt.pojo.HiltItem
import com.aibb.android.base.networkservice.RetrofitFactory
import com.aibb.android.base.networkservice.RxApi
import io.reactivex.Observable

/**
 * Copyright:   Copyright (c)  All rights reserved.<br>
 * Author:      aibingbing <br>
 * Date:        2020/8/29 <br>
 * Desc:        <br>
 */
object HiltService {
    val githubModel = RetrofitFactory.create("https://api.github.com", HiltApi::class.java)

    fun getGithubRespo(): Observable<List<HiltItem>> {
        return RxApi.createApi(githubModel.getUserGithubRepos())
    }
}