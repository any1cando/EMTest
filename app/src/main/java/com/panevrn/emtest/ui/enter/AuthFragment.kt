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
import com.panevrn.emtest.viewmodel.AuthViewModel
import com.panevrn.emtest.viewmodel.states.LoadState
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AuthFragment : Fragment() {

    private val viewModel: AuthViewModel by viewModels()
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
        binding.btnOkAuth.setOnClickListener {
            openUrl(url = "https://vk.com/")
        }

        // 2-ой кейс
        binding.btnOkAuth.setOnClickListener {
            openUrl("https://ok.ru/")
        }

        // Переход к фрагменту регистрации
        binding.tvRegister.setOnClickListener {
            findNavController().navigate(R.id.action_authFragment_to_registerFragment)
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


        // Проверяем, мониторя состояние авторизации. От этого будут зависеть: виден ли прогресс
        // бар и можно ли нажать на кнопку "Вход"
        viewModel.authState.observe(viewLifecycleOwner) { state ->
            when(state) {
                is LoadState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.btnLogin.isEnabled = false
                }
                is LoadState.Success -> {
                    viewModel.resetAuthState()

                    findNavController().navigate(
                        R.id.action_authFragment_to_mainFragment,
                        null,
                        navOptions { popUpTo(R.id.nav_graph) { inclusive = true } }
                    )
                }
                is LoadState.Error -> {
                    viewModel.resetAuthState()
                    Toast.makeText(requireContext(), state.message ?: "Ошибка авторизации", Toast.LENGTH_SHORT).show()
                }
                is LoadState.Idle -> {
                    binding.progressBar.visibility = View.GONE
                    binding.btnLogin.isEnabled = true
                }
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