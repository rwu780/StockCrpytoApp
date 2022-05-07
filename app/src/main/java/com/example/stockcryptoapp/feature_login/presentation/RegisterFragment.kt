package com.example.stockcryptoapp.feature_login.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.stockcryptoapp.R
import com.example.stockcryptoapp.databinding.FragmentLoginBinding
import com.example.stockcryptoapp.databinding.FragmentRegisterBinding


class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
    }


    private fun validPassword(): Pair<Boolean, String?> {
        val password = binding.etPassword.text.toString()
        val repeatedPassword = binding.etRpPassword.text.toString()

        if (password.isBlank() || repeatedPassword.isBlank())
            return Pair(false, "Password cannot be blank")

        if (password.length < 8 || repeatedPassword.length < 8)
            return Pair(false, "Invalid Password")

        if (password != repeatedPassword)
            return Pair(false, "Passwords do not match")

        return Pair(true, null)

    }


    private fun initViews() {
        binding.btnCancel.setOnClickListener {
            onCancelButtonPressed()
        }

        binding.btnRegister.setOnClickListener {
            navigateToQuoteListFragment()
        }
    }


    fun navigateToQuoteListFragment() {

        if (validPassword().first){
            binding.passwordTextInput.isErrorEnabled = false
            binding.passwordRpTextInput.isErrorEnabled = false

            findNavController().navigate(R.id.action_registerFragment_to_quoteListFragment)
        } else {
            val msg = validPassword().second

            binding.passwordTextInput.isErrorEnabled = true
            binding.passwordRpTextInput.isErrorEnabled = true

            binding.passwordTextInput.error = msg
            binding.passwordRpTextInput.error = msg

        }
    }

    fun onCancelButtonPressed() {
        findNavController().navigateUp()

    }
}