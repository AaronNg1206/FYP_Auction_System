package com.nghycp.fyp_auction_system.admin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nghycp.fyp_auction_system.R
import com.nghycp.fyp_auction_system.databinding.FragmentAdminHomePageBinding
import com.nghycp.fyp_auction_system.databinding.FragmentUserHomePageBinding


class AdminHomePage : Fragment() {

    private lateinit var binding: FragmentAdminHomePageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        setHasOptionsMenu(true)

        binding = FragmentAdminHomePageBinding.inflate(inflater, container, false)
        return binding.root

    }

}