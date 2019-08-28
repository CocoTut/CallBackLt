package com.cherepanovky.callbackit.core.di

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import com.cherepanovky.callbackit.core.di.viewmodel.VMFactory
import com.cherepanovky.callbackit.core.di.viewmodel.ViewModelModule
import dagger.Component
import ru.cherepanovk.core.di.dependencies.ContextProvider
import ru.cherepanovk.core_db_api.di.CoreDbApi
import ru.cherepanovk.core_db_impl.di.CoreDbModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    ApplicationModule::class,
    CoreDbModule::class,
    ViewModelModule::class
])
interface ApplicationComponent : ContextProvider, CoreDbApi {
    fun inject(application: Application)
}