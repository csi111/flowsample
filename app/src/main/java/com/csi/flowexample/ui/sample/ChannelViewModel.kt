package com.csi.flowexample.ui.sample

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.receiveAsFlow

@FlowPreview
@ExperimentalCoroutinesApi
class ChannelViewModel @ViewModelInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel(), LifecycleObserver {

    private val _produceText: MutableLiveData<String> = MutableLiveData()
    private val _channelText: MutableLiveData<String> = MutableLiveData()

    private val produceChannel: ReceiveChannel<Int> = viewModelScope.produce {
        repeat(100) {
            delay(100)
            this.channel.send(it)
        }
        close()
    }

    private val broadcastChannel : BroadcastChannel<Int> = BroadcastChannel(Channel.BUFFERED)

    private val channel : Channel<Int> = Channel()

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {

        viewModelScope.launch {
            for (count in produceChannel) {
                _produceText.postValue(count.toString())
            }
        }

//        viewModelScope.launch {
//            for(count in broadcastChannel) {
//                _channelText.postValue(count.toString())
//            }
//        }

        viewModelScope.launch(Dispatchers.IO) {
            repeat(100) {
                delay(100)
                broadcastChannel.send(it)
            }
            broadcastChannel.close()
//            channel.close()
        }
    }

    fun countText(): LiveData<String> = _produceText

    fun channelCountText(): LiveData<String> = channelCount().map { it.toString() }

    fun count() = produceChannel.receiveAsFlow().asLiveData()

    fun channelCount() = broadcastChannel.asFlow().asLiveData()
}