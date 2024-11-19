package com.picpay.desafio.android.data.repository

import com.picpay.desafio.android.domain.model.User
import kotlinx.coroutines.flow.Flow

interface RemoteUserDataSource {
    suspend fun fetchUserData(): Flow<List<User>>
}
