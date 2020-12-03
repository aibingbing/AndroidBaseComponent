package com.aibb.android.base.example.network.view;

import com.aibb.android.base.example.network.pojo.GithubRepos;
import com.aibb.android.base.mvp.IBaseMvpView;

import java.util.List;

/**
 * Copyright:   Copyright (c)  All rights reserved.<br>
 * Author:      aibingbing <br>
 * Date:        2020/8/29 <br>
 * Desc:        <br>
 */
public interface NetworkServiceTestView extends IBaseMvpView {
    void dismissLoading();
    void onLoadDataSuccess(List<GithubRepos> data);
    void onLoadDataFailed();
}
