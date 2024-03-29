package com.nghycp.fyp_auction_system.admin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.nghycp.fyp_auction_system.R
import com.nghycp.fyp_auction_system.bidding.BidHomePageAdapter
import com.nghycp.fyp_auction_system.bidding.ModelBid
import com.nghycp.fyp_auction_system.customer.ModelArtwork
import com.nghycp.fyp_auction_system.customer.homePageAdapter
import com.nghycp.fyp_auction_system.databinding.FragmentAdminHomePageBinding


class AdminHomePage : Fragment() {

    private lateinit var binding: FragmentAdminHomePageBinding
    private lateinit var artworkList: ArrayList<ModelArtwork>
    private lateinit var homePageAdapter: homePageAdapter
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var recyclerViewhomePage: RecyclerView
    private lateinit var recyclerViewBidHomePage: RecyclerView

    private lateinit var bidArrayList: ArrayList<ModelBid>
    private lateinit var bidHomePageAdapter: BidHomePageAdapter

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        firebaseAuth = FirebaseAuth.getInstance()
        recyclerViewhomePage = view.findViewById(R.id.recyclerViewhomePage)
        recyclerViewhomePage.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)
        recyclerViewhomePage.setHasFixedSize(true)

        recyclerViewBidHomePage = view.findViewById(R.id.recyclerViewBidHome)
        recyclerViewBidHomePage.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)
        recyclerViewBidHomePage.setHasFixedSize(true)

        artworkList = arrayListOf<ModelArtwork>()

        showProduct()
        loadBid()
//        binding.textViewHomePage.setOnClickListener {
//            findNavController().navigate(R.id.action_fragmentUserHomePage_to_textTimer)
//        }

    }
    private fun showProduct() {
        artworkList = ArrayList()

        val ref = Firebase.database("https://artwork-e6a68-default-rtdb.asia-southeast1.firebasedatabase.app/")
            .getReference("artwork")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                artworkList.clear()
                for (ds in snapshot.children){
                    val model = ds.getValue(ModelArtwork::class.java)

                    artworkList.add(model!!)
                }
                homePageAdapter = homePageAdapter(context!!,artworkList)

                recyclerViewhomePage.adapter = homePageAdapter
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
    private fun loadBid() {
        bidArrayList = ArrayList()

        val ref = Firebase.database("https://artwork-e6a68-default-rtdb.asia-southeast1.firebasedatabase.app/")
            .getReference("Product")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                bidArrayList.clear()
                for (ds in snapshot.children){

                    val model = ds.getValue(ModelBid::class.java)

                    bidArrayList.add(model!!)

                }

                bidHomePageAdapter = BidHomePageAdapter(context!!,bidArrayList)

                recyclerViewBidHomePage.adapter = bidHomePageAdapter
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

}