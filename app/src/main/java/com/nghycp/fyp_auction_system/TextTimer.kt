package com.nghycp.fyp_auction_system

import android.content.ContentValues.TAG
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.nghycp.fyp_auction_system.databinding.FragmentTextTimerBinding
import java.sql.Date
import java.sql.Timestamp
import java.util.concurrent.TimeUnit

class TextTimer : Fragment() {

    private var _binding : FragmentTextTimerBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        _binding = FragmentTextTimerBinding.inflate(inflater,container,false)

        val database = Firebase.database.reference

        database.child("Product").addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for(snapshot in  dataSnapshot.children){
                    val timer = snapshot.child("expDate").getValue(String::class.java) ?: return
                    Toast.makeText(context,timer.toString(),Toast.LENGTH_LONG).show()
                    val expirationDate = Date(timer.toLong())

                    val now = System.currentTimeMillis()
                    val timeRemaining = timer.toLong() - now
                    Toast.makeText(context,timeRemaining.toString(),Toast.LENGTH_LONG).show()
                    val countdownTimer = object : CountDownTimer(timeRemaining, 1000) {
                        override fun onTick(millisUntilFinished: Long) {
                            // Update the view with the remaining time
                            val timeRemainingText = String.format("%02d:%02d:%02d",
                                TimeUnit.MILLISECONDS.toHours(millisUntilFinished),
                                TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) % TimeUnit.HOURS.toMinutes(1),
                                TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) % TimeUnit.MINUTES.toSeconds(1))
                            binding.ttimer.text = timeRemainingText
                        }

                        override fun onFinish() {
                            // Handle the expiration event
                            binding.ttimer.text = "Expired"
                        }
                    }

                    // Start the CountdownTimer
                    countdownTimer.start()
                    // Bind the countdown timer value to the view using databinding
                    binding.ttimer.text = countdownTimer.toString()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e(TAG, "Error retrieving expiration time", error.toException())
            }
        })

        return binding.root
    }
}
