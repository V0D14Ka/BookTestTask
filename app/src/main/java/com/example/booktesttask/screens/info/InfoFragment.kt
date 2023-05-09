package com.example.booktesttask.screens.info

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.booktesttask.databinding.FragmentInfoBinding
import com.example.booktesttask.utils.factory

class InfoFragment: Fragment() {
    private lateinit var binding: FragmentInfoBinding
    private val viewModel: InfoViewModel by viewModels{ factory() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel.getUser()
        binding = FragmentInfoBinding.inflate(inflater, container, false)
        binding.tryAgainButton.setOnClickListener{ viewModel.getUser()}
        binding.ShareButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_TEXT,viewModel.getShareInfo())
            val chooser = Intent.createChooser(intent,"Share using...")
            startActivity(chooser)
        }
        observeAccountDetails()
        observeState()
        return binding.root
    }

    private fun observeAccountDetails() {
        viewModel.user.observe(viewLifecycleOwner) { user ->
            if (user == null) return@observe
            binding.likedcount.text = user.favorite_books?.size.toString()
            binding.readcount.text = user.already_read_books?.size.toString()
            binding.topgenres.text = viewModel.getTopGenres()
        }
    }

    private fun observeState() = viewModel.state.observe(viewLifecycleOwner) {
        binding.tryAgainContainer.visibility = if(it.apiFailInfo) View.VISIBLE else View.INVISIBLE
        binding.progressBar.visibility = if (it.showProgress) View.VISIBLE else View.INVISIBLE
        binding.all.visibility = if (it.showProgress || it.apiFailInfo) View.INVISIBLE else View.VISIBLE
    }
}