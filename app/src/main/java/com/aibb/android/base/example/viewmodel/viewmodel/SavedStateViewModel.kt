package com.aibb.android.base.example.viewmodel.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class SavedStateViewModel(private val state: SavedStateHandle) : ViewModel() {
    fun loadData() {
        state.get<String>("saved_state_name")
    }
}