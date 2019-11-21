package com.cherepanovky.callbackit.di

import com.cherepanovky.callbackit.CallBackItMainActivity
import dagger.Component
import ru.cherepanovk.core.di.dependencies.RootViewProvider
import ru.cherepanovk.core_domain_api.di.CoreDomainApi

@Component(
   modules = [MainActivityModule::class],
    dependencies = [CoreDomainApi::class]
)
interface MainActivityComponent : RootViewProvider {
    fun inject(mainActivity: CallBackItMainActivity)
}