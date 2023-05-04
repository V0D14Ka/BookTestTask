package com.example.booktesttask.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.booktesttask.databinding.FragmentTinderBinding
import com.example.booktesttask.utils.factory

class TinderFragment: Fragment() {
    private lateinit var binding: FragmentTinderBinding
    //private lateinit var adapter:
    private val viewModel: TinderViewModel by viewModels{ factory() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTinderBinding.inflate(inflater, container, false)

        return binding.root
    }
}