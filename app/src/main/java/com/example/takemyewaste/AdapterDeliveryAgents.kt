package layout

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.takemyewaste.ModelDeliveryAgents
import com.example.takemyewaste.ModelFaqs
import com.example.takemyewaste.databinding.RowDeliveryAgentsBinding
import com.example.takemyewaste.databinding.RowFaqsBinding
import com.google.firebase.database.FirebaseDatabase

class AdapterDeliveryAgents: RecyclerView.Adapter<AdapterDeliveryAgents.HolderCategory> {

    private  val context: Context
    public var deliArrayList:ArrayList<ModelDeliveryAgents>
    private var filterList: ArrayList<ModelDeliveryAgents>

    private lateinit var binding: RowDeliveryAgentsBinding


    //constructor
    constructor(context: Context, deliArrayList: ArrayList<ModelDeliveryAgents>) {
        this.context = context
        this.deliArrayList = deliArrayList
        this.filterList = deliArrayList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderCategory {
        binding = RowDeliveryAgentsBinding.inflate(LayoutInflater.from(context), parent, false)
        return  HolderCategory(binding.root)
    }

    override fun onBindViewHolder(holder: HolderCategory, position: Int) {

        //get data
        val model = deliArrayList[position]
        val id = model.id
        val name = model.name
        val mobile = model.mobile
        val uid = model.uid
        val timestamp = model.timestamp

        //set data
        holder.nameTv1.text = name
        holder.mobileTv1.text = mobile


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

    private fun deleteCategory(model: ModelDeliveryAgents, holder: HolderCategory) {
        //get id of category to delete
        val id = model.id

        //firebase Db > Categories > categoryID
        val ref = FirebaseDatabase.getInstance().getReference("DeliveryAgents")
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
        return deliArrayList.size
    }


    //Viewholder class to hold/init UI views for row_category.xml
    inner class HolderCategory(itemView: View): RecyclerView.ViewHolder(itemView) {
        //init ui views
        var nameTv1: TextView = binding.nameTv
        var mobileTv1: TextView = binding.mobileTv
        var deleteBtn: LinearLayout = binding.deleteBtn
    }
}