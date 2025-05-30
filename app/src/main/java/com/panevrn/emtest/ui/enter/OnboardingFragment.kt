package com.panevrn.emtest.ui.enter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.panevrn.emtest.R
import com.panevrn.emtest.databinding.FragmentOnboardingBinding
import com.panevrn.emtest.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class OnboardingFragment : Fragment() {

    private val viewModel: AuthViewModel by viewModels()
    private var _binding: FragmentOnboardingBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentOnboardingBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (viewModel.isOnboardingCompleted()) {
            findNavController().navigate(
                R.id.action_onboardingFragment_to_authFragment,
                null,
                navOptions { popUpTo(R.id.nav_graph) { inclusive = true } }
            )
            return
        }

        binding.btnContinue.setOnClickListener {
            viewModel.completeOnboarding()
            findNavController().navigate(
                R.id.action_onboardingFragment_to_authFragment,
                null,
                navOptions { popUpTo(R.id.nav_graph) { inclusive = true } }
            )
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}