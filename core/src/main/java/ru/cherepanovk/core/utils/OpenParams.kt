package ru.cherepanovk.core.utils

import android.os.Bundle

interface OpenParams {
    fun toBundle(bundle: Bundle? = null): Bundle

    interface Companion<T : OpenParams> {
        fun fromBundle(bundle: Bundle?): T?
    }
}