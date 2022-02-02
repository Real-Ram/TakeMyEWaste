package com.example.takemyewaste

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.takemyewaste.databinding.ActivityAddDeliveryAgentsBinding
import com.example.takemyewaste.databinding.ActivityApprovePickupBinding
import com.example.takemyewaste.databinding.ActivityApprovedPickupDeliveryboyBinding
import com.example.takemyewaste.databinding.ActivityPickUpStatusBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import layout.AdapterDeliveryAgents

class ApprovedPickupDeliveryboy : AppCompatActivity() {

    //View binding
    private lateinit var binding: ActivityApprovedPickupDeliveryboyBinding

    //Firebase Auth
    private lateinit var firebaseAuth: FirebaseAuth

    //arraylist to hold categories
    private lateinit var pickupStatusArrayList: ArrayList<ModelDeliveryBoy>


    //adapter
    private lateinit var adapterApprovePickup: AdapterGoToDelivery


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityApprovedPickupDeliveryboyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //init firebase auth
        firebaseAuth = FirebaseAuth.getInstance()
        loadPickupStatus()

    }

    private fun loadPickupStatus() {
        //init arraylisdt
        pickupStatusArrayList = ArrayList()

        //get all categories from firebase database....Firebase DB >Categories
        val ref = FirebaseDatabase.getInstance().getReference("Approved_Order")
        ref.orderByChild("id").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                //clear list before starting adding data into it
                pickupStatusArrayList.clear()
                for (ds in snapshot.children){
                    //get data as model
                    val model = ds.getValue(ModelDeliveryBoy::class.java)

                    //add to arraylist
                    pickupStatusArrayList.add(model!!)
                }
                //setup adapter
                adapterApprovePickup = AdapterGoToDelivery(this@ApprovedPickupDeliveryboy, pickupStatusArrayList)

                //set adapter to recyclerview
                binding.pickupStatusAdminRv.adapter = adapterApprovePickup
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

}