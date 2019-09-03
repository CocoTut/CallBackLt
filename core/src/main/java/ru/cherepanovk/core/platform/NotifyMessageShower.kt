package ru.cherepanovk.core.platform

import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

//Do not add @Reusable or @Singleton
class NotifyMessageShower @Inject constructor(
    private val viewProvider: RootViewProvider
) {

    fun notify(@StringRes message: Int) {
        Snackbar.make(viewProvider.rootView, message, Snackbar.LENGTH_LONG).show()
    }

    fun notify(message: String) {
        Snackbar.make(viewProvider.rootView, message, Snackbar.LENGTH_LONG).show()
    }

    fun notifyWithAction(@StringRes message: Int, @StringRes actionText: Int, action: () -> Any): Snackbar {
        val snackBar = Snackbar.make(viewProvider.rootView, message, Snackbar.LENGTH_INDEFINITE)
        snackBar.setAction(actionText) { action.invoke() }
        snackBar.show()
        return snackBar
    }
}