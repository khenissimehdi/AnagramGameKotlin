package com.example.deschiffresandletters

import android.content.Context
import java.lang.IllegalArgumentException

data class CumulatedFrequency(val letter: Char, val frequency: Int)

fun List<CumulatedFrequency>.findLetter(pickedNumber: Int): Char {
    forEach {
        if (pickedNumber < it.frequency) {
            return it.letter
        }
    }
    throw  IllegalArgumentException("Invalid pickdNumber")
}
fun String.toCharFrequencyPair(): Pair<Char, Int>? = split("\t").let {
    val c = it.getOrNull(0)?.getOrNull(0)
    val f = it.getOrNull(1)?.toIntOrNull()
    if (c != null && f != null) Pair(c,f) else null
}

class LetterPicker(frequencyTable: Map<Char, Int>) {
    private val cumulatedFrequencies: List<CumulatedFrequency> = run {
        val result = mutableListOf<CumulatedFrequency>()
        var p = 0
        frequencyTable.forEach {  entry ->
            result.add(CumulatedFrequency(entry.key, entry.value + p))
            p+= entry.value
        }
        result
    }

   private fun pickLetter() = cumulatedFrequencies.findLetter((0 until cumulatedFrequencies.last().frequency).random())

   fun pickLetters(n: Int) = (0 until n).map { pickLetter() }.joinToString ("" )
    companion object {
        fun buildFromResource(context: Context, resourceId: Int): LetterPicker {
            val inputStream = context.resources.openRawResource(resourceId)
            val r  = inputStream.bufferedReader().useLines { seq -> seq.mapNotNull { it.toCharFrequencyPair() }.toMap() }
            return LetterPicker(r)
        }
    }
}

