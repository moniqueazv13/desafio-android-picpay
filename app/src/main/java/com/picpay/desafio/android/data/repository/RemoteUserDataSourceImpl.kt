package com.picpay.desafio.android.data.repository

import com.picpay.desafio.android.data.remote.PicPayService
import com.picpay.desafio.android.domain.model.User
import kotlinx.coroutines.flow.Flow

class RemoteUserDataSourceImpl(
    private val api: PicPayService
) : BaseDataSource(), RemoteUserDataSource {
    override suspend fun fetchUserData(): Flow<List<User>> = call {
        api.getUsers()
    }
}