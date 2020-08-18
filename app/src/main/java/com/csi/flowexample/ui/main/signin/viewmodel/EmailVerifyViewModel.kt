package com.csi.flowexample.ui.main.signin.viewmodel

import android.util.Patterns
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class EmailVerifyViewModel @ViewModelInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel(), LifecycleObserver {

    val emailAddress: MutableLiveData<String> = MutableLiveData()

    fun getButtonEnabled(): LiveData<Boolean> =
        emailAddress.map { Patterns.EMAIL_ADDRESS.matcher(it).matches() }

}