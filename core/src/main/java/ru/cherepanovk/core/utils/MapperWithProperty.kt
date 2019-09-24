package ru.cherepanovk.core.utils

interface MapperWithProperty<in T, out R, B> {
    var property: B

    fun map(from: T): R
}