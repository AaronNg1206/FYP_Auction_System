package com.nghycp.fyp_auction_system.bidding

import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.nghycp.fyp_auction_system.R
import com.nghycp.fyp_auction_system.databinding.FragmentAuctionShowProductBinding
import com.nghycp.fyp_auction_system.databinding.FragmentBidProductBinding
import kotlinx.android.synthetic.main.bid_layout.*
import kotlinx.android.synthetic.main.fragment_bid_process.*
import kotlin.math.exp

class FragmentBidProduct : Fragment() {

    private var _binding: FragmentBidProductBinding? = null

    private lateinit var firebaseAuth: FirebaseAuth

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        setHasOptionsMenu(true)

        _binding = FragmentBidProductBinding.inflate(inflater,container,false)

        val args = this.arguments
        val desc= args?.get("desc")
        val descTextView = binding.descShow
        descTextView.text = desc.toString()

        val artist= args?.get("artist")
        val artistTextView = binding.artistShow
        artistTextView.text = artist.toString()

        val price= args?.get("price")
        val priceTextView = binding.priceShow
        priceTextView.text = price.toString()

        val name= args?.get("name")
        val nameTextView = binding.nameShow
        nameTextView.text = name.toString()

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
                binding.expTimer.text = remainingTimeString
            }

            override fun onFinish() {
                // Update the UI when the countdown finishes
                binding.expTimer.text = "Expired"
            }
        }.start()

        val img= args?.get("img")
        //val imgTextView = binding.artImage
        //imgTextView. = img.toString()

        Glide.with(this@FragmentBidProduct)
            .load(img.toString())
            .placeholder(R.drawable.user)
            .into(binding.artImage)

        binding.btnGo.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentBidProduct_to_fragmentBidProcess)
        }

        return binding.root
    }

}