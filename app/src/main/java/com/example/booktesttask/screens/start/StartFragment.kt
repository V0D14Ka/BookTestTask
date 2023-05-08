package com.example.booktesttask.screens.start

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.booktesttask.databinding.FragmentStartBinding
import com.example.booktesttask.utils.factory

class StartFragment: Fragment() {
    private lateinit var binding: FragmentStartBinding
    private val viewModel: StartViewModel by viewModels{ factory() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentStartBinding.inflate(inflater, container, false)
        return binding.root
    }
}