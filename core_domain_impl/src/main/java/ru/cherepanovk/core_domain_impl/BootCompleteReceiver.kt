package ru.cherepanovk.core_domain_impl

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import timber.log.Timber

class BootCompleteReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
//        Toast.makeText(context, "Boot is completed1!", Toast.LENGTH_SHORT).show()
        println("RECEIVE_BOOT_COMPLETED111")
    }
}
