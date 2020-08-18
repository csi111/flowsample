package com.csi.flowexample.ui.main.signin

import android.os.Bundle
import android.util.Patterns
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.csi.flowexample.databinding.FragmentEmailVerifyBinding
import com.csi.flowexample.ui.main.signin.viewmodel.EmailVerifyViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class EmailVerifyFragment : Fragment() {

    companion object {
        fun newInstance() = EmailVerifyFragment()
    }

    private val viewModel: EmailVerifyViewModel by viewModels()
    private lateinit var viewBinding: FragmentEmailVerifyBinding

     override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewLifecycleOwner.lifecycle.addObserver(viewModel)
        return FragmentEmailVerifyBinding.inflate(inflater, container, false)
            .apply {
                viewModel = this@EmailVerifyFragment.viewModel
                lifecycleOwner = viewLifecycleOwner
                btnInit.setOnClickListener {
                    root.findNavController()
                        .navigate(EmailVerifyFragmentDirections.actionEmailVerifyWaiting(tvEmail.text.toString()))
                }
                tvEmail.setOnEditorActionListener(TextView.OnEditorActionListener { v, _, event ->
                    if (Patterns.EMAIL_ADDRESS.matcher(v.text).matches()) {
                        event?.let { keyEvent ->
                            if (keyEvent.action == KeyEvent.ACTION_DOWN) root.findNavController()
                                .navigate(EmailVerifyFragmentDirections.actionEmailVerifyWaiting(v.text.toString()))
                            return@OnEditorActionListener true
                        }
                        return@OnEditorActionListener true
                    }
                    return@OnEditorActionListener false
                })
            }
            .also { viewBinding = it }
            .root
    }

    @FlowPreview
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

}