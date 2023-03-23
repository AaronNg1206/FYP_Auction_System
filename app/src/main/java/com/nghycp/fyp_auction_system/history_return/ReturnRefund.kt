package com.nghycp.fyp_auction_system.history_return

import android.app.ProgressDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.nghycp.fyp_auction_system.R
import com.nghycp.fyp_auction_system.databinding.FragmentReturnRefundBinding
import com.nghycp.fyp_auction_system.databinding.FragmentViewPurchaseBinding
import kotlinx.android.synthetic.main.fragment_return_refund.*


class ReturnRefund : Fragment() {

    private var _binding: FragmentReturnRefundBinding? = null

    private lateinit var firebaseAuth: FirebaseAuth

    private val binding get() = _binding!!

    private lateinit var progressDialog: ProgressDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)

        _binding = FragmentReturnRefundBinding.inflate(inflater,container,false)

        val args = this.arguments

        val name = args?.get("artName")
        val nameText = binding.namePro
        nameText.text = name.toString()

        val price = args?.get("artPrice")
        val priceText = binding.pricepro
        priceText.text = price.toString()

        val img = args?.get("artImage")

        Glide.with(this@ReturnRefund)
            .load(img.toString())
            .placeholder(R.drawable.user)
            .into(binding.imageView2)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        firebaseAuth = FirebaseAuth.getInstance()
        progressDialog = ProgressDialog(context)
        progressDialog.setTitle("Please Wait...")
        progressDialog.setCanceledOnTouchOutside(false)

        binding.button.visibility = View.VISIBLE

        binding.button.setOnClickListener {
            validateData()
        }

    }

    private var desc = ""
    private var spinner = ""

    private fun validateData() {

        desc =binding.desc.text.toString().trim()
        spinner = binding.spinner.selectedItem.toString().trim()

        if(desc.isEmpty()){
            binding.desc.error = "Description cannot be empty"
        }else if (desc.length < 10){
            binding.desc.error = "Please briefly describe"
        } else if(spinner.isEmpty()){
            Toast.makeText(context, "Reason cannot be empty", Toast.LENGTH_SHORT).show()
        }else{
            saveData()
        }

    }

    private fun saveData() {

        val user = firebaseAuth.currentUser
        val uid = user!!.uid

        val args = this.arguments

        val name = args?.get("artName").toString()
        val price = args?.get("artPrice").toString()
        val img = args?.get("artImage").toString()
        Glide.with(this@ReturnRefund).load(img)

        val hashMap = HashMap<String, Any>()

        hashMap["uid"] = uid
        hashMap["nameArt"] = name
        hashMap["desc"] = desc
        hashMap["reason"] = spinner
        hashMap["priceArt"] = price
        hashMap["imageArt"] = img
        hashMap["status"] = "Pending"
        hashMap["refundStatus"]

        val ref = Firebase.database("https://artwork-e6a68-default-rtdb.asia-southeast1.firebasedatabase.app/")
            .getReference("Return").push()
        val pushkey = ref.key.toString()
        hashMap["PID"] = pushkey

        ref.setValue(hashMap)
            .addOnSuccessListener {
                progressDialog.dismiss()
                Toast.makeText(context,"Submitted", Toast.LENGTH_SHORT).show()
                deleteData()
            }
            .addOnFailureListener {
                progressDialog.dismiss()
                Toast.makeText(context,"Failed to return this artwork", Toast.LENGTH_SHORT).show()
            }
        findNavController().navigate(R.id.action_returnRefund_to_viewPurchase)
    }

    private fun deleteData() {

        val args = this.arguments

        val ref2 = Firebase.database("https://artwork-e6a68-default-rtdb.asia-southeast1.firebasedatabase.app/")
            .getReference("paid")

        val PID = args?.get("PID")

        ref2.child(PID as String).removeValue()

    }


}