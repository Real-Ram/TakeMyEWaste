package com.example.takemyewaste.admin

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.takemyewaste.ModelFaqs
import com.example.takemyewaste.databinding.RowFaqsBinding
import com.google.firebase.database.FirebaseDatabase

class AdapterFaqs: RecyclerView.Adapter<AdapterFaqs.HolderCategory> {

    private  val context: Context
    public var faqArrayList:ArrayList<ModelFaqs>
    private var filterList: ArrayList<ModelFaqs>

    private lateinit var binding: RowFaqsBinding

    //constructor
    constructor(context: Context, faqArrayList: ArrayList<ModelFaqs>) {
        this.context = context
        this.faqArrayList = faqArrayList
        this.filterList = faqArrayList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderCategory {
        binding = RowFaqsBinding.inflate(LayoutInflater.from(context), parent, false)
        return  HolderCategory(binding.root)
    }

    override fun onBindViewHolder(holder: HolderCategory, position: Int) {

        //get data
        val model = faqArrayList[position]
        val id = model.id
        val question = model.question
        val answer = model.answer
        val uid = model.uid
        val timestamp = model.timestamp

        //set data
        holder.quetionTv.text = question
        holder.answerTv.text = answer


        //handle click, delete category
        holder.deleteBtn.setOnClickListener {
            //confirm before delete
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Delete")
                .setMessage("Are you sure you want to delete this category?")
                .setPositiveButton("Confirm"){ a, d->
                    Toast.makeText(context, "Deleting....", Toast.LENGTH_SHORT).show()
                    deleteCategory(model, holder)
                }
                .setNegativeButton("Cancel"){a, d->
                    a.dismiss()
                }
                .show()
        }

    }

    private fun deleteCategory(model: ModelFaqs, holder: HolderCategory) {
        //get id of category to delete
        val id = model.id

        //firebase Db > Categories > categoryID
        val ref = FirebaseDatabase.getInstance().getReference("EwasteFaq")
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
        return faqArrayList.size
    }


    //Viewholder class to hold/init UI views for row_category.xml
    inner class HolderCategory(itemView: View): RecyclerView.ViewHolder(itemView) {
        //init ui views
        var quetionTv: TextView = binding.QuesTv
        var answerTv: TextView = binding.AnsTv
        var deleteBtn: LinearLayout = binding.deleteBtn
    }
}