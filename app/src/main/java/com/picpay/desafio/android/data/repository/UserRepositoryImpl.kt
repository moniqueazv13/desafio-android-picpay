package com.picpay.desafio.android.data.repository

import com.picpay.desafio.android.data.remote.PicPayService
import com.picpay.desafio.android.domain.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UserRepositoryImpl(private val api: PicPayService) : UserRepository {
    override suspend fun getUsers(): Flow<List<User>> = flow {
        emit(api.getUsers())
    }
}