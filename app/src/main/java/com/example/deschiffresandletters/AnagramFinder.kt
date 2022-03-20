package com.example.deschiffresandletters

import android.annotation.SuppressLint
import android.content.Context
import java.util.*

typealias Dictionary = List<String>
typealias AnagramClasses = Map<String, String>


val String.sortedWordForm: String get() =
    this.toCharArray().sorted().joinToString("")


@SuppressLint("NewApi")
fun Dictionary.computeAnagramClasses(): AnagramClasses {
    val result = HashMap<String, String>() // values contain the word in the class (separated by ;)
    this.forEach { result.merge(it.sortedWordForm, it) { x, y -> "$x;$y" } }
    return result
}

fun Context.loadDictionary(assetPath: String) =
    this.assets.open(assetPath).bufferedReader().useLines { seq -> seq.map { it.trim() }.filter{ it.isNotEmpty() }.toList().sorted() }

class AnagramFinder(dictionary: Dictionary) {
    val anagramClasses = dictionary.computeAnagramClasses()

    fun findBestAnagrams(draw: String, minLetters: Int = 0): SortedSet<String> {
        val result = sortedSetOf<String>()
        val candidates = ArrayDeque(listOf(draw.sortedWordForm))
        while (candidates.size > 0) {
            val candidate = candidates.removeFirst()
            if (candidate.length < (result.firstOrNull()?.length ?: 0)) break
            anagramClasses[candidate.sortedWordForm]?.let { result += it.split(';') } ?: run {
                if (candidate.length - 1 >= minLetters && (result.size == 0 || result.first().length == candidate.length - 1)) {
                    val letterSet = candidate.sortedWordForm.toCharArray().toSet() // to remove duplicate letters
                    letterSet.forEach { letterToRemove ->
                        // replace only one letter (several samples of the same letter may be present)
                        val withoutLetter = candidate.sortedWordForm.replaceFirst("$letterToRemove", "")
                        candidates.add(withoutLetter)
                    }
                }
            }
        }
        return result
    }
}