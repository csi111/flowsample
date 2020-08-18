package com.csi.flowexample.ui.sample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.csi.flowexample.databinding.ChannelFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class ChannelFragment : Fragment() {

    companion object {
        fun newInstance() = ChannelFragment()
    }

    private val viewModel: ChannelViewModel by viewModels()
    private lateinit var viewBinding: ChannelFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewLifecycleOwner.lifecycle.addObserver(viewModel)
        return ChannelFragmentBinding.inflate(inflater, container, false)
            .also {
                it.viewModel = viewModel
                it.lifecycleOwner = viewLifecycleOwner
                viewBinding = it
            }.root
    }

    @FlowPreview
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.count().observe(viewLifecycleOwner, Observer {
            viewBinding.channelProgress.progress = it
        })

        viewModel.channelCount().observe(viewLifecycleOwner, Observer {
            viewBinding.oriChannelProgress.progress = it
        })
    }

}