package com.nghycp.fyp_auction_system.bidding

import android.app.ProgressDialog
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.nghycp.fyp_auction_system.R
import com.nghycp.fyp_auction_system.databinding.FragmentBidProcessBinding
import kotlinx.android.synthetic.main.fragment_bid_process.*

class FragmentBidProcess : Fragment() {

    private var _binding: FragmentBidProcessBinding? = null

    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var bidShowBidUserAdpter: ShowBidUserAdpter

    private lateinit var recyclerView: RecyclerView

    private lateinit var bidShowArrayList: ArrayList<ModelBidUser>

    private lateinit var progressDialog: ProgressDialog

    val database = Firebase.database

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        setHasOptionsMenu(true)

        _binding = FragmentBidProcessBinding.inflate(inflater, container, false)

        getHighBid()
        loadUser()

        firebaseAuth = FirebaseAuth.getInstance()
        progressDialog = ProgressDialog(context)
        progressDialog.setTitle("Please Wait...")
        progressDialog.setCanceledOnTouchOutside(false)

        val user = firebaseAuth.currentUser
        val uid = user!!.uid

        val userRef =
            Firebase.database("https://artwork-e6a68-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("Users").child(uid)
        userRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val userName = snapshot.child("name").value as String?

                binding.userName.setText(userName)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })


        return binding.root
    }

    private fun getHighBid() {

        val args = this.arguments

        val name = args?.get("name")
        val PID = args?.get("PID").toString()

        val showRef = FirebaseDatabase.getInstance().getReference("Bid")
        val ref2 = showRef.child(PID).orderByChild("price").limitToLast(1)
        //val key = showRef.key.toString()
        ref2.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                bidShowArrayList.clear()
                for (ds in snapshot.children) {
                    //val key2 = ds.key.toString()
                    //for (snap in ds.children){
                    //if(uid == snap.child("uid").value as String) {

                    //if (key2 == PID) {

                    val price = ds.child("price").value as String

                    binding.currentPrice.text = price.toString()
                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

    }

    private fun loadUser() {

        firebaseAuth = FirebaseAuth.getInstance()

        val args = this.arguments

        val PID = args?.get("PID").toString()

        bidShowArrayList = ArrayList()

        val showRef = FirebaseDatabase.getInstance().getReference("Bid")
        showRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                bidShowArrayList.clear()
                for (ds in snapshot.children) {
                    val key2 = ds.key.toString()

                    for (snap in ds.children) {
                        if (PID == key2) {
                            val model = snap.getValue(ModelBidUser::class.java)

                            bidShowArrayList.add(model!!)

                        }
                    }

                    var sortedList = bidShowArrayList.toMutableList()

                    sortedList.sortByDescending { it.price }

                    val context = context
                    if (context != null) {

                        bidShowBidUserAdpter = ShowBidUserAdpter(context, sortedList)

                        recyclerView.adapter = bidShowBidUserAdpter
                    }
                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = this.arguments

        val name = args?.get("name")
        val nameTextView = binding.showName
        nameTextView.text = name.toString()

        val price = args?.get("price")
        val priceTextView = binding.currentPrice
        priceTextView.text = price.toString()

        val img = args?.get("img")
        Glide.with(this@FragmentBidProcess)
            .load(img.toString())
            .placeholder(R.drawable.user)
            .into(binding.imgShow)

        val expTimestamp = arguments?.getLong("expDate")
        val now = System.currentTimeMillis()
        val millisLeft = expTimestamp?.minus(now)
        object : CountDownTimer(millisLeft!!, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                // Update the UI with the remaining time
                val remainingTime = millisUntilFinished / 1000
                val remainingDays = remainingTime / (24 * 60 * 60)
                val remainingHours = (remainingTime % (24 * 60 * 60)) / (60 * 60)
                val remainingMinutes = (remainingTime % (60 * 60)) / 60
                val remainingSeconds = remainingTime % 60
                val remainingTimeString =
                    "$remainingDays:$remainingHours:$remainingMinutes:$remainingSeconds"
                binding.timer.text = remainingTimeString
            }

            override fun onFinish() {
                // Update the UI when the countdown finishes
                binding.timer.text = "Expired"

                binding.boxPlace.visibility = View.GONE

                binding.btnPlace.visibility = View.GONE

                firebaseAuth = FirebaseAuth.getInstance()
                val user = firebaseAuth.currentUser
                val uid = user!!.uid

                val PID = args?.get("PID").toString()

                val showRef = FirebaseDatabase.getInstance().getReference("Bid")
                val ref2 = showRef.child(PID)

                var highest = 0
                var highestBidUser : String = ""
                ref2.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        for (ds in snapshot.children) {
                            //val key2 = ds.key.toString()
                            var value = ds.getValue() as Map <String, Object>
                            var currBid = (value["price"] as String).toInt()
                            if(currBid >= highest){
                                highest = currBid
                                highestBidUser = value["uid"] as String
                            }
                        }
                        if (highestBidUser == uid) {
                            binding.btnPayment.visibility = View.VISIBLE
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }
                })

                binding.btnPayment.setOnClickListener {
                    paymentProcess()
                }
            }
        }.start()

        recyclerView = view.findViewById(R.id.recycleViewShowUser)

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)
        bidShowArrayList = arrayListOf<ModelBidUser>()

        binding.btnPlace.setOnClickListener {
            validateBid()
        }

    }



    private var currentPrice = ""
    private var totalPrice = ""

    private fun validateBid() {

        currentPrice = binding.currentPrice.text.toString().trim()
        totalPrice = binding.boxPlace.text.toString().trim()

        if (totalPrice <= currentPrice) {
            binding.boxPlace.error = "Your price are not higher than current price ! "
        } else {
            saveBid()
        }

    }

    private var name = ""
    private var price = ""
    private var nameUser = ""


    private fun saveBid() {


        nameUser = binding.userName.text.toString().trim()
        name = binding.showName.text.toString().trim()
        price = binding.boxPlace.text.toString().trim()
        val args = this.arguments
        val PID = args?.get("PID").toString()
        val image = args?.get("img").toString()
        //val id = args?.get("id").toString()
        Glide.with(this@FragmentBidProcess).load(image)

        val hashMap = HashMap<String, Any>()

        //hashMap["id"] = id
        hashMap["uid"] = "${firebaseAuth.uid}"
        hashMap["name"] = name
        hashMap["price"] = price
        hashMap["img"] = image
        hashMap["userName"] = nameUser
        hashMap["type"] = "Bid"

        val ref =
            Firebase.database("https://artwork-e6a68-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("Bid")
        ref.child(PID).child("${firebaseAuth.uid}")
            .setValue(hashMap)
            .addOnSuccessListener {
                progressDialog.dismiss()
                Toast.makeText(context, "Place Successful", Toast.LENGTH_SHORT).show()
                //findNavController().navigate(R.id.action_fragmentBidProcess_to_auction)
                binding.currentPrice.text = price
            }
            .addOnFailureListener {
                progressDialog.dismiss()
                Toast.makeText(context, "Failed to bid this artwork", Toast.LENGTH_SHORT).show()
            }
    }
    private fun paymentProcess() {
        val args = this.arguments
        val PID = args?.get("PID").toString()
        val image = args?.get("img").toString()
        val ref = Firebase.database("https://artwork-e6a68-default-rtdb.asia-southeast1.firebasedatabase.app/")
            .getReference("Checkout")
        ref.removeValue()
        val hashMap = HashMap<String, Any>()
        hashMap["artName"] = name
        hashMap["artImage"] = image
        Log.d("ABC",currentPrice)
        hashMap["artPrice"] = price
        hashMap["uid"] = "${firebaseAuth.uid}"
        ref.child(PID)
            .setValue(hashMap)
            .addOnSuccessListener{
                Toast.makeText(context," Proceed to checkout", Toast.LENGTH_SHORT).show()
            }
            .addOnCanceledListener {  ->
                Toast.makeText(context,"Failed to remove this artwork", Toast.LENGTH_SHORT).show()
            }
        findNavController().navigate(R.id.action_fragmentBidProcess_to_paymentFragment)
    }


}