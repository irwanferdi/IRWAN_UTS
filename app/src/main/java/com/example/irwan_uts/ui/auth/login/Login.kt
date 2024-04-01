package com.example.irwan_uts.ui.auth.login

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.irwan_uts.MainActivity
import com.example.irwan_uts.databinding.FragmentLoginBinding
import com.example.irwan_uts.R
import com.example.irwan_uts.data.local.UserData
import kotlinx.coroutines.launch

class Login : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        val textUsernameUser: TextView = binding.usernameEditText
        val textPasswordUser: TextView = binding.passwordEditText

        binding.loginButton.setOnClickListener {
            val username = textUsernameUser.text.toString()
            val password = textPasswordUser.text.toString()

            lifecycleScope.launch {
                viewModel.getUser.observe(viewLifecycleOwner) { userData: UserData ->
                    if (username == userData.username && password == userData.password) {
                        viewModel.saveToken("a")
                        val intent = Intent(requireContext(), MainActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(requireContext(), "Invalid data", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        binding.registerButton.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
