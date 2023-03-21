package com.nghycp.fyp_auction_system.history_return

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
import com.nghycp.fyp_auction_system.customer.RecentAddAdapter
import com.nghycp.fyp_auction_system.databinding.FragmentViewPurchaseBinding


class ViewPurchase : Fragment() {

    private lateinit var binding : FragmentViewPurchaseBinding

    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var showArrayList: ArrayList<ModelShow>

    private lateinit var adapterHistory: AdapterHistory

    private lateinit var recyclerView: RecyclerView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        setHasOptionsMenu(true)
        binding = FragmentViewPurchaseBinding.inflate(inflater,container,false)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        firebaseAuth = FirebaseAuth.getInstance()
        recyclerView = view.findViewById(R.id.RecyclerViewPurchase)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)

        showArrayList = arrayListOf<ModelShow>()


        loadShow()

    }

    private fun loadShow() {
        showArrayList = ArrayList()

        val ref = Firebase.database("https://artwork-e6a68-default-rtdb.asia-southeast1.firebasedatabase.app/")
            .getReference("paid")
        ref.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()) {
                    val user = firebaseAuth.currentUser
                    val uid = user!!.uid
                    showArrayList.clear()
                    for (ds in snapshot.children) {

                        val model = ds.getValue(ModelShow::class.java)
                        if (model != null){
                            showArrayList.add(model!!)
                        }
                    }

                    val filteredList = showArrayList.filter { it.uid == uid }
                    adapterHistory = AdapterHistory(context!!, filteredList as ArrayList<ModelShow>)
                    recyclerView.adapter = adapterHistory

                }
            }


            override fun onCancelled(error: DatabaseError) {
                try{

                }catch(e: Exception) {
                    Log.d("ccc",e.toString())
                }
            }
        })
    }

}