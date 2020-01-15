package ru.cherepanovk.core_domain_impl.callservices

import android.content.Context
import android.content.Intent
import android.telephony.PhoneStateListener
import android.telephony.TelephonyManager

class CallPhoneStateListener(private val context: Context) : PhoneStateListener() {
    override fun onCallStateChanged(state: Int, incomingNumber: String?) {
        super.onCallStateChanged(state, incomingNumber)

//        when(state){
//            TelephonyManager.CALL_STATE_RINGING ->  startNotificationJobService(incomingNumber, state.toString())
//            TelephonyManager.CALL_STATE_OFFHOOK -> startNotificationJobService(incomingNumber, state.toString())
//        }
    }

//    private fun startNotificationJobService(savedNumber: String?, state: String?) {
//        val mIntent = Intent()
//        mIntent.putExtra("phonenumber", savedNumber)
//        mIntent.putExtra("state", state)
//        NotificationJobService.enqueueWork(context, mIntent)
//    }
}