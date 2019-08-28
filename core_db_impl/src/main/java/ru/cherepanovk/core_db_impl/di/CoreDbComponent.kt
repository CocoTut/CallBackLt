package ru.cherepanovk.core_db_impl.di

import dagger.Component
import ru.cherepanovk.core.di.dependencies.ContextProvider
import javax.inject.Singleton

@Component(modules = [CoreDbModule::class],
    dependencies = [ContextProvider::class])
@Singleton
interface CoreDbComponent {

}