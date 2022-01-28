package com.example.takemyewaste

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.takemyewaste.databinding.ActivityCancelledPickupAdminBinding
import com.example.takemyewaste.databinding.ActivityCancelledPickupBinding
import com.example.takemyewaste.databinding.ActivityPickUpStatusBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class CancelledPickup : AppCompatActivity() {

    //View binding
    private lateinit var binding: ActivityCancelledPickupBinding

    //Firebase Auth
    private lateinit var firebaseAuth: FirebaseAuth

    //arraylist to hold categories
    private lateinit var pickupStatusArrayList: ArrayList<ModelPickUp>

    //adapter
    private lateinit var adapterCancelledPickup: AdapterCancelledPickup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCancelledPickupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //init firebase auth
        firebaseAuth = FirebaseAuth.getInstance()
        loadPickupStatus()

    }

    //show the pickup status
    private fun loadPickupStatus() {
        //init arraylisdt
        pickupStatusArrayList = ArrayList()


        val rootRef = FirebaseDatabase.getInstance().reference
        val uid = FirebaseAuth.getInstance().currentUser!!.uid
        val uidRef = rootRef.child("Users").child(uid)

        //get all categories from firebase database....Firebase DB >Categories
        val ref = FirebaseDatabase.getInstance().getReference("Cancelled_Order")
        ref.orderByChild("uid").equalTo(uid)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    //clear list before starting adding data into it
                    pickupStatusArrayList.clear()
                    for (ds in snapshot.children){
                        //get data as model
                        val model = ds.getValue(ModelPickUp::class.java)

                        //add to arraylist
                        pickupStatusArrayList.add(model!!)
                    }
                    //setup adapter
                    adapterCancelledPickup = AdapterCancelledPickup(this@CancelledPickup, pickupStatusArrayList)

                    //set adapter to recyclerview
                    binding.pickupStatusRv.adapter = adapterCancelledPickup
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
    }

}