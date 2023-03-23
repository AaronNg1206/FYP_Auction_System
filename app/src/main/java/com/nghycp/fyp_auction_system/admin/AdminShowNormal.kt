package com.nghycp.fyp_auction_system.admin

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.nghycp.fyp_auction_system.R
import com.nghycp.fyp_auction_system.customer.ArtworkAdapter
import com.nghycp.fyp_auction_system.customer.ModelArtwork
import com.nghycp.fyp_auction_system.databinding.FragmentAdminShowNormalBinding
import com.nghycp.fyp_auction_system.databinding.FragmentArtworkDisplayBinding


class AdminShowNormal : Fragment() {

    private lateinit var binding: FragmentAdminShowNormalBinding
    private lateinit var artworkList: ArrayList<ModelNormal>
    private lateinit var ShowNormalAdapter: ShowNormalAdapter
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var recyclerView: RecyclerView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentAdminShowNormalBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        firebaseAuth = FirebaseAuth.getInstance()
        recyclerView = view.findViewById(R.id.RecyclerViewShow)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)

        artworkList = arrayListOf<ModelNormal>()

        showProduct()

    }

    private fun showProduct() {
        artworkList = ArrayList()

        val ref = Firebase.database("https://artwork-e6a68-default-rtdb.asia-southeast1.firebasedatabase.app/")
            .getReference("artwork")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                artworkList.clear()
                for (ds in snapshot.children){
                    val model = ds.getValue(ModelNormal::class.java)

                    artworkList.add(model!!)
                }
                val context = context
                if (context != null) {
                    ShowNormalAdapter = ShowNormalAdapter(context!!, artworkList)

                    recyclerView.adapter = ShowNormalAdapter
                }

            }
            override fun onCancelled(error: DatabaseError) {
                try{

                }catch(e: Exception) {
                    Log.d("c",e.toString())
                }
            }

        })
    }


}