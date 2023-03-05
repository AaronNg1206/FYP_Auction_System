package com.nghycp.fyp_auction_system

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.nghycp.fyp_auction_system.customer.ArtworkAdapter
import com.nghycp.fyp_auction_system.customer.ModelArtwork
import com.nghycp.fyp_auction_system.databinding.FragmentArtworkDisplayBinding


class FragmentArtworkDisplay : Fragment() {

    private lateinit var binding:FragmentArtworkDisplayBinding
    private lateinit var artworkList: ArrayList<ModelArtwork>
    private lateinit var artworkAdapter: ArtworkAdapter
    private lateinit var firebaseAuth: FirebaseAuth
    //private lateinit var database: FirebaseDatabase
    //private lateinit var artworkRef: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        binding = FragmentArtworkDisplayBinding.inflate(inflater, container, false)

        firebaseAuth = FirebaseAuth.getInstance()
        showProduct()
        return binding.root
    }


    private fun showProduct() {
        artworkList = ArrayList()

        val ref = Firebase.database("https://artwork-e6a68-default-rtdb.asia-southeast1.firebasedatabase.app/")
            .getReference("artwork")
        ref.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                artworkList.clear()
                for (ds in snapshot.children){
                    val model = ds.getValue(ModelArtwork::class.java)

                    artworkList.add(model!!)
                }

                artworkAdapter = ArtworkAdapter(context!!,artworkList)

                binding.RecyclerViewArtworkShow.adapter = artworkAdapter


            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}
