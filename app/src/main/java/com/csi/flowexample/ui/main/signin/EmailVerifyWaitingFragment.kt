package com.csi.flowexample.ui.main.signin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.csi.flowexample.databinding.FragmentWaitConnectingBinding
import com.csi.flowexample.ui.main.signin.viewmodel.EmailVerifyWaitingViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class EmailVerifyWaitingFragment : Fragment() {

    companion object {
        fun newInstance() = EmailVerifyWaitingFragment()
    }

    private val viewModel: EmailVerifyWaitingViewModel by viewModels()
    private lateinit var viewBinding: FragmentWaitConnectingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewLifecycleOwner.lifecycle.addObserver(viewModel)
        return FragmentWaitConnectingBinding.inflate(inflater, container, false)
            .also {
                it.viewModel = viewModel
                it.lifecycleOwner = viewLifecycleOwner
                viewBinding = it


            }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.confirmCode().observe(viewLifecycleOwner, Observer {
            if (it.length == 4) {
                if (it == "0000") {
                    findNavController().navigate(EmailVerifyWaitingFragmentDirections.actionLogin())
                } else {
                    findNavController().navigate(EmailVerifyWaitingFragmentDirections.actionFailedVerify())
                }
            }
        })
    }

    @FlowPreview
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

}