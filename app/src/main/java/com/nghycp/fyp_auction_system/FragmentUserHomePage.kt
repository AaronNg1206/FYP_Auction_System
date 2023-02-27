package com.nghycp.fyp_auction_system

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.nghycp.fyp_auction_system.databinding.FragmentUserHomePageBinding

class FragmentUserHomePage : Fragment() {

    private lateinit var binding: FragmentUserHomePageBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = FragmentUserHomePageBinding.inflate(layoutInflater)

        binding.ViewMoreBidding.setOnClickListener{
            //findNavController().navigate(R.id.action_fragmentUserHomePage_to_fragmentBidSellerForm)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_home_page, container, false)
    }

}
