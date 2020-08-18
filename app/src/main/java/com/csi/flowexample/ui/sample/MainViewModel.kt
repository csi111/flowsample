package com.csi.flowexample.ui.sample

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@FlowPreview
@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModel @ViewModelInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel(), LifecycleObserver {

    private val countChannel = BroadcastChannel<String>(Channel.CONFLATED)


    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        viewModelScope.launch {
            for (i in 1..10) {
                delay(1000)
                countChannel.send(i.toString())
            }
        }
    }

    private fun countChannelFlow() = countChannel.asFlow()
    fun countChannelFlowLiveData(): LiveData<String> = countChannelFlow().asLiveData()


    fun countFlow(value: String) = flow {
        for (i in 1..10) {
            delay(1000)
            emit("$value $i")
        }
    }

    fun countFlowLiveData() = countFlow("Count").asLiveData()

    private fun countFlatmapLatestFlow(): Flow<String> = flow {
        emit("1 Latest")
        delay(3000)
        emit("2 Latest")
        delay(3000)
        emit("3 Latest")

    }.flatMapLatest { countFlow(it) }

    private fun countFlatmapMergeFlow(): Flow<String> = flow {
        emit("1 Merge")
        delay(3000)
        emit("2 Merge")
        delay(3000)
        emit("3 Merge")

    }.flatMapMerge { countFlow(it) }

    private fun countFlatmapConcatFlow(): Flow<String> = flow {
        emit("1 Concat")
        delay(3000)
        emit("2 Concat")
        delay(3000)
        emit("3 Concat")

    }.flatMapConcat { countFlow(it) }

    fun countFlatmapLatestFlowLiveData() = countFlatmapLatestFlow().asLiveData()

    fun countFlatmapMergeFlowLiveData() = countFlatmapMergeFlow().asLiveData()

    fun countFlatmapConcatFlowLiveData() = countFlatmapConcatFlow().asLiveData()
}