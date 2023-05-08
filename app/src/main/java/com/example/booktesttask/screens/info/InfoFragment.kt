package com.example.booktesttask.screens.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.booktesttask.databinding.FragmentInfoBinding
import com.example.booktesttask.models.user.User
import com.example.booktesttask.utils.factory

class InfoFragment: Fragment() {
    private lateinit var binding: FragmentInfoBinding
    private val viewModel: InfoViewModel by viewModels{ factory() }
    private var user: User? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentInfoBinding.inflate(inflater, container, false)
        binding.tryAgainButton.setOnClickListener{ viewModel.getUser()}
        observeAccountDetails()
        observeState()
        viewModel.getUser()
        return binding.root
    }

    private fun observeAccountDetails() {
        viewModel.user.observe(viewLifecycleOwner) { user ->
            if (user == null) return@observe
            binding.liked.text = user.favorite_books?.size.toString()
            binding.disliked.text = user.dislike_books?.size.toString()
        }
    }

    private fun observeState() = viewModel.state.observe(viewLifecycleOwner) {
        binding.tryAgainContainer.visibility = if(it.apiFailInfo) View.VISIBLE else View.INVISIBLE
        binding.progressBar.visibility = if (it.showProgress) View.VISIBLE else View.INVISIBLE
    }
}