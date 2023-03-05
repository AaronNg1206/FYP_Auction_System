package com.nghycp.fyp_auction_system.customer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.nghycp.fyp_auction_system.R
import com.nghycp.fyp_auction_system.databinding.FragmentArtworkDetailsBinding


class artworkDetailsFragment : Fragment() {

    private var _binding : FragmentArtworkDetailsBinding? = null

    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        setHasOptionsMenu(true)
        _binding = FragmentArtworkDetailsBinding.inflate(inflater,container,false)

        val args = this.arguments

        val name= args?.get("artName")
        val nameTextView = binding.showProduct
        nameTextView.text = name.toString()

        val price= args?.get("artPrice")
        val priceTextView = binding.showPricing
        priceTextView.text = price.toString()

        val desc= args?.get("artDescription")
        val descTextView = binding.showDesc
        descTextView.text = desc.toString()

        val artist= args?.get("artAuthor")
        val artistTextView = binding.showAuthor
        artistTextView.text = artist.toString()

        val image= args?.get("artImage")
        //val imgTextView = binding.imageProduct

        Glide.with(this@artworkDetailsFragment)
            .load(image.toString())
            .placeholder(R.drawable.artwork_placeholder)
            .into(binding.imageProduct)

        return binding.root

    }


    }
