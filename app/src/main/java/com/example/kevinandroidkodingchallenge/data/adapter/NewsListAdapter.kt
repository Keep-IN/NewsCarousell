package com.example.kevinandroidkodingchallenge.data.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.core.data.response.NewsResponse
import com.example.core.data.response.NewsResponseItem
import com.example.kevinandroidkodingchallenge.databinding.NewsItemListBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class NewsListAdapter: RecyclerView.Adapter<NewsListAdapter.ViewHolder>() {
    private val data: MutableList<NewsResponseItem> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsListAdapter.ViewHolder {
        return ViewHolder(
            NewsItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }
    override fun onBindViewHolder(holder: NewsListAdapter.ViewHolder, position: Int) {
        holder.setData(data[position])
    }

    override fun getItemCount(): Int = data.size

    fun submitList(list: List<NewsResponseItem>){
        val initSize = itemCount
        data.clear()
        notifyItemRangeRemoved(0, initSize)
        data.addAll(list.sortedBy { it.timeCreated })
        notifyItemRangeInserted(0, data.size)
    }

    fun submitListRankSorted(list: List<NewsResponseItem>){
        val initSize = itemCount
        data.clear()
        notifyItemRangeRemoved(0, initSize)
        data.addAll(list.sortedBy { it.rank })
        notifyItemRangeInserted(0, data.size)
    }

    inner class ViewHolder(private val binding: NewsItemListBinding): RecyclerView.ViewHolder(binding.root) {
        fun setData(item: NewsResponseItem){
            binding.apply {
                tvNewsTitle.text = item.title
                tvNewsDesc.text = item.description
                tvNewsTime.text = getTimeAgo(item.timeCreated)
            }
            Glide.with(itemView.context)
                .load(item.bannerUrl)
                .into(binding.ivNewsBanner)
        }
    }
    private fun getTimeAgo(createdAt: Int): String {
        val timeInMillis = createdAt * 1000L
        val now = System.currentTimeMillis()

        val diffInMillis = now - timeInMillis
        val seconds = diffInMillis / 1000
        val minutes = seconds / 60
        val hours = minutes / 60
        val days = hours / 24
        val weeks = days / 7
        val months = weeks / 4
        val years = months / 12

        return when {
            seconds < 60 -> "${seconds.toInt()} seconds ago"
            minutes < 60 -> "${minutes.toInt()} minutes ago"
            hours < 24 -> if (hours in 1..1) {
                "1 hour ago"
            } else {
                "${hours.toInt()} hours ago"
            }
            days < 7 -> if (days in 1..1) {
                "1 day ago"
            } else {
                "${days.toInt()} days ago"
            }
            weeks < 4 -> if (weeks in 1..1) {
                "1 week ago"
            } else {
                "${weeks.toInt()} weeks ago"
            }
            months < 12 -> if (months in 1..1) {
                "1 month ago"
            } else {
                "${months.toInt()} months ago"
            }
            years == 1L -> "1 year ago"
            else -> SimpleDateFormat("dd MMM yyyy", Locale("id", "ID")).format(Date(timeInMillis))
        }
    }
}