package ru.cherepanovk.core.platform

import android.content.Intent

interface ActivityStarter {
    fun startActivity(intent: Intent)
    fun startActivityForResult(intent: Intent, requestCode: Int)
}
