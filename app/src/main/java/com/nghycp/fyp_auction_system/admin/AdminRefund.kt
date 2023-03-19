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
import com.nghycp.fyp_auction_system.bidding.BidShowAdapter
import com.nghycp.fyp_auction_system.bidding.ModelBid
import com.nghycp.fyp_auction_system.databinding.FragmentAdminRefundBinding
import com.nghycp.fyp_auction_system.databinding.FragmentViewPurchaseBinding
import com.nghycp.fyp_auction_system.history_return.AdapterHistory
import com.nghycp.fyp_auction_system.history_return.ModelShow


class AdminRefund : Fragment() {

    private lateinit var binding : FragmentAdminRefundBinding

    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var showReturnList: ArrayList<ModelReturn>

    private lateinit var returnAdapter: ReturnAdapter

    private lateinit var recyclerView: RecyclerView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true)
        binding = FragmentAdminRefundBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        firebaseAuth = FirebaseAuth.getInstance()
        recyclerView = view.findViewById(R.id.RecyclerViewReturn)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)

        showReturnList = arrayListOf<ModelReturn>()


        loadShow()

    }

    private fun loadShow() {
        showReturnList = ArrayList()

        val ref = Firebase.database("https://artwork-e6a68-default-rtdb.asia-southeast1.firebasedatabase.app/")
            .getReference("Return")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                showReturnList.clear()
                for (ds in snapshot.children){
                    //for(snap in ds.children){

                    val model = ds.getValue(ModelReturn::class.java)

                    showReturnList.add(model!!)
                    //}
                }

                val context = context
                if (context != null){

                    returnAdapter = ReturnAdapter(context!!,showReturnList)

                    recyclerView.adapter = returnAdapter
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