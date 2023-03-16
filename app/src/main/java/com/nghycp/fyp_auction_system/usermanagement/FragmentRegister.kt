package com.nghycp.fyp_auction_system.usermanagement

import android.app.ProgressDialog
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Patterns
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.ProgressBar
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.nghycp.fyp_auction_system.R
import com.nghycp.fyp_auction_system.databinding.FragmentRegisterBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class FragmentRegister : Fragment() {

    private var _binding: FragmentRegisterBinding? = null

    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var progressDialog: ProgressDialog

    //var sharedPreference: SharedPreferences? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        setHasOptionsMenu(true)

        _binding = FragmentRegisterBinding.inflate(inflater, container, false)

        firebaseAuth = FirebaseAuth.getInstance()
        progressDialog = ProgressDialog(context)
        progressDialog.setTitle("Please Wait...")
        progressDialog.setCanceledOnTouchOutside(false)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backToLogin.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentRegister_to_fragmentLogin)
        }

        binding.btnRegister.setOnClickListener{

            validateData()

        }

    }

    private var name = ""
    private var email = ""
    private var age = ""
    private var country = ""
    private var phone = ""
    private var password = ""

    private fun validateData() {
        name = binding.username.text.toString().trim()
        email = binding.edittextemail.text.toString().trim()
        age = binding.age.text.toString().trim()
        country = binding.country.text.toString().trim()
        phone = binding.phoneField.text.toString().trim()
        password = binding.edittextpassword.text.toString().trim()

        if(name.isEmpty()){
            binding.username.error = "Enter Your Name"
        }else if (email.isEmpty()){
            binding.edittextemail.error = "Enter Your Email"
        }else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.edittextemail.error = "Invalid Email"
        }else if (age.isEmpty()){
            binding.age.error = "Enter Your Age"
        }else if (country.isEmpty()){
            binding.country.error = "Enter Your Country"
        }else if (phone.isEmpty()){
            binding.phoneField.error = "Enter Your Phone Number"
        }else if (password.isEmpty()){
            binding.edittextpassword.error = "Enter Your Password"
        }else if (!Patterns.PHONE.matcher(phone).matches()){
            binding.phoneField.error = "Invalid Phone Number"
        }else {
            createUserAccount()
        }
    }

    private fun createUserAccount() {
        progressDialog.setMessage("Creating Account")
        progressDialog.show()

        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                updateUserInfo()
            }
            .addOnFailureListener {
                progressDialog.dismiss()
                Toast.makeText(context,"Failed to Register", Toast.LENGTH_LONG).show()
            }
    }

    private fun updateUserInfo() {

        progressDialog.setMessage("Saving User Info...")

        //val timestamp = System.currentTimeMillis()

        val uid = firebaseAuth.uid

        val hashMap: HashMap<String, Any?> = HashMap()

        hashMap["uid"] = uid
        hashMap["name"] = name
        hashMap["email"] = email
        hashMap["age"] = age
        hashMap["country"] = country
        hashMap["phone"] = phone
        //hashMap["password"] = password
        hashMap["userType"] = "user"
        hashMap["profileImage"] = ""

        val ref = Firebase.database("https://artwork-e6a68-default-rtdb.asia-southeast1.firebasedatabase.app/")
            .getReference("Users")
        ref.child(uid!!)
            .setValue(hashMap)
            .addOnSuccessListener {
                progressDialog.dismiss()
                Toast.makeText(context,"Register Successful", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_fragmentRegister_to_fragmentLogin)
            }
            .addOnFailureListener {
                progressDialog.dismiss()
                Toast.makeText(context,"Failed saving user info", Toast.LENGTH_SHORT).show()
            }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater){
        super.onCreateOptionsMenu(menu, inflater)

        menu.findItem(R.id.action_logout).isVisible = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}