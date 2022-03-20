package com.example.deschiffresandletters
import android.content.Context
import java.util.*

typealias Dictionary = List<String>

fun Context.loadDictionaryTwo(assetPath: String): List<String> {
   return  this.assets.open(assetPath).bufferedReader().useLines { seq -> seq.map { it.trim() }.filter{ it.isNotEmpty() }.toList().sorted() }
}


