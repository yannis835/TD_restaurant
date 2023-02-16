package com.example.td1

import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class PhotoAdapter (val images: List<String>,private val detailActivity3: DetailActivity3, activity: AppCompatActivity): FragmentStateAdapter(activity){

    override fun getItemCount(): Int = images.size

    override fun createFragment(position: Int): Fragment {
        return PhotoFragment.newInstance(images[position])
        val imageView = ImageView(detailActivity3)
    }
}