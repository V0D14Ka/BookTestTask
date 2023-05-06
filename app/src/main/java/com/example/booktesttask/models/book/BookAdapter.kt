package com.example.booktesttask.models.book

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.booktesttask.R
import com.example.booktesttask.databinding.ItemBookBinding
import com.yuyakaido.android.cardstackview.CardStackView

class BookAdapter: RecyclerView.Adapter<BookAdapter.BookViewHolder>() {
    class BookViewHolder(
        val binding: ItemBookBinding
    ) : RecyclerView.ViewHolder(binding.root)

    var books: List<Book> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemBookBinding.inflate(inflater, parent, false)

//        binding.root.setOnClickListener(this)
//        binding.moreImageViewButton.setOnClickListener(this)

        return BookViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return books.size
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        with(holder.binding) {
            val item = books[position]
            itemName.text = item.name
            itemAge.text = item.author
            itemCity.text = item.description
            if(item.preview.isNotBlank()) {
                Glide.with(itemImage)
                    .load(item.preview)
                    .circleCrop()
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background)
                    .into(itemImage)
            }else {
                itemImage.setImageResource(R.drawable.ic_launcher_background)
            }
        }
    }
}