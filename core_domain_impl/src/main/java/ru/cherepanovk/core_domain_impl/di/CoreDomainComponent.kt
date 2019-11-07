package ru.cherepanovk.core_domain_impl.di

import dagger.Component
import ru.cherepanovk.core.di.dependencies.ContextProvider
import ru.cherepanovk.core_domain_api.di.CoreDomainApi

@Component(
    dependencies = [
        ContextProvider::class
    ],
    modules = [CoreDomainModule::class]
)
interface CoreDomainComponent : CoreDomainApi {
}