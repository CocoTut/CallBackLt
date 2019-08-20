package ru.cherepanovk.imgurtest.utils.extensions

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

fun Fragment.hideKeyboard() {
    (context as? AppCompatActivity?)?.window?.decorView?.hideKeyboard()
}