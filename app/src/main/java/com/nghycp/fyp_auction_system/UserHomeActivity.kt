package com.nghycp.fyp_auction_system

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.header_home_user.view.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.nghycp.fyp_auction_system.databinding.ActivityUserHomeBinding
import com.nghycp.fyp_auction_system.databinding.FragmentLoginBinding
import kotlinx.android.synthetic.main.header_home_user.*
import java.lang.Exception

class UserHomeActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityUserHomeBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUserHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarHome.toolbar)

        val drawerLayout:DrawerLayout = binding.drawerLayoutUser
        val navView: NavigationView = binding.navViewUser
        val navController = findNavController(R.id.nav_host)
        appBarConfiguration = AppBarConfiguration(setOf(
            R.id.fragmentUserHomePage,R.id.userProfile,R.id.viewPurchase,R.id.userProfile,R.id.category,
            R.id.about,R.id.action_logout,R.id.sell
        ),drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        updateNavHeader()

    }

    private fun updateNavHeader() {
        firebaseAuth = FirebaseAuth.getInstance()

        val user = firebaseAuth.currentUser
        val uid = user!!.uid

        val ref = Firebase.database("https://artwork-e6a68-default-rtdb.asia-southeast1.firebasedatabase.app/")
            .getReference("Users").child(uid)

        ref.addValueEventListener(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val navView: NavigationView = binding.navViewUser
                val view: View = navView.getHeaderView(0)
                val name = snapshot.child("name").value as String?
                val email = snapshot.child("email").value as String?
                val profileImage = snapshot.child("profileImage").value as String?

                view.textViewUser.setText(name)
                view.textViewEmailUser.setText(email)

                try {
                    Glide.with(this@UserHomeActivity)
                        .load(profileImage)
                        .placeholder(R.drawable.user)
                        .into(imageViewHome)
                } catch (e: Exception) {

                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.action_logout -> {
                val intent = Intent(this, FragmentLoginBinding::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}