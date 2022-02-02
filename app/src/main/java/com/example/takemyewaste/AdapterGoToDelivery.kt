package com.example.takemyewaste

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.takemyewaste.databinding.RowPickupChooseDeliveryBinding
import com.example.takemyewaste.databinding.RowPickupStatusAdminBinding
import com.example.takemyewaste.databinding.RowPickupStatusBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.util.regex.Pattern

class AdapterGoToDelivery: RecyclerView.Adapter<AdapterGoToDelivery.HolderCategory> {

    private  val context: Context
    public var pickupStatusArrayList:ArrayList<ModelDeliveryBoy>
    private var filterList: ArrayList<ModelDeliveryBoy>

    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var binding: RowPickupChooseDeliveryBinding

    //constructor
    constructor(context: Context, pickupStatusArrayList: ArrayList<ModelDeliveryBoy>) {
        this.context = context
        this.pickupStatusArrayList = pickupStatusArrayList
        this.filterList = pickupStatusArrayList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderCategory {
        binding = RowPickupChooseDeliveryBinding.inflate(LayoutInflater.from(context), parent, false)
        return  HolderCategory(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
    }


    override fun onBindViewHolder(holder: HolderCategory, position: Int) {

        //get data
        val model = pickupStatusArrayList[position]
        val id = model.id
        val name = model.name
        val mobile = model.mobile
        val product = model.product
        val address = model.address
        val time = model.time
        val uid = model.uid
        val timestamp = model.timestamp
        var d_uid = model.d_uid
        //val d_name = model.d_name
        //val  d_mobile = model.d_mobile


        //set data
        holder.nameTv.text = name
        holder.mobileTv.text = mobile
        holder.productTv.text = product
        holder.addressTv.text = address
        holder.timeTv.text = time
        //holder.d_nameEt.text = d_name
        //holder.d_mobileEt.text = d_mobile

        //d_uid = FirebaseAuth.getInstance().currentUser!!.uid



        //handle cancel click
        holder.goToDeliveryBtn.setOnClickListener {
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Cancel")
                .setMessage("Are you sure you want to go for this delivery?")
                .setPositiveButton("Confirm") { a, d ->
                    Toast.makeText(context, "Please wait....", Toast.LENGTH_SHORT).show()
                    sendToDbUnderDelivery(model, holder)
                }
                .setNegativeButton("Cancel") { a, d ->
                    a.dismiss()
                }
                .show()
        }
    }

    private fun sendToDbUnderDelivery(model: ModelDeliveryBoy, holder: AdapterGoToDelivery.HolderCategory) {

        val id = model.id
        model.d_uid = FirebaseAuth.getInstance().currentUser!!.uid
        model.d_name = binding.deliveryBoyNameEt.text.toString().trim()
        model.d_mobile = binding.deliveryBoyMobileEt.text.toString().trim()
        model.d_date = binding.deliveryBoyDateEt.text.toString().trim()


        val ref = FirebaseDatabase.getInstance().getReference("UnderDelivery_Order")

        ref.child(id)//.child(id) before the d_uid
            .setValue(model)
            .addOnSuccessListener {
                removePickUp(model, holder)
            }
            .addOnFailureListener { e ->
                Toast.makeText(context, "Unable to due to ${e.message}", Toast.LENGTH_SHORT)
                    .show()
            }

    }

    private fun removePickUp(model: ModelDeliveryBoy, holder: AdapterGoToDelivery.HolderCategory) {

        //get id of category to delete
        val id = model.id

        //firebase Db > Categories > categoryID
        val ref = FirebaseDatabase.getInstance().getReference("Approved_Order")
        ref.child(id)
            .removeValue()
            .addOnSuccessListener {
                Toast.makeText(context, "Please wait....", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {e->
                Toast.makeText(context, "Unable to delete due to ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    override fun getItemCount(): Int {
        return pickupStatusArrayList.size
    }


    //Viewholder class to hold/init UI views for row_category.xml
    inner class HolderCategory(itemView: View): RecyclerView.ViewHolder(itemView) {
        //init ui views
        var nameTv: TextView = binding.nameEt
        var mobileTv: TextView = binding.mobileEt
        var productTv: TextView = binding.productEt
        var addressTv: TextView = binding.addressEt
        var timeTv: TextView = binding.timeEt
        //var d_nameEt: EditText = binding.deliveryBoyNameEt
        //var d_mobileEt: EditText = binding.deliveryBoyMobileEt
        var goToDeliveryBtn: LinearLayout = binding.goToDeliveryBtn
    }
}
