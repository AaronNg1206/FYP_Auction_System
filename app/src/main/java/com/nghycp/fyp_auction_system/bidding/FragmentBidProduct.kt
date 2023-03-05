package com.nghycp.fyp_auction_system.bidding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.nghycp.fyp_auction_system.R
import com.nghycp.fyp_auction_system.databinding.FragmentAuctionShowProductBinding
import com.nghycp.fyp_auction_system.databinding.FragmentBidProductBinding

class FragmentBidProduct : Fragment() {

    private var _binding: FragmentBidProductBinding? = null

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

        val img= args?.get("img")
        //val imgTextView = binding.artImage
        //imgTextView. = img.toString()

        Glide.with(this@FragmentBidProduct)
            .load(img.toString())
            .placeholder(R.drawable.user)
            .into(binding.artImage)

        return binding.root
    }

}