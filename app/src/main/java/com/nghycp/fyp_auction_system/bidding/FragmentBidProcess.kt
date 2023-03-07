package com.nghycp.fyp_auction_system.bidding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.nghycp.fyp_auction_system.R
import com.nghycp.fyp_auction_system.databinding.FragmentBidProcessBinding

class FragmentBidProcess : Fragment() {

    private var _binding: FragmentBidProcessBinding? = null

    private lateinit var firebaseAuth: FirebaseAuth

    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        setHasOptionsMenu(true)

        _binding = FragmentBidProcessBinding.inflate(inflater,container,false)

//        val args = this.arguments
//        val img = args?.get("img")
//
//        Glide.with(this@FragmentBidProcess)
//            .load(img.toString())
//            .placeholder(R.drawable.example)
//            .into(binding.imageView2)

        return binding.root
    }

}