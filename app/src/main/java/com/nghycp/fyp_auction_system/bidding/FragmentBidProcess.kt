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

        val args = this.arguments

        val name= args?.get("name")
        val nameTextView = binding.showName
        nameTextView.text = name.toString()

        val img= args?.get("img")
        Glide.with(this@FragmentBidProcess)
            .load(img.toString())
            .placeholder(R.drawable.user)
            .into(binding.imgShow)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val expTimestamp = arguments?.getLong("expDate")
        val now = System.currentTimeMillis()
        val millisLeft = expTimestamp?.minus(now)
        object : CountDownTimer(millisLeft!!, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                // Update the UI with the remaining time
                val remainingTime = millisUntilFinished / 1000
                val remainingDays = remainingTime / (24 * 60 * 60)
                val remainingHours = (remainingTime % (24 * 60 * 60)) / (60 * 60)
                val remainingMinutes = (remainingTime % (60 * 60)) / 60
                val remainingSeconds = remainingTime % 60
                val remainingTimeString = "$remainingDays:$remainingHours:$remainingMinutes:$remainingSeconds"
                binding.timer.text = remainingTimeString
            }

            override fun onFinish() {
                // Update the UI when the countdown finishes
                binding.timer.text = "Expired"
            }
        }.start()

    }

}