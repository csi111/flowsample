package com.csi.flowexample.ui.main.signin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.csi.flowexample.databinding.FragmentAccountListBinding
import com.csi.flowexample.databinding.FragmentEmailVerifyBinding
import com.csi.flowexample.ui.main.signin.viewmodel.EmailVerifyViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@AndroidEntryPoint
class AccountListFragment : Fragment() {

    companion object {
        fun newInstance() = AccountListFragment()
    }

    private val viewModel: EmailVerifyViewModel by viewModels()
    private lateinit var viewBinding: FragmentAccountListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewLifecycleOwner.lifecycle.addObserver(viewModel)
        return FragmentAccountListBinding.inflate(inflater, container, false)
            .also {
                it.viewModel = viewModel
                it.lifecycleOwner = viewLifecycleOwner
                viewBinding = it
            }.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

}