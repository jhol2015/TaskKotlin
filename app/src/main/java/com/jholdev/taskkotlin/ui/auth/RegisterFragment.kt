package com.jholdev.taskkotlin.ui. auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.jholdev.taskkotlin.R
import com.jholdev.taskkotlin.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = Firebase.auth

        initClicks()
    }

    private fun initClicks(){
        binding.btnRegister.setOnClickListener{ validateData() }
    }

    private fun validateData(){
        val email = binding.edtEmail.text.toString().trim()
        val senha = binding.edtSenha.text.toString().trim()

        if(email.isNotEmpty()) {
            if (senha.isNotEmpty()) {
                binding.progressBar.isVisible = true

                registerUser(email, senha)
            } else {
                Toast.makeText(requireContext(), "Informe a sua senha", Toast.LENGTH_SHORT).show()
            }
        }else{
                Toast.makeText(requireContext(), "Informe seu email", Toast.LENGTH_SHORT).show()
        }

    }

    private fun registerUser(email: String, password: String){
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    findNavController().navigate(R.id.action_registerFragment_to_homeFragment)
                } else {
                    binding.progressBar.isVisible = false
                }
            }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}