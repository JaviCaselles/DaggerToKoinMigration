package com.example.poc.feature.b.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.poc.data.model.Product
import com.example.poc.feature.b.domain.GetProductsUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProductListViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase
) : ViewModel() {

    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> = _products

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    init {
        loadProducts()
    }

    fun loadProducts() {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null
            try {
                _products.value = getProductsUseCase()
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _loading.value = false
            }
        }
    }
}
