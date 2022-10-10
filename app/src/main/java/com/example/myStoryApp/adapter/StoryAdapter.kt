package com.example.myStoryApp.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.example.myStoryApp.data.response.ResponseStory
import com.example.myStoryApp.databinding.ItemListStoryBinding
import com.example.myStoryApp.ui.detailStory.DetailStoryActivity
import com.example.myStoryApp.utils.convertToTimeAgo
import com.example.myStoryApp.utils.loadImageViaGlide

class StoryAdapter(private val listStory: List<ResponseStory.ListStoryItem>):
    RecyclerView.Adapter<StoryAdapter.ListViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListViewHolder = ListViewHolder(
        ItemListStoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(
        holder: ListViewHolder,
        position: Int
    )= holder.bind(listStory[position])

    override fun getItemCount(): Int = listStory.size

    inner class ListViewHolder(private val binding: ItemListStoryBinding): RecyclerView.ViewHolder(binding.root){
        fun bind (story: ResponseStory.ListStoryItem){
            with(binding){
                tvItemName.text = story.name
                tvItemCreatedAt.text = convertToTimeAgo(story.createdAt)
                itemView.apply {
                    context.loadImageViaGlide(story.photoUrl.toUri(), binding.imgStory)
                    setOnClickListener {
                        val intent = Intent(context, DetailStoryActivity::class.java)
                        intent.putExtra(DetailStoryActivity.EXTRA_DATA, story)
                        context.startActivity(intent)
                    }
                }
            }
        }
    }
}