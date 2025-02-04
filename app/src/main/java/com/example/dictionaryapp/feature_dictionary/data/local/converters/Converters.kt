package com.example.dictionaryapp.feature_dictionary.data.local.converters

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.example.dictionaryapp.feature_dictionary.data.util.JsonParser
import com.example.dictionaryapp.feature_dictionary.domain.model.Meaning
import com.squareup.moshi.Types

@ProvidedTypeConverter
class Converters(
    private val jsonParser: JsonParser
) {

    private val listType = Types.newParameterizedType(List::class.java, Meaning::class.java)

    @TypeConverter
    fun fromMeaningList(meanings: List<Meaning>?): String? {
        return meanings?.let { jsonParser.toJson(it, listType) } // Serialize List<Meaning> to JSON
    }

    @TypeConverter
    fun toMeaningList(json: String?): List<Meaning>? {
        return json?.let { jsonParser.fromJson(it, listType) } // Deserialize JSON to List<Meaning>
    }

    private val stringListType = Types.newParameterizedType(List::class.java, String::class.java)

    @TypeConverter
    fun fromStringList(value: List<String>?): String {
        return jsonParser.toJson(value, stringListType) ?: "[]" // Serialize List<String> to JSON
    }

    @TypeConverter
    fun toStringList(json: String?): List<String> {
        return json?.let { jsonParser.fromJson(it, stringListType) } ?: emptyList() // Deserialize JSON to List<String>
    }

}