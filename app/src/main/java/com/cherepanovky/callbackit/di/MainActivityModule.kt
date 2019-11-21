package com.cherepanovky.callbackit.di

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cherepanovky.callbackit.CallBackItMainViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import ru.cherepanovk.core.di.viewmodel.ViewModelFactory
import ru.cherepanovk.core.di.viewmodel.ViewModelKey
import ru.cherepanovk.core.di.viewmodel.ViewModelModule
import ru.cherepanovk.core.platform.RootView

@Module(includes = [MainActivityModule.Binding::class])
class MainActivityModule(private val rootView: View) {

    @Provides
    fun provideRootView(): RootView = RootView { rootView }

    @Module
    abstract class Binding {
        @Binds
        abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

        @Binds
        @IntoMap
        @ViewModelKey(CallBackItMainViewModel::class)
        abstract fun bindViewModel(callBackItMainViewModel: CallBackItMainViewModel): ViewModel
    }
}