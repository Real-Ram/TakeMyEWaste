package com.example.takemyewaste

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.takemyewaste.databinding.RowPickupStatusAdminBinding
import com.example.takemyewaste.databinding.RowPickupStatusBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class AdapterApprovePickup: RecyclerView.Adapter<AdapterApprovePickup.HolderCategory> {

    private  val context: Context
    public var pickupStatusArrayList:ArrayList<ModelPickUp>
    private var filterList: ArrayList<ModelPickUp>

    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var binding: RowPickupStatusAdminBinding

    //constructor
    constructor(context: Context, pickupStatusArrayList: ArrayList<ModelPickUp>) {
        this.context = context
        this.pickupStatusArrayList = pickupStatusArrayList
        this.filterList = pickupStatusArrayList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderCategory {
        binding = RowPickupStatusAdminBinding.inflate(LayoutInflater.from(context), parent, false)
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

        //set data
        holder.nameTv.text = name
        holder.mobileTv.text = mobile
        holder.productTv.text = product
        holder.addressTv.text = address
        holder.timeTv.text = time

        //handle cancel click
        holder.cancelBtn.setOnClickListener {

            val builder = AlertDialog.Builder(context)
            builder.setTitle("Cancel")
                .setMessage("Are you sure you want to cancel this pickup?")
                .setPositiveButton("Confirm"){ a, d->
                    Toast.makeText(context, "Deleting....", Toast.LENGTH_SHORT).show()
                    sendToDbCancel(model, holder)
                }
                .setNegativeButton("Cancel"){a, d->
                    a.dismiss()
                }
                .show()
        }

        //handle receive click
        holder.receiveBtn.setOnClickListener {

            val builder = AlertDialog.Builder(context)
            builder.setTitle("Received the Pick-Up")
                .setMessage("Are you sure you got the pickup?")
                .setPositiveButton("Confirm"){ a, d->
                    Toast.makeText(context, "Deleting....", Toast.LENGTH_SHORT).show()
                    sendToDbReceive(model, holder)
                }
                .setNegativeButton("Cancel"){a, d->
                    a.dismiss()
                }
                .show()
        }

    }

    private fun sendToDbCancel(model: ModelPickUp, holder: AdapterApprovePickup.HolderCategory) {

        val id = model.id

        val ref = FirebaseDatabase.getInstance().getReference("Cancelled_Order")

        ref.child(id)
            .setValue(model)
            .addOnSuccessListener {
                cancelPickUp(model, holder)
            }
            .addOnFailureListener { e->
                Toast.makeText(context, "Unable to due to ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun sendToDbReceive(model: ModelPickUp, holder: AdapterApprovePickup.HolderCategory) {

        val id = model.id

        val ref = FirebaseDatabase.getInstance().getReference("Completed_order")

        ref.child(id)
            .setValue(model)
            .addOnSuccessListener {
                cancelPickUp(model, holder)
            }
            .addOnFailureListener { e->
                Toast.makeText(context, "Unable to due to ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun cancelPickUp(model: ModelPickUp, holder: AdapterApprovePickup.HolderCategory) {

        //get id of category to delete
        val id = model.id

        //firebase Db > Categories > categoryID
        val ref = FirebaseDatabase.getInstance().getReference("PendingPickup")
        ref.child(id)
            .removeValue()
            .addOnSuccessListener {
                Toast.makeText(context, "Deleted....", Toast.LENGTH_SHORT).show()
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
        var cancelBtn: LinearLayout = binding.cancelBtn
        var receiveBtn: LinearLayout = binding.receivedBtn
    }
}
