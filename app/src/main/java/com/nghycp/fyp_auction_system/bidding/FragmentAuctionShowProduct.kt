package com.nghycp.fyp_auction_system.bidding

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.nghycp.fyp_auction_system.R
import com.nghycp.fyp_auction_system.databinding.FragmentAuctionShowProductBinding


class FragmentAuctionShowProduct : Fragment() {

    private var _binding: FragmentAuctionShowProductBinding? = null
    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var bidArrayList: ArrayList<ModelBid>

    private lateinit var bidShowAdapter: BidShowAdapter

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        setHasOptionsMenu(true)

        _binding = FragmentAuctionShowProductBinding.inflate(inflater,container,false)

        firebaseAuth = FirebaseAuth.getInstance()
        loadBid()

        return binding.root

    }

    private fun loadBid() {
        bidArrayList = ArrayList()

        val ref = Firebase.database("https://artwork-e6a68-default-rtdb.asia-southeast1.firebasedatabase.app/")
            .getReference("Product")
        ref.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                bidArrayList.clear()
                for (ds in snapshot.children){
                    for(snap in ds.children){

                    val model = snap.getValue(ModelBid::class.java)

                    bidArrayList.add(model!!)
                    }
                }

                val context = context
                if (context != null){

                    bidShowAdapter = BidShowAdapter(context!!,bidArrayList)

                    binding.recyclerviewJobShow.adapter = bidShowAdapter
                }

            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        // log out action not visible in registration page
        menu.findItem(R.id.action_logout).isVisible = false
    }
}
