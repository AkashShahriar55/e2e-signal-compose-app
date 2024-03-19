package com.nsa.network.di

import javax.inject.Qualifier


@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class ApplicationScope

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class NoAuthClient