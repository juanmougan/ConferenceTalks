package com.github.juanmougan.talks.helpers

import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import com.github.juanmougan.talks.models.Talk
import java.io.InputStream

fun readStrictCsv(inputStream: InputStream): List<Talk> = csvReader().open(inputStream) {
    var lineCount = 0L
    readAllWithHeaderAsSequence().map {
        Talk(
            id = ++lineCount,   // TODO drop this nasty hack once a DB is present
            title = it["Title"]!!.trim(),
            abstract = it["Abstract"]!!.trim(),
            speakers = parseStringToList(it["Speakers"]!!.trim()),
            topics = parseStringToList(it["Topics"]!!.trim()),
            room = it["Room"]!!.trim()
        )
    }.toList()
}

fun parseStringToList(listAsString: String): List<String> {
    return listAsString.removeSurrounding("\"").split(",").map { it.trim() }
}
