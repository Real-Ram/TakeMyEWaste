package com.example.takemyewaste.user

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.takemyewaste.*
import com.example.takemyewaste.databinding.ActivityDashboardUserBinding
import com.google.firebase.auth.FirebaseAuth

class DashboardUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.logoutBtn.setOnClickListener {

            val builder = AlertDialog.Builder(this)
            builder.setTitle("LOGOUT")
                .setMessage("Are you sure you want to logout now?")
                .setPositiveButton("Confirm"){ a, d->
                    Toast.makeText(this, "logging out....", Toast.LENGTH_SHORT).show()
                    FirebaseAuth.getInstance().signOut()
                    startActivity(Intent(this, LoginActivity::class.java))
                }
                .setNegativeButton("Cancel"){a, d->
                    a.dismiss()
                }
                .show()
        }

        binding.newPickupBtn.setOnClickListener {
            startActivity(Intent(this, NewPickUpActivity::class.java))
        }

        binding.cancelledPickupBtn.setOnClickListener {
            startActivity(Intent(this, CancelledPickup::class.java))
        }

        binding.completedPickupBtn.setOnClickListener {
            startActivity(Intent(this, CompletedPickup::class.java))
        }


        binding.pickupStatusBtn.setOnClickListener {
            startActivity(Intent(this, PickUpStatusActivity::class.java))
        }

        binding.aboutUsBtn.setOnClickListener {
            startActivity(Intent(this, AboutUs::class.java))
        }

        binding.EWasteFaqBtn.setOnClickListener {
            startActivity(Intent(this, ShowEWasteFaqUser::class.java))
        }

        binding.profileDataBtn.setOnClickListener {
            startActivity(Intent(this, ProfileData::class.java))
        }
    }
}