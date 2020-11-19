package ru.cherepanovk.feature_alarm_impl.callservices

import android.telephony.PhoneStateListener

class CallPhoneStateListener : PhoneStateListener() {
    override fun onCallStateChanged(state: Int, incomingNumber: String?) {
        super.onCallStateChanged(state, incomingNumber)
        println("Boot is completed1! CallPhoneStateListener")
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