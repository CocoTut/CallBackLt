package com.cherepanovky.callbackit.di

import com.cherepanovky.callbackit.CallBackItMainActivity
import dagger.Component
import ru.cherepanovk.core.di.dependencies.RootViewProvider

@Component(
   modules = [MainActivityModule::class]
)
interface MainActivityComponent : RootViewProvider {
    fun inject(mainActivity: CallBackItMainActivity)
}