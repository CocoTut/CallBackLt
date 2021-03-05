package com.cherepanovky.callbackit.di

import androidx.lifecycle.ViewModel
import com.cherepanovky.callbackit.CallBackItMainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.cherepanovk.core.di.dependencies.FeatureNavigator
import ru.cherepanovk.core.di.viewmodel.ViewModelKey
import ru.cherepanovk.core.platform.FeatureNavigatorImpl

@Module
abstract class MainActivityModule() {

    @Binds
    @IntoMap
    @ViewModelKey(CallBackItMainViewModel::class)
    abstract fun bindViewModel(callBackItMainViewModel: CallBackItMainViewModel): ViewModel

    @Binds
    abstract fun bindFeatureNavigator(featureNavigatorImpl: FeatureNavigatorImpl): FeatureNavigator
}