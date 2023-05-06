package com.example.booktesttask.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import com.example.booktesttask.R
import com.example.booktesttask.databinding.FragmentTinderBinding
import com.example.booktesttask.models.book.BookAdapter
import com.example.booktesttask.utils.factory
import com.yuyakaido.android.cardstackview.CardStackLayoutManager
import com.yuyakaido.android.cardstackview.CardStackListener
import com.yuyakaido.android.cardstackview.CardStackView
import com.yuyakaido.android.cardstackview.Direction

class TinderFragment: Fragment() {
    private lateinit var binding: FragmentTinderBinding
    private lateinit var adapter: BookAdapter
    private lateinit var manager: CardStackLayoutManager
    private val viewModel: TinderViewModel by viewModels{ factory() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTinderBinding.inflate(inflater, container, false)
        adapter = BookAdapter()
        manager = CardStackLayoutManager(requireContext(), object: CardStackListener{
            override fun onCardDragging(direction: Direction?, ratio: Float) {
            }

            override fun onCardSwiped(direction: Direction?) {
                viewModel.directionToast(context!!, direction!!)
                if (manager.topPosition == viewModel.getSize()) {
                    Toast.makeText(requireContext(), "Last card", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCardRewound() {
            }

            override fun onCardCanceled() {
            }

            override fun onCardAppeared(view: View?, position: Int) {
            }

            override fun onCardDisappeared(view: View?, position: Int) {
            }

        })

        manager.setVisibleCount(3)
        manager.setTranslationInterval(0.6f)
        manager.setScaleInterval(0.8f)
        manager.setDirections(Direction.FREEDOM)
        binding.cardStackView.layoutManager = manager
        binding.cardStackView.adapter = adapter
        binding.cardStackView.itemAnimator = DefaultItemAnimator()

        observeChanges()

        return binding.root
    }

    private fun observeChanges() = viewModel.books.observe(viewLifecycleOwner) {
        adapter.books = it
    }
}