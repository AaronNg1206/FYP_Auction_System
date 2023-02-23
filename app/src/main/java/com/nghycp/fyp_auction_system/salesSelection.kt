package com.nghycp.fyp_auction_system

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.nghycp.fyp_auction_system.databinding.FragmentSalesSelectionBinding


class salesSelection : Fragment() {

    private lateinit var binding: FragmentSalesSelectionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = FragmentSalesSelectionBinding.inflate(layoutInflater)

        binding.btnBid.setOnClickListener {
            //findNavController().navigate(R.id.action_sellOption_to_fragmentSellerSell2)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sales_selection, container, false)
    }

}