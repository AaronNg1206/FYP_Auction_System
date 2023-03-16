package com.nghycp.fyp_auction_system

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.nghycp.fyp_auction_system.databinding.FragmentSalesSelectionBinding


class salesSelection : Fragment() {

    private var _binding: FragmentSalesSelectionBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        _binding = FragmentSalesSelectionBinding.inflate(inflater, container, false)

        binding.buttonBid.setOnClickListener {
            findNavController().navigate(R.id.action_sellOption_to_fragmentBidSellerForm2)
        }
        binding.buttonGo.setOnClickListener {
            findNavController().navigate(R.id.action_sellOption_to_customer_Add_Artwork)
        }
        return binding.root
    }

}