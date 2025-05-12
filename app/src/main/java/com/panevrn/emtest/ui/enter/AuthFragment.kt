package com.panevrn.emtest.ui.enter

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.panevrn.emtest.R
import com.panevrn.emtest.databinding.FragmentAuthBinding
import com.panevrn.emtest.viewmodel.EnterViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AuthFragment : Fragment() {

    private val viewModel: EnterViewModel by viewModels()
    private var _binding: FragmentAuthBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentAuthBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 1-ый кейс
        binding.btnVk.setOnClickListener {
            openUrl(url = "https://vk.com/")
        }

        // 2-ой кейс
        binding.btnOk.setOnClickListener {
            openUrl("https://ok.ru/")
        }


        binding.etEmail.doOnTextChanged { text, _, _, _ ->
            viewModel.onEmailChanged(text.toString())
        }


        binding.etPassword.doOnTextChanged { text, _, _, _ ->
            viewModel.onPasswordChanged(text.toString())
        }


        viewModel.isLoginEnabled.observe(viewLifecycleOwner) { isEnabled ->
            binding.btnLogin.isEnabled = isEnabled
        }


        // Нажимая на кнопку, мы запускаем метод login() из EnterViewModel (отправка логин/пароль)
        binding.btnLogin.setOnClickListener {
            viewModel.login()
        }


        // Проверяем, мониторя состояние авторизации
        viewModel.authResult.observe(viewLifecycleOwner) { result ->
            result.onSuccess {
                findNavController().navigate(
                    R.id.action_authFragment_to_mainFragment,
                    null,
                    navOptions { popUpTo(R.id.nav_graph) { inclusive = true } }
                )
            }.onFailure {
                Toast.makeText(requireContext(), "Ошибка авторизации", Toast.LENGTH_SHORT).show()
            }
        }


    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun openUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }


}