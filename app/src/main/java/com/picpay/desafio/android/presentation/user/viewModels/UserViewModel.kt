package com.picpay.desafio.android.presentation.user.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.picpay.desafio.android.domain.model.UserModel
import com.picpay.desafio.android.domain.useCases.UserDataUseCase
import com.picpay.desafio.android.presentation.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class UserViewModel(
private val userDataUseCase: UserDataUseCase
) : ViewModel() {

    private val _onGetUsersSuccess = MutableStateFlow<List<UserModel>>(emptyList())
    val onGetUsersSuccess: StateFlow<List<UserModel>> = _onGetUsersSuccess

    private val _onGetUsersError = SingleLiveEvent<Unit?>()
    val onGetUsersError: SingleLiveEvent<Unit?> = _onGetUsersError

    init {
        getUserList()
    }

    private fun getUserList() {
            viewModelScope.launch {
                userDataUseCase.getUsers()
                    .flowOn(Dispatchers.IO)
                    .catch { _onGetUsersError.postValue(null) }
                    .collect { _onGetUsersSuccess.value = it }
            }
    }
}