package com.example.stockcryptoapp.feature_login.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.stockcryptoapp.R
import com.example.stockcryptoapp.databinding.FragmentRegisterBinding
import com.example.stockcryptoapp.feature_login.domain.UserManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding

    @Inject
    lateinit var userManager: UserManager

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
        binding.etUserName.text?.clear()
        binding.etPassword.text?.clear()
        binding.etRpPassword.text?.clear()

        binding.btnCancel.setOnClickListener {

            findNavController().navigateUp()

        }

        binding.btnRegister.setOnClickListener {
            navigateToQuoteListFragment()
        }
    }


    private fun navigateToQuoteListFragment() {

        if (validPassword().first){
            binding.passwordTextInput.isErrorEnabled = false
            binding.passwordRpTextInput.isErrorEnabled = false

            val userName = binding.etUserName.text.toString()
            val password = binding.etPassword.text.toString()
            if(userManager.isUserExist(userName)){
                binding.usernameTextInput.isErrorEnabled = true
                binding.usernameTextInput.error = "User already exists"
            }
            else {
                userManager.registerUser(userName, password)
                findNavController().navigate(R.id.action_registerFragment_to_quoteListFragment)

            }
        } else {
            val msg = validPassword().second

            binding.passwordTextInput.isErrorEnabled = true
            binding.passwordRpTextInput.isErrorEnabled = true

            binding.passwordTextInput.error = msg
            binding.passwordRpTextInput.error = msg

        }
    }
}