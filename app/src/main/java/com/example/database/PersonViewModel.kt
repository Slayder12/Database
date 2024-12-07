package com.example.database

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PersonViewModel: ViewModel() {
    val personLiveData: MutableLiveData<MutableList<Person>> = MutableLiveData(mutableListOf())
}
