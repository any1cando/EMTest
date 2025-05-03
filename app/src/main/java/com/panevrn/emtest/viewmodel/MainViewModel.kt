package com.panevrn.emtest.viewmodel



import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


// TODO: Возможно, удалить ViewModel из-за ненадобности
@HiltViewModel
class MainViewModel @Inject constructor(): ViewModel() {

    private val _selectedIcon = MutableLiveData<Int>()
    val selectedIcon: LiveData<Int> get() = _selectedIcon

    fun onIconSelected(itemId: Int) {
        _selectedIcon.value = itemId
    }

}
