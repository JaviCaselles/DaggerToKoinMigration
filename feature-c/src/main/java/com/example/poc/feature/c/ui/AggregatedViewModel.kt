package com.example.poc.feature.c.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.poc.data.model.Product
import com.example.poc.data.model.User
import com.example.poc.feature.c.domain.AggregatedUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class AggregatedViewModel @Inject constructor(
    private val aggregatedUseCase: AggregatedUseCase
) : ViewModel() {

    private val _data = MutableLiveData<Pair<List<User>, List<Product>>>()
    val data: LiveData<Pair<List<User>, List<Product>>> = _data

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    init {
        loadData()
    }

    fun loadData() {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null
            try {
                _data.value = aggregatedUseCase()
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _loading.value = false
            }
        }
    }
}
