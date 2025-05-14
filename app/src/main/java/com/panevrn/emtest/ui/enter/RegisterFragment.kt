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
import com.panevrn.emtest.databinding.FragmentRegisterBinding
import com.panevrn.emtest.viewmodel.AuthViewModel
import com.panevrn.emtest.viewmodel.RegisterViewModel
import com.panevrn.emtest.viewmodel.states.LoadState
import dagger.hilt.android.AndroidEntryPoint

// На этот фрагмент пользователь может попасть только из AuthFragment
@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private val viewModel: RegisterViewModel by viewModels()
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 1-ый кейс
        binding.btnVkRegister.setOnClickListener {
            openUrl(url = "https://vk.com/")
        }

        // 2-ой кейс
        binding.btnOkRegister.setOnClickListener {
            openUrl("https://ok.ru/")
        }

        // Если пользователь нажимает на "Войти", если у него уже есть аккаунт, то просто возвращаемся на AuthFragment
        binding.tvAuth.setOnClickListener {
            findNavController().popBackStack()
        }

        // Обновляем поле в ViewModel, если меняем поле почты
        binding.etEmailRegister.doOnTextChanged { email, _, _, _ ->
            viewModel.onEmailChanged(email.toString())
        }

        // Обновляем поле в ViewModel, если меняем поле пароля
        binding.etPasswordRegister.doOnTextChanged { password, _, _, _ ->
            viewModel.onPasswordChanged(password.toString())
        }

        // Обновляем поле в ViewModel, если меняем поле повтора пароля
        binding.etPasswordRepeatRegister.doOnTextChanged { repeatPassword, _, _, _ ->
            viewModel.onRepeatPasswordChanged(repeatPassword.toString())
        }

        // Доступна ли кнопка для нажатия на кнопку "Регистрация" (все поля должны быть валидны)
        viewModel.isRegisterEnabled.observe(viewLifecycleOwner) { isEnabled ->
            binding.btnRegister.isEnabled = isEnabled
        }

        // Нажимая на кнопку, мы запускаем метод register() из RegisterViewModel (отправка логин/пароль)
        binding.btnRegister.setOnClickListener {
            viewModel.register()
        }

        viewModel.registerState.observe(viewLifecycleOwner) { state ->
            when(state) {
                is LoadState.Loading -> {
                    binding.progressBarRegister.visibility = View.VISIBLE
                    binding.btnRegister.isEnabled = false
                }
                is LoadState.Success -> {
                    viewModel.resetRegisterState()
                    Toast.makeText(requireContext(), "Успешная регистрация", Toast.LENGTH_SHORT).show()
                    findNavController().popBackStack()
                }
                is LoadState.Error -> {
                    viewModel.resetRegisterState()
                    Toast.makeText(requireContext(), state.message ?: "Ошибка регистрации", Toast.LENGTH_SHORT).show()
                }
                is LoadState.Idle -> {
                    binding.progressBarRegister.visibility = View.GONE
                    binding.btnRegister.isEnabled = true
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