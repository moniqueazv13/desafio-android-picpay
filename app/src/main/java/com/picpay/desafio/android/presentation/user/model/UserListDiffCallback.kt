package com.picpay.desafio.android.presentation.user.model

import androidx.recyclerview.widget.DiffUtil
import com.picpay.desafio.android.domain.model.UserVO

class UserDiffCallback : DiffUtil.ItemCallback<UserVO>() {
    override fun areItemsTheSame(oldItem: UserVO, newItem: UserVO) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: UserVO, newItem: UserVO) = oldItem == newItem
}
