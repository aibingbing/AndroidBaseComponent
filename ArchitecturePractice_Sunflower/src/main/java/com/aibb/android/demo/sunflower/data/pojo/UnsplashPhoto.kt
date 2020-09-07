package com.aibb.android.demo.sunflower.data.pojo

import com.google.gson.annotations.SerializedName

data class UnsplashPhoto(
    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("urls")
    val urls: UnsplashPhotoUrls,

    @field:SerializedName("user")
    val user: UnsplashUser,
)