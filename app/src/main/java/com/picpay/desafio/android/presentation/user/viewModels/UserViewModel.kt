package com.picpay.desafio.android.presentation.user.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.picpay.desafio.android.data.repository.UserRepository
import com.picpay.desafio.android.domain.model.UserVO
import com.picpay.desafio.android.presentation.user.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class UserViewModel(
    private val repository: UserRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<UserVO>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<UserVO>>> get() = _uiState

    init {
        getUserList()
    }

    private fun getUserList() {
        viewModelScope.launch {
            repository.getUsers()
                .flowOn(Dispatchers.IO)
                .catch { errorMessage ->
                    _uiState.value = UiState.Error(errorMessage)
                }
                .collect { users ->
                    _uiState.value = UiState.Success(users.map {
                        UserVO(it.id, it.img, it.name, it.username)
                    })
                }
        }
    }
}