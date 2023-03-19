package com.nghycp.fyp_auction_system.admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
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
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.nghycp.fyp_auction_system.R
import com.nghycp.fyp_auction_system.databinding.ActivityAdminHomeBinding
import com.nghycp.fyp_auction_system.databinding.FragmentLoginBinding
import kotlinx.android.synthetic.main.header_home_user.*
import kotlinx.android.synthetic.main.header_home_user.view.*
import java.lang.Exception

class AdminHomeActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityAdminHomeBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAdminHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarHomeAdmin.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayoutAdmin
        val navView: NavigationView = binding.navViewAdmin
        val navController = findNavController(R.id.nav_host_admin)
        appBarConfiguration = AppBarConfiguration(setOf(
            R.id.adminHomePage,R.id.ReportActivity,R.id.sellOption, R.id.action_logout,R.id.recentAddFragment,R.id.adminShowBid, R.id.normalArt,R.id.adminRefund
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

        ref.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val navView: NavigationView = binding.navViewAdmin
                val view: View = navView.getHeaderView(0)
                val name = snapshot.child("name").value as String?
                val email = snapshot.child("email").value as String?
                val profileImage = snapshot.child("profileImage").value as String?

                view.textViewUser.setText(name)
                view.textViewEmailUser.setText(email)

                try {
                    Glide.with(this@AdminHomeActivity)
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
        menuInflater.inflate(R.menu.menu_main_admin, menu)

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
        val navController = findNavController(R.id.nav_host_admin)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

}