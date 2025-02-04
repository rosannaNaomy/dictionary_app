package com.example.dictionaryapp.feature_dictionary.data.util

import com.squareup.moshi.Moshi
import java.lang.reflect.Type

class MoshiParser(
    val moshi: Moshi
) : JsonParser {

    override fun <T> fromJson(json: String, type: Type): T? {
        val adapter = moshi.adapter<T>(type)
        return adapter.fromJson(json)
    }

    override fun <T> toJson(obj: T, type: Type): String? {
        val adapter = moshi.adapter<T>(type)
        return adapter.toJson(obj)
    }
}