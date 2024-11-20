package com.picpay.desafio.android.presentation.user.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.picpay.desafio.android.databinding.ListItemUserBinding
import com.picpay.desafio.android.domain.model.UserVO
import com.picpay.desafio.android.presentation.user.adapters.viewholders.UserListItemViewHolder

class UserListAdapter(diffCallback: DiffUtil.ItemCallback<UserVO>) : ListAdapter<UserVO, UserListItemViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListItemViewHolder
    {
        val binding = ListItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserListItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserListItemViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
    }
}