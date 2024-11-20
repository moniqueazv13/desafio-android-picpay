package com.picpay.desafio.android.di

import com.picpay.desafio.android.data.remote.PicPayService
import com.picpay.desafio.android.data.repository.UserRepository
import com.picpay.desafio.android.data.repository.UserRepositoryImpl
import com.picpay.desafio.android.presentation.user.viewModels.UserViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val appModule = module {
    viewModel { UserViewModel(get()) }
    factory<UserRepository> { UserRepositoryImpl(get()) }
    factory<PicPayService> {
        get<Retrofit>()
            .create(PicPayService::class.java)
    }
}

val applicationModules =
    listOf(
        appModule, securityModule
    )