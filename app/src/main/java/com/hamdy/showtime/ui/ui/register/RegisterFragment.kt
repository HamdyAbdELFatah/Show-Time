package com.hamdy.showtime.ui.ui.register

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.hamdy.showtime.R
import com.hamdy.showtime.databinding.LoginFragmentBinding
import com.hamdy.showtime.databinding.RegisterFragmentBinding

class RegisterFragment : Fragment() {

    private lateinit var viewModel: RegisterViewModel
    private lateinit var binding : RegisterFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding= RegisterFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)
        binding.Signin.setOnClickListener {
            it.findNavController().navigate(R.id.action_registerFragment_to_loginFragment,null,null,null)
        }

    }

}