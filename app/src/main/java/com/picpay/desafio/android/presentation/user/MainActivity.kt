package com.picpay.desafio.android.presentation.user

import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.picpay.desafio.android.databinding.ActivityMainBinding
import com.picpay.desafio.android.presentation.hide
import com.picpay.desafio.android.presentation.show
import com.picpay.desafio.android.presentation.user.adapters.UserListAdapter
import com.picpay.desafio.android.presentation.user.model.UserDiffCallback
import com.picpay.desafio.android.presentation.user.viewModels.UserViewModel
import com.picpay.desafio.android.presentation.viewBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity() {

    override val binding by viewBinding(ActivityMainBinding::inflate)

    private val userViewModel: UserViewModel by viewModel()

    override fun initView() {
        with(binding) {
            val userListAdapter = UserListAdapter(UserDiffCallback())
            recyclerViewUsers.adapter = userListAdapter
            recyclerViewUsers.layoutManager = LinearLayoutManager(this@MainActivity)

            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    userViewModel.uiState.collectLatest { state ->
                        when (state) {
                            is UiState.Loading -> {
                                userListProgressBar.show()
                                recyclerViewUsers.hide()
                            }

                            is UiState.Success -> {
                                userListProgressBar.hide()
                                recyclerViewUsers.show()
                                userListAdapter.submitList(state.value)
                            }

                            is UiState.Error -> {
                                userListProgressBar.hide()
                                Toast.makeText(
                                    this@MainActivity,
                                    "$state.error",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                }
            }
        }
    }
}
