package com.example.getset.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.getset.data.local.DatabaseHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(private val dbHelper: DatabaseHelper) : ViewModel() {

    private val _dataList = MutableStateFlow<List<Map<String, Any>>>(emptyList())
    val dataList: StateFlow<List<Map<String, Any>>> = _dataList

    fun loadData() {
        viewModelScope.launch {
            try {
                val data = withContext(Dispatchers.IO) {
                    dbHelper.loadDataToList()
                }
                _dataList.update { data }
                println("Загружено ${data.size} упражнений")
            } catch (e: Exception) {
                println("Ошибка: ${e.message}")
                _dataList.update { emptyList() }
            }
        }
    }
}