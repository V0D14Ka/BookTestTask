package com.example.booktesttask.screens.tinder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import com.example.booktesttask.databinding.FragmentTinderBinding
import com.example.booktesttask.models.book.Book
import com.example.booktesttask.models.book.BookAdapter
import com.example.booktesttask.utils.factory
import com.yuyakaido.android.cardstackview.CardStackLayoutManager
import com.yuyakaido.android.cardstackview.CardStackListener
import com.yuyakaido.android.cardstackview.Direction
import com.yuyakaido.android.cardstackview.StackFrom
import com.yuyakaido.android.cardstackview.SwipeableMethod

class TinderFragment: Fragment() {
    private lateinit var binding: FragmentTinderBinding
    private lateinit var adapter: BookAdapter
    private lateinit var manager: CardStackLayoutManager
    private val viewModel: TinderViewModel by viewModels{ factory() }
    private var item: Book? = null



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTinderBinding.inflate(inflater, container, false)
        binding.tryAgainButton.setOnClickListener{ viewModel.getRecommendations() }
        binding.newload.setOnClickListener { viewModel.getRecommendations() }
        adapter = BookAdapter()
        manager = CardStackLayoutManager(requireContext(), object: CardStackListener{
            override fun onCardDragging(direction: Direction?, ratio: Float) {
            }

            override fun onCardSwiped(direction: Direction?) {
//                viewModel.directionToast(context!!, direction!!)
                if (direction != null) {
                    viewModel.event(direction, item!!)
                }
            }

            override fun onCardRewound() {
            }

            override fun onCardCanceled() {
            }

            override fun onCardAppeared(view: View?, position: Int) {
            }

            override fun onCardDisappeared(view: View?, position: Int) {
                if (view != null) {
                    item = view.tag as Book
                }
            }

        })

        manager.setVisibleCount(3)
        manager.setTranslationInterval(0.6f)
        manager.setScaleInterval(0.8f)
        manager.setStackFrom(StackFrom.None)
        manager.setSwipeThreshold(0.3f)
        manager.setMaxDegree(20.0f)
        manager.setSwipeableMethod(SwipeableMethod.Manual)
        manager.setDirections(Direction.FREEDOM)
        binding.cardStackView.layoutManager = manager
        binding.cardStackView.adapter = adapter
        binding.cardStackView.itemAnimator = DefaultItemAnimator()
        observeState()
        observeChanges()

        return binding.root
    }

    private fun observeChanges() = viewModel.books.observe(viewLifecycleOwner) {
        adapter.books = it
    }

    private fun observeState() = viewModel.state.observe(viewLifecycleOwner) {
        binding.tryAgainContainer.visibility = if(it.apiFailInfo) View.VISIBLE else View.INVISIBLE
        binding.noBooksContainer.visibility = if(it.emptyList && !it.apiFailInfo) View.VISIBLE else View.INVISIBLE
        binding.progressBar.visibility = if (it.showProgress) View.VISIBLE else View.INVISIBLE
    }
}