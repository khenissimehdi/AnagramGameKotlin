package com.example.deschiffresandletters


/* Determine if the parameter is an anagram of `this`
 * proposition must contain only letters of `this` (with the same or different order)
 */
fun String.containsAnagram(props: String): Boolean {
    props.forEach { c ->
         if (!this.contains(c)) {
             return false
         }
     }
    return true
}

