package ru.cherepanovk.core.utils.extentions

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import ru.cherepanovk.core.acc.Event
import ru.cherepanovk.core.acc.EventObserver
import ru.cherepanovk.core.exception.Failure
import ru.cherepanovk.core.platform.BaseFragment

fun <T> Fragment.observe(liveData: LiveData<T>, block: (T) -> Unit) {
    liveData.observe(viewLifecycleOwner, Observer(block))
}
fun Fragment.observeFailure(liveData: LiveData<Failure>, body: (Failure?) -> Unit) =
    liveData.observe(viewLifecycleOwner, Observer(body))

fun <T : Any, L : LiveData<Event<T>>> Fragment.observeEvent(liveData: L, body: (T) -> Unit) =
    liveData.observe(viewLifecycleOwner, EventObserver(body))

//val BaseFragment.viewContainer: View get() = activity!!.findViewById(R.id.fragmentContainer)

val BaseFragment.appContext: Context get() = activity?.applicationContext!!

fun <T> Fragment.getNavigationResult(key: String = "result"): LiveData<T>? =
    findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<T>(key)

fun <T> Fragment.setNavigationResult(result: T, key: String = "result") {
    findNavController().previousBackStackEntry?.savedStateHandle?.set(key, result)
}