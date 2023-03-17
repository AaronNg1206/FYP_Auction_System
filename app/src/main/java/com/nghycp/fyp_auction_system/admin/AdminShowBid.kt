package com.nghycp.fyp_auction_system.admin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.nghycp.fyp_auction_system.R
import com.nghycp.fyp_auction_system.bidding.BidShowAdapter
import com.nghycp.fyp_auction_system.bidding.ModelBid
import com.nghycp.fyp_auction_system.databinding.FragmentAdminShowBidBinding
import com.nghycp.fyp_auction_system.databinding.FragmentAuctionShowProductBinding


class AdminShowBid : Fragment() {

    private var _binding: FragmentAdminShowBidBinding? = null
    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var bidArrayList: ArrayList<ModedlBidAdmin>

    private lateinit var ShowAdapter: ShowAdapter

    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)

        _binding = FragmentAdminShowBidBinding.inflate(inflater, container, false)

        firebaseAuth = FirebaseAuth.getInstance()

        loadShow()

        return binding.root
    }

    private fun loadShow() {
        bidArrayList = ArrayList()

        val ref = Firebase.database("https://artwork-e6a68-default-rtdb.asia-southeast1.firebasedatabase.app/")
            .getReference("Product")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                bidArrayList.clear()
                for (ds in snapshot.children){
                    for(snap in ds.children){

                        val model = snap.getValue(ModedlBidAdmin::class.java)

                        bidArrayList.add(model!!)
                    }
                }

                val context = context
                if (context != null){

                    ShowAdapter = ShowAdapter(context!!,bidArrayList)

                    binding.recyclerviewAdminShow.adapter = ShowAdapter
                }

            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }


}