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
import com.nghycp.fyp_auction_system.admin.ModelReturn
import com.nghycp.fyp_auction_system.admin.ReturnAdapter
import com.nghycp.fyp_auction_system.databinding.FragmentViewReturnBinding


class ViewReturn : Fragment() {

    private lateinit var binding: FragmentViewReturnBinding

    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var showReturnList: ArrayList<ModelReturnNN>

    private lateinit var adapterReturn: AdapterReturn

    private lateinit var recyclerView: RecyclerView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true)
        binding = FragmentViewReturnBinding.inflate(inflater, container, false)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        firebaseAuth = FirebaseAuth.getInstance()
        recyclerView = view.findViewById(R.id.RecyclerViewPurchase)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)

        showReturnList = arrayListOf<ModelReturnNN>()

        loadShow()

    }

    private fun loadShow() {
        showReturnList = ArrayList()

        val ref =
            Firebase.database("https://artwork-e6a68-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("Return")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                showReturnList.clear()
                for (ds in snapshot.children) {
                    //for(snap in ds.children){

                    val model = ds.getValue(ModelReturnNN::class.java)

                    showReturnList.add(model!!)
                    //}
                }

                val context = context
                if (context != null) {

                    val user = firebaseAuth.currentUser
                    val uid = user!!.uid

                    val filteredList = showReturnList.filter { it.uid == uid }

                    adapterReturn = AdapterReturn(context!!, filteredList as ArrayList<ModelReturnNN>)
                    recyclerView.adapter = adapterReturn
                }
            }


            override fun onCancelled(error: DatabaseError) {
                try {

                } catch (e: Exception) {
                    Log.d("ccc", e.toString())
                }
            }
        })
    }
}