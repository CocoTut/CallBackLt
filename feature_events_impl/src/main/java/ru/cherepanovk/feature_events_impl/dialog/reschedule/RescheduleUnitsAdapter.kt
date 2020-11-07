package ru.cherepanovk.feature_events_impl.dialog.reschedule

import android.content.Context
import android.widget.ArrayAdapter
import androidx.annotation.LayoutRes

class RescheduleUnitsAdapter @JvmOverloads constructor(
    context: Context,
    @LayoutRes layoutId: Int
) :  ArrayAdapter<Map<RescheduleUnitType, String>> (context, layoutId){

}