package com.example.td1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.td1.databinding.FragmentPhotoBinding
import com.squareup.picasso.Picasso
import java.util.zip.Inflater

class PhotoFragment : Fragment(){
    private var image : String? = null
    lateinit var binding : FragmentPhotoBinding

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        arguments?.let{
            image = it.getString(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container:ViewGroup?,
        savedInstanceState: Bundle?
    ):View?{
        binding = FragmentPhotoBinding.inflate(inflater, container, false)
        Picasso.get().load(image).into(binding.imageView2)
        return binding.root
    }

    companion object {
        val ARG_PARAM1 = "ARG_PARAM"

        fun newInstance(image: String) =
            PhotoFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, image)
                }
            }
    }
}