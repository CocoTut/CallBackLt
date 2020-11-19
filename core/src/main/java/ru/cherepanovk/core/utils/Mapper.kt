package ru.cherepanovk.core.utils

interface Mapper<in T, out R> {
    fun map(from: T): R
}