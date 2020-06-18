package com.example.budgetwala.userProfile.views

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.budgetwala.R
import com.example.budgetwala.dashboard.views.ItemSelected
import com.example.budgetwala.userProfile.dataModel.ExpenseModel
import com.example.budgetwala.userProfile.dataModel.Type
import kotlinx.android.synthetic.main.row_item_expense.view.*

class ExpenseAdapter(private val list : ArrayList<ExpenseModel>?,
                      private val itemSelected: ItemSelected) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemCount(): Int {
        return list?.size?:0
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        Log.i("tag::","Onbindviewcalled")
        if (holder is ChildAdapterViewHolder){
            holder.apply {
                list?.let {
                    tv_name.text = it[position].name
                    if(!it[position].value.isNullOrBlank()){
                        holder.et_value.setText(it[position].value)
                    }
                    holder.btn_add.setOnClickListener {
                        val type = when {
                            holder.d.isChecked -> Type.DAILY
                            holder.w.isChecked -> Type.WEEKLY
                            else -> Type.MONTHLY
                        }
                        list.let {it1-> itemSelected.onItemSelected(ExpenseModel(it1[position].name,
                        type, holder.et_value.text.toString()))}
                    }
                    if(it[position].type != null){
                        when(it[position].type){
                            Type.DAILY -> { holder.d.isChecked = true}
                            Type.MONTHLY -> { holder.m.isChecked = true }
                            Type.WEEKLY -> { holder.w.isChecked = true }
                        }
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_item_expense, parent, false)
        return ChildAdapterViewHolder(
            view
        )
    }

    class ChildAdapterViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val tv_name = item.tv_name
        val et_value = item.et_value
        val type = item.rg_type
        val btn_add = item.btn_add
        val d = item.d
        val w = item.w
        val m = item.m
    }

}
