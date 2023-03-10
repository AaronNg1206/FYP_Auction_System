package com.nghycp.fyp_auction_system.bidding

import android.content.ContentValues.TAG
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.nghycp.fyp_auction_system.R
import com.nghycp.fyp_auction_system.databinding.FragmentBidProcessBinding
import java.lang.Exception
import java.sql.Date
import java.util.concurrent.TimeUnit

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

        val name =  requireArguments().getString("name").toString()
        binding.showName.setText(name)

        val expDate =  requireArguments().getString("expDate").toString()
        binding.timer.setText(expDate)

        val img = requireArguments().getString("img").toString()

        Glide.with(this@FragmentBidProcess)
            .load(img.toString())
            .placeholder(R.drawable.user)
            .into(binding.imgShow)

        return binding.root
    }

}