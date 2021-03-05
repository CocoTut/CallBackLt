package ru.cherepanovk.core.platform

import android.view.View
import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

//Do not add @Reusable or @Singleton
class NotifyMessageShower @Inject constructor() {

    fun notify(view: View, @StringRes message: Int) {
        Snackbar.make(view.rootView, message, Snackbar.LENGTH_LONG).show()
    }

    fun notify(view: View, message: String) {
        Snackbar.make(view.rootView, message, Snackbar.LENGTH_LONG).show()
    }

    fun notifyWithAction(view: View, @StringRes message: Int, @StringRes actionText: Int, action: () -> Unit): Snackbar {
        val snackBar = Snackbar.make(view.rootView, message, Snackbar.LENGTH_LONG)
        snackBar.setAction(actionText) { action.invoke() }
        snackBar.show()
        return snackBar
    }
}