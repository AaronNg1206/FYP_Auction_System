package com.nghycp.fyp_auction_system.customer

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.nghycp.fyp_auction_system.R
import com.nghycp.fyp_auction_system.databinding.FragmentArtworkDisplayBinding
import com.nghycp.fyp_auction_system.databinding.FragmentRecentAddBinding
import kotlinx.android.synthetic.main.fragment_recent_add_layout.*

class RecentAddFragment : Fragment() {
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var binding : FragmentRecentAddBinding
    private lateinit var artworkList: ArrayList<ModelArtwork>
    private lateinit var recentAddList: ArrayList<ModelArtwork>
    private lateinit var RecentAddAdapter: RecentAddAdapter
    private lateinit var database: FirebaseDatabase
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        binding = FragmentRecentAddBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        firebaseAuth = FirebaseAuth.getInstance()
        recyclerView = view.findViewById(R.id.recycleviewRecentAdded)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)

        artworkList = arrayListOf<ModelArtwork>()
        recentAddList = arrayListOf<ModelArtwork>()
        loadRecentAdd()

    }

    private fun loadRecentAdd() {
        val ref = Firebase.database("https://artwork-e6a68-default-rtdb.asia-southeast1.firebasedatabase.app/")
            .getReference("artwork").
            ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = firebaseAuth.currentUser
                val uid = user!!.uid
                //clear list before starting adding data into it
                artworkList.clear()
                for(ds in snapshot.children){
                    //get data as model
                    val model = ds.getValue(ModelArtwork::class.java)
                    //add to array list
                    artworkList.add(model!!)
                }
                val recentAddList: ArrayList<ModelArtwork> = ArrayList()
                for ( i in  artworkList.indices){
                    if (artworkList.get(i).uid  == uid){
                        recentAddList.add(artworkList.get(i))
                    }
                }
                         RecentAddAdapter = RecentAddAdapter(context!!, recentAddList)
                         binding.recycleviewRecentAdded.adapter = RecentAddAdapter
            }
            override fun onCancelled(error: DatabaseError) {}
        })
    }
}