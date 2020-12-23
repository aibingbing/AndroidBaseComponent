package com.aibb.android.base.example.datastore.serializer

import androidx.datastore.core.Serializer
import com.aibb.android.base.example.datastore.proto.UserInfoPreferences
import java.io.InputStream
import java.io.OutputStream

object UserInfoSerializer : Serializer<UserInfoPreferences> {
    override val defaultValue: UserInfoPreferences
        get() = UserInfoPreferences.getDefaultInstance()

    override fun readFrom(input: InputStream): UserInfoPreferences {
        return UserInfoPreferences.parseFrom(input)
    }

    override fun writeTo(t: UserInfoPreferences, output: OutputStream) {
        t.writeTo(output)
    }
}