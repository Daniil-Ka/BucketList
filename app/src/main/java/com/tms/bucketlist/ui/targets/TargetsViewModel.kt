package com.tms.bucketlist.ui.targets

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TargetsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is targets Fragment"
    }
    val text: LiveData<String> = _text
}