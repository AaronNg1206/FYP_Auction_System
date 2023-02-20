package com.nghycp.fyp_auction_system.usermanagement

import android.app.ProgressDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.nghycp.fyp_auction_system.R
import com.nghycp.fyp_auction_system.databinding.ActivityAdminHomeBinding.inflate
import com.nghycp.fyp_auction_system.databinding.ActivityMainBinding.inflate
import com.nghycp.fyp_auction_system.databinding.FragmentUserProfileBinding


class FragmentUserProfile : Fragment() {

    private var _binding: FragmentUserProfileBinding? = null
    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var progressDialog: ProgressDialog

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        _binding = FragmentUserProfileBinding.inflate(inflater,container, false)
        firebaseAuth = FirebaseAuth.getInstance()

        progressDialog = ProgressDialog(context)
        progressDialog.setTitle("Please wait...")
        progressDialog.setCanceledOnTouchOutside(false)

        val user = firebaseAuth.currentUser
        val uid = user!!.uid

        val ref = Firebase.database("https://artwork-e6a68-default-rtdb.asia-southeast1.firebasedatabase.app/")
            .getReference("Users").child(uid)

        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val name = snapshot.child("name").value as String?
                val age = snapshot.child("age").value as String?
                val country = snapshot.child("country").value as String?
                val phone = snapshot.child("phone").value as String?
                val email = snapshot.child("email").value as String?
                val image = snapshot.child("image").value as String?

                binding.Name.setText(name)
                binding.Name1.setText(name)
                binding.Age.setText(age)
                binding.Country.setText(country)
                binding.Phone.setText(phone)
                binding.Email.setText(email)

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        return binding.root
    }

}