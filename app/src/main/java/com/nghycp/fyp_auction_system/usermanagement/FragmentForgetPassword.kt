package com.nghycp.fyp_auction_system.usermanagement

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.nghycp.fyp_auction_system.R
import com.nghycp.fyp_auction_system.databinding.FragmentForgetPasswordBinding


class FragmentForgetPassword : Fragment() {

    private var _binding: FragmentForgetPasswordBinding? = null
    private lateinit var auth: FirebaseAuth

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        setHasOptionsMenu(true)

        _binding =FragmentForgetPasswordBinding.inflate(inflater, container, false)

        binding.btnBackToLogin.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentForgetPassword_to_fragmentLogin)
        }

        auth = Firebase.auth

        val buttonForget = binding.resetBtn

        buttonForget.setOnClickListener{
            val email = binding.forgetPassword.text.toString().trim{it <= ' '}
            if(email.isEmpty()){
                Toast.makeText(this.context, "Please enter you email address", Toast.LENGTH_LONG).show()
            }else{
                auth.sendPasswordResetEmail(email).addOnCompleteListener{
                    if(it.isSuccessful){

                        Toast.makeText(this.context, "Email was sent successfully", Toast.LENGTH_LONG).show()


                    }
                }
            }
        }
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        // log out action not visible in forgot password fragment
        menu.findItem(R.id.action_logout).isVisible = false
    }

}