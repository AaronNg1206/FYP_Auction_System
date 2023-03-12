package com.nghycp.fyp_auction_system.bidding

import android.app.ProgressDialog
import android.content.ContentValues.TAG
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
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.nghycp.fyp_auction_system.R
import com.nghycp.fyp_auction_system.customer.ModelArtwork
import com.nghycp.fyp_auction_system.databinding.FragmentBidProcessBinding
import kotlinx.android.synthetic.main.fragment_bid_process.*
import java.lang.Exception
import java.sql.Date
import java.util.concurrent.TimeUnit

class FragmentBidProcess : Fragment() {

    private var _binding: FragmentBidProcessBinding? = null

    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var bidShowBidUserAdpter: ShowBidUserAdpter

    private lateinit var recyclerView : RecyclerView

    private lateinit var bidShowArrayList: ArrayList<ModelBidUser>

    private lateinit var progressDialog: ProgressDialog

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        setHasOptionsMenu(true)

        _binding = FragmentBidProcessBinding.inflate(inflater,container,false)

        loadUser()

        firebaseAuth = FirebaseAuth.getInstance()
        progressDialog = ProgressDialog(context)
        progressDialog.setTitle("Please Wait...")
        progressDialog.setCanceledOnTouchOutside(false)

        val user = firebaseAuth.currentUser
        val uid = user!!.uid

        val userRef = Firebase.database("https://artwork-e6a68-default-rtdb.asia-southeast1.firebasedatabase.app/")
            .getReference("Users").child(uid)
        userRef.addListenerForSingleValueEvent(object : ValueEventListener{
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

    private fun loadUser() {

        bidShowArrayList = ArrayList()

        val showref = Firebase.database("https://artwork-e6a68-default-rtdb.asia-southeast1.firebasedatabase.app/")
            .getReference("Bid")
        showref.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                bidShowArrayList.clear()
                for (ds in snapshot.children){

                    val model = ds.getValue(ModelBidUser::class.java)

                    bidShowArrayList.add(model!!)
                }

                bidShowBidUserAdpter = ShowBidUserAdpter(context!!,bidShowArrayList)

                recyclerView.adapter = bidShowBidUserAdpter
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = this.arguments

        val name= args?.get("name")
        val nameTextView = binding.showName
        nameTextView.text = name.toString()

        val img= args?.get("img")
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
                findNavController().navigate(R.id.action_fragmentBidProcess_to_paymentFragment)

            }
        }.start()

        recyclerView = view.findViewById(R.id.recycleViewShowUser)

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)
        bidShowArrayList = arrayListOf<ModelBidUser>()

        binding.btnPlace.setOnClickListener {
            saveBid()
        }

    }

    private var name = ""
    private var price = ""
    private var nameUser = ""


        private fun saveBid() {

            nameUser = binding.userName.text.toString().trim()
            name = binding.showName.text.toString().trim()
            price = binding.bid.selectedItem.toString().trim()
            val args = this.arguments
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


            val ref = Firebase.database("https://artwork-e6a68-default-rtdb.asia-southeast1.firebasedatabase.app/")
                    .getReference("Bid")
            val newId = ref.push().key!!
                    ref.child(newId)
                .setValue(hashMap)
                .addOnSuccessListener {
                    progressDialog.dismiss()
                    Toast.makeText(context,"Place Successful", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    progressDialog.dismiss()
                    Toast.makeText(context,"Failed to bid this artwork", Toast.LENGTH_SHORT).show()
                }
        }

}