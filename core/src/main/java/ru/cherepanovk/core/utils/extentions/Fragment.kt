package ru.cherepanovk.core.utils.extentions

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import ru.cherepanovk.core.exception.Failure
import ru.cherepanovk.core.platform.BaseFragment

fun <T> Fragment.observe(liveData: LiveData<T>, block: (T) -> Unit) {
    liveData.observe(viewLifecycleOwner, Observer(block))
}
fun Fragment.observeFailure(liveData: LiveData<Failure>, body: (Failure?) -> Unit) =
    liveData.observe(viewLifecycleOwner, Observer(body))

//val BaseFragment.viewContainer: View get() = activity!!.findViewById(R.id.fragmentContainer)

val BaseFragment.appContext: Context get() = activity?.applicationContext!!