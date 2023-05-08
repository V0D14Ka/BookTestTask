package com.example.booktesttask.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.booktesttask.databinding.FragmentInfoBinding
import com.example.booktesttask.databinding.FragmentTinderBinding
import com.example.booktesttask.models.book.BookAdapter
import com.example.booktesttask.utils.factory
import com.yuyakaido.android.cardstackview.CardStackLayoutManager

class InfoFragment: Fragment() {
    private lateinit var binding: FragmentInfoBinding
    private val viewModel: InfoViewModel by viewModels{ factory() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentInfoBinding.inflate(inflater, container, false)
        return binding.root
    }
}