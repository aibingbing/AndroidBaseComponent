package com.aibb.android.base.networkservice.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Copyright:   Copyright (c)  All rights reserved.<br>
 * Author:      aibingbing <br>
 * Date:        2020/8/29 <br>
 * Desc:        <br>
 */
public class GsonProvider {

    private GsonProvider() {
    }

    private static class GsonProviderHolder {
        private static final GsonProvider INSTANCE = new GsonProvider();
        private static GsonBuilder gsonBuilder = INSTANCE.providerGsonBuilder();
        private static Gson gson = gsonBuilder.create();
    }

    public static GsonProvider getInstance() {
        return GsonProviderHolder.INSTANCE;
    }

    public Gson getGson() {
        if (GsonProviderHolder.gson == null) {
            GsonProviderHolder.gson = providerGsonBuilder().create();
        }
        return GsonProviderHolder.gson;
    }

    /**
     * 存在通过MultiTypeJsonParser去注册多类型解析的情况，
     * 子类根据具体情况可以重写
     *
     * @return gsonBuilder
     */
    protected GsonBuilder providerGsonBuilder() {
        return new GsonBuilder();
    }

//   重写providerGsonBuilder的例子
//    protected GsonBuilder providerGsonBuilder() {
//        GsonBuilder gsonBuilder = new GsonBuilder();
//        MultiTypeJsonParser.Builder<Person.class>()
//                .registerTypeElementName("type")
//                .registerTargetClass(Person.class)
//                .registerTargetUpperLevelClass(Org.class)
//                .registerTypeElementValueWithClassType("male", Man.class)
//                .build(gsonBuilder);
//        return gsonBuilder;
//    }

}
