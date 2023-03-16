package com.nghycp.fyp_auction_system.usermanagement

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.ProgressBar
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.nghycp.fyp_auction_system.R
import com.nghycp.fyp_auction_system.UserHomeActivity
import com.nghycp.fyp_auction_system.admin.AdminHomeActivity
import com.nghycp.fyp_auction_system.databinding.FragmentAdminHomePageBinding
import com.nghycp.fyp_auction_system.databinding.FragmentLoginBinding
import com.nghycp.fyp_auction_system.report.monthlySalesReport

class FragmentLogin : Fragment() {

    private var _binding: FragmentLoginBinding? = null

    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var progressDialog: ProgressDialog

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        setHasOptionsMenu(true)

        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        firebaseAuth = FirebaseAuth.getInstance()

        progressDialog = ProgressDialog(context)
        progressDialog.setTitle("Please wait...")
        progressDialog.setCanceledOnTouchOutside(false)

        binding.btnLogin.setOnClickListener {
            validateData()
        }


        return binding.root

    }

    private var email = ""
    private var password = ""

    private fun validateData() {
        email = binding.editTextEmailAddress.text.toString().trim()
        password = binding.editTextPassword.text.toString().trim()

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.editTextEmailAddress.error = "Invalid email format"
        }else if (email.isEmpty()){
            binding.editTextEmailAddress.error = "Please enter your email"
        }else if (password.isEmpty()){
            binding.editTextPassword.error = "Please enter your password"
        }else{
            loginUser()
        }
    }

    private fun loginUser() {
        progressDialog.setMessage("Logging in...")
        progressDialog.show()

        firebaseAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
            checkUser()
        }
            .addOnFailureListener { e->
                progressDialog.dismiss()
                Toast.makeText(context,"Failed to Login",Toast.LENGTH_LONG).show()
            }

    }

    private fun checkUser() {
        progressDialog.setMessage("Checking User...")

        val firebaseUser = firebaseAuth.currentUser!!

        val ref = Firebase.database("https://artwork-e6a68-default-rtdb.asia-southeast1.firebasedatabase.app/")
            .getReference("Users")
        ref.child(firebaseUser.uid)
            .addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    progressDialog.dismiss()

                    val userType = snapshot.child("userType").value
                    if(userType == "user"){
                        val intent = Intent(
                            context,
                            UserHomeActivity::class.java
                        )
                        startActivity(intent)
                        Toast.makeText(context,"Welcome home User", Toast.LENGTH_SHORT).show()
                    }else if (userType == "Admin"){
                        val intent = Intent(
                            context,
                            AdminHomeActivity::class.java
                        )
                        startActivity(intent)
                        Toast.makeText(context,"Welcome Home Admin",Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.signup.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentLogin_to_fragmentRegister)
        }
        binding.forgotpassword.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentLogin_to_fragmentForgetPassword)
        }
    }//

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater){
        super.onCreateOptionsMenu(menu, inflater)

        menu.findItem(R.id.action_logout).isVisible = false
    }
}