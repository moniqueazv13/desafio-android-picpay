package com.picpay.desafio.android.presentation.user

import com.picpay.desafio.android.presentation.user.viewModels.UserViewModel
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.picpay.desafio.android.R
import com.picpay.desafio.android.databinding.ActivityMainBinding
import com.picpay.desafio.android.domain.model.UserModel
import com.picpay.desafio.android.presentation.hide
import com.picpay.desafio.android.presentation.user.adapters.UserListAdapter
import com.picpay.desafio.android.presentation.show
import com.picpay.desafio.android.presentation.viewBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity() {

    override val binding by viewBinding(ActivityMainBinding::inflate)

    private val userViewModel: UserViewModel by viewModel()

    override fun initView() = with(binding) {
        initObservers()

//        userViewModel.getUserList()

        binding.recyclerViewUsers.apply {
            adapter = UserListAdapter()
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
        userListProgressBar.show()
    }

    private fun initObservers() =
        with(userViewModel) {
            onGetUsersError.observe(this@MainActivity) { errorSignatureState() }

            lifecycleScope.launch {
                onGetUsersSuccess.collectLatest { data ->
                    successSignatureState(data)
                }
            }
        }

    private fun errorSignatureState() = with(binding) {
        userListProgressBar.hide()
        binding.recyclerViewUsers.hide()
        Toast.makeText(this@MainActivity, getString(R.string.error), Toast.LENGTH_SHORT).show()
    }

    private fun successSignatureState(data: List<UserModel>) = with(binding) {
        userListProgressBar.hide()
        (binding.recyclerViewUsers.adapter as UserListAdapter).users = data
    }
}