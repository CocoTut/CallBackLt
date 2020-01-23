package ru.cherepanovk.core.utils.extentions

/**
 * Removes [key] iff value associated with it is equals to value
 * */
fun <K, V> MutableMap<K, V>.removeIfEquals(key: K, value: V): Boolean =
    if (this[key] == value) {
        this.remove(key)
        true
    } else {
        false
    }
