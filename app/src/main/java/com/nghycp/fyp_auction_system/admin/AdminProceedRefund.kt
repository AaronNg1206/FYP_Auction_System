package com.nghycp.fyp_auction_system.admin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.nghycp.fyp_auction_system.R
import com.nghycp.fyp_auction_system.databinding.FragmentAdminProceedRefundBinding
import com.nghycp.fyp_auction_system.databinding.FragmentBidProductBinding


class AdminProceedRefund : Fragment() {

    private var _binding: FragmentAdminProceedRefundBinding? = null

    private lateinit var firebaseAuth: FirebaseAuth

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true)

        _binding = FragmentAdminProceedRefundBinding.inflate(inflater,container,false)

        val args = this.arguments
        val name= args?.get("nameArt")
        val nameTextView = binding.returnName
        nameTextView.text = name.toString()

        val price= args?.get("priceArt")
        val priceTextView = binding.returnPrice
        priceTextView.text = price.toString()

        val img = args?.get("imageArt")

        val desc= args?.get("desc")
        val descTextView = binding.returnDesc
        descTextView.text = desc.toString()

        val reason= args?.get("reason")
        val reasonTextView = binding.reason
        reasonTextView.text = reason.toString()

        val PID = args?.get("PID")
        val PIDTextView = binding.textView26
        PIDTextView.text = PID.toString()

        Glide.with(this@AdminProceedRefund)
            .load(img.toString())
            .placeholder(R.drawable.user)
            .into(binding.returnImg)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = this.arguments
        val PID = args?.get("PID").toString()
        val price = args?.get("priceArt")

        binding.btnAcpt.setOnClickListener {
            firebaseAuth = FirebaseAuth.getInstance()

            val hashMap = HashMap<String, Any>()

            hashMap["status"] = "Approved, successful refund ${price}"

            val ref =
                Firebase.database("https://artwork-e6a68-default-rtdb.asia-southeast1.firebasedatabase.app/")
                    .getReference("Return")
            ref.child(PID)
                .updateChildren(hashMap)

                .addOnSuccessListener {
                    Toast.makeText(context, "Updated", Toast.LENGTH_SHORT).show()
                    binding.btnAcpt.visibility = View.GONE
                    binding.btnDenied.visibility = View.GONE
                }
                .addOnFailureListener {
                    Toast.makeText(context, "", Toast.LENGTH_SHORT).show()
                }
        }

        binding.btnDenied.setOnClickListener {

            firebaseAuth = FirebaseAuth.getInstance()

            val hashMap = HashMap<String, Any>()

            hashMap["status"] = "Unsuccessful to refund the product"

            val ref = Firebase.database("https://artwork-e6a68-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("Return")
            ref.child(PID)
                .updateChildren(hashMap)

                .addOnSuccessListener {
                    Toast.makeText(context,"Updated", Toast.LENGTH_SHORT).show()
                    binding.btnAcpt.visibility = View.GONE
                    binding.btnDenied.visibility = View.GONE
                }
                .addOnFailureListener {
                    Toast.makeText(context,"", Toast.LENGTH_SHORT).show()
                }
        }

    }

}