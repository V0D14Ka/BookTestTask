package com.example.booktesttask.models.book

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.booktesttask.R
import com.example.booktesttask.databinding.ItemBookBinding
import com.yuyakaido.android.cardstackview.CardStackListener

class BookAdapter (
        ): RecyclerView.Adapter<BookAdapter.BookViewHolder>() {
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

        return BookViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return books.size
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        with(holder.binding) {
            val item = books[position]
            holder.itemView.tag = item
            itemName.text = item.name
            itemAuthor.text = item.author
            itemGenre.text = item.genre
            if(item.preview.isNotBlank()) {
                Glide.with(itemImage)
                    .load(item.preview)
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background)
                    .into(itemImage)
            }else {
                itemImage.setImageResource(R.drawable.ic_launcher_background)
            }
        }
    }
//    override fun onClick(v: View) {
//        val post = v.tag as Book
//        when(v.id) {
//            R.id.moreImageViewButton -> {
//                actionListener.onPostFavorite(post)
//            }
//            else -> {
//                actionListener.onPostDetails(post)
//            }
//        }
//    }
}