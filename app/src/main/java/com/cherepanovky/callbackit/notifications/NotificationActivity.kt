package com.cherepanovky.callbackit.notifications

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.cherepanovky.callbackit.CallBackItMainActivity

class NotificationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Toast.makeText(this, "Open", Toast.LENGTH_SHORT).show()
    }

    override fun onBackPressed() {
        startActivity(Intent(this, CallBackItMainActivity::class.java))
        super.onBackPressed()
    }
}
