package com.picpay.desafio.android.data.repository

import com.picpay.desafio.android.domain.model.UserModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow

class UserRepositoryImpl(
    private val remoteUserDataSource: RemoteUserDataSource,
    private val localUserDataSource: LocalUserDataSource
) : UserDataRepository {

    override suspend fun fetchUserData() = remoteUserDataSource.fetchUserData()

    override fun fetchUserLocalData(): Flow<List<UserModel>> =
        localUserDataSource.fetchUserLocalData()

    override fun insertUser(usersList: List<UserModel>) =
        localUserDataSource.insertUser(usersList)

    override fun deleteUser() = localUserDataSource.deleteUsers()

    override fun fetchUser(): Flow<List<UserModel>> = flow {
        fetchUserLocalData().collect { localUsers ->
            if (localUsers.isEmpty()) {
                fetchUserData().collectLatest { remoteUsers ->
                    val mappedUsers = remoteUsers.map { user ->
                        UserModel(
                            id = user.id,
                            img = user.img,
                            name = user.name,
                            username = user.username
                        )
                    }
                    insertUser(mappedUsers)
                    emit(mappedUsers)
                }
            } else {
                emit(localUsers)
            }
        }
    }
}