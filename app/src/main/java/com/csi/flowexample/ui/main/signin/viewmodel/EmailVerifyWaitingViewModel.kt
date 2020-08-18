package com.csi.flowexample.ui.main.signin.viewmodel

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class EmailVerifyWaitingViewModel @ViewModelInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel(), LifecycleObserver {

    companion object {
        const val TIME_WAITING_CODE = 30 // sec
        const val RETRY_CODE = 10 // sec
    }

    private val _confirmCode: MutableLiveData<String> = MutableLiveData()
    fun confirmCode() = _confirmCode

    private val _waitingCodeTime: MutableLiveData<String> = MutableLiveData()
    fun waitingCodeTime(): LiveData<String> = _waitingCodeTime

    private val _enableRetryBtn: MutableLiveData<Boolean> = MutableLiveData()
    fun enableRetryBtn(): LiveData<Boolean> = _enableRetryBtn

    private val _waitingRetryTime: MutableLiveData<String> = MutableLiveData()
    fun waitingRetryTime(): LiveData<String> = _waitingRetryTime

    val email = savedStateHandle.get<String>("email")

    private var codeCounter: ReceiveChannel<Int> = viewModelScope.produce(Dispatchers.Default) {
        repeat(TIME_WAITING_CODE) {
            delay(1000)
            this.channel.send(it)
        }
        close()
    }

    fun onClickRetry() {
        viewModelScope.launch {
            startWaitingCodeCounter()
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        viewModelScope.launch {
            codeCounter.receiveAsFlow().collect {
                updateWaitingCodeTime((TIME_WAITING_CODE - it).toLong())
                if (RETRY_CODE - it >= 0) {
                    updateRetryCodeTime((RETRY_CODE - it).toLong())
                }
                _enableRetryBtn.postValue(RETRY_CODE - it <= 0)
            }
        }


    }

    private suspend fun startWaitingCodeCounter() {
        codeCounter.receiveAsFlow().collect {
            updateWaitingCodeTime((TIME_WAITING_CODE - it).toLong())
            if (RETRY_CODE - it >= 0) {
                updateRetryCodeTime((RETRY_CODE - it).toLong())
            }
            _enableRetryBtn.postValue(RETRY_CODE - it <= 0)
        }
    }

    private fun updateWaitingCodeTime(remainingSec: Long) {
        val min = remainingSec / 60
        val sec = remainingSec - (min * 60)
        _waitingCodeTime.postValue(String.format("%02d:%02d", min, sec))
    }

    private fun updateRetryCodeTime(remainingSec: Long) {
        _waitingRetryTime.postValue(String.format("%02d", remainingSec))
    }
}