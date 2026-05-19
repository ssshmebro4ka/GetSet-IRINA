package com.example.getset.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.getset.theme.Database_Helper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(private val dbHelper: Database_Helper) : ViewModel() {

    private val _dataList = MutableStateFlow<List<Map<String, Any>>>(emptyList())
    val dataList: StateFlow<List<Map<String, Any>>> = _dataList

    fun loadData() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val data = dbHelper.loadDataToList()
                _dataList.update { data }
                println("Загружено ${data.size} упражнений")
            } catch (e: Exception) {
                println("Ошибка: ${e.message}")
                _dataList.update { emptyList() }
            }
        }
    }
}