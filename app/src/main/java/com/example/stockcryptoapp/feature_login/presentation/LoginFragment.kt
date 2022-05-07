package com.example.stockcryptoapp.feature_login.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.stockcryptoapp.R
import com.example.stockcryptoapp.databinding.FragmentLoginBinding


class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentLoginBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
    }

    private fun initViews() {

        binding.btnLogin.setOnClickListener {
            navigateToQuoteListFragment()
        }

        binding.btnRegister.setOnClickListener {
            navigateToRegister()
        }

    }

    private fun isUserValid(): Boolean {
        // This function should be replace with Firebase Authorization
        // If not time, just  leave it as it is
        val password = binding.etPassword.text.toString()

        return password.isNotBlank() && password.length >= 8
    }

    fun navigateToQuoteListFragment () {

        if (isUserValid()){
            binding.passwordTextInput.error = null
            findNavController().navigate(R.id.action_loginFragment_to_quoteListFragment)
        } else {
            binding.passwordTextInput.error = getString(R.string.incorrect_password)
        }
    }

    fun navigateToRegister() {
        findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
    }


}