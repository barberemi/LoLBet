package com.example.lolbet.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lolbet.data.Rank
import com.example.lolbet.data.User
import com.example.lolbet.repository.RankRepository
import com.example.lolbet.repository.UserRepository

class UserViewModel: ViewModel() {
    private val _userData = MutableLiveData<User>()
    val userData: LiveData<User> get() = _userData


    init {
        _userData.value = UserRepository.getUserData()
    }

    fun getRank(rps: Int): Rank? {
        return RankRepository.getRankByRps(rps)
    }

    fun getRpsNextRank(rps: Int): Rank? {
        return RankRepository.getNextRank(rps)
    }
}