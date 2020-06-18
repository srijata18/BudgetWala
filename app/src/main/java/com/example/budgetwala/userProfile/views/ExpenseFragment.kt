package com.example.budgetwala.userProfile.views

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.budgetwala.dashboard.views.DashboardActivity
import com.example.budgetwala.R
import com.example.budgetwala.dashboard.views.ItemSelected
import com.example.budgetwala.userProfile.dataModel.ExpenseModel
import com.example.budgetwala.userProfile.dataModel.Type
import com.example.budgetwala.utils.AppInitializer
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.activity_expense.*

class ExpenseFragment : Fragment(), ItemSelected {

    private var expenseAdapter : ExpenseAdapter?=null
    private var dummyList = arrayListOf<ExpenseModel>()
    private var addedList = hashSetOf<ExpenseModel>()
    private var totalExpense = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.activity_expense, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (AppInitializer.salary.isNotEmpty() && AppInitializer.savings!=0){
            totalExpense = AppInitializer.salary.toInt() - AppInitializer.savings
        }
        btn_submit.setOnClickListener(onSubmitClicked)
    }

    override fun onStart() {
        super.onStart()
        addDummyList()
        setAdapter()
    }

    private fun setAdapter(){
        val mLayoutManager = LinearLayoutManager(activity)
        mLayoutManager.orientation = LinearLayoutManager.VERTICAL
        expenseAdapter = ExpenseAdapter(dummyList, this)
        rv_inputs?.layoutManager = mLayoutManager
        rv_inputs?.adapter = expenseAdapter
        rv_inputs?.isNestedScrollingEnabled = false
        expenseAdapter?.notifyDataSetChanged()
    }

    private fun addDummyList(){
        if (AppInitializer.userAddedList.isNullOrEmpty()) {
            dummyList.apply {
                add(ExpenseModel("Diary (Milk,Butter, Curd)"))
                add(ExpenseModel("Meat , Fish , Eggs"))
                add(ExpenseModel("Fruit && Vegetables"))
                add(ExpenseModel("Street Food"))
                add(ExpenseModel("Cafe"))
                add(ExpenseModel("Pub"))
                add(ExpenseModel("Restaurant"))
                add(ExpenseModel("Clothes"))
                add(ExpenseModel("Footwear"))
                add(ExpenseModel("Dal, Masala, Oil"))
                add(ExpenseModel("Bakery"))
                add(ExpenseModel("Beverages"))
                add(ExpenseModel("Snacks"))
                add(ExpenseModel("Beauty & Hygiene"))
                add(ExpenseModel("Kitchen"))
                add(ExpenseModel("Transport"))
                add(ExpenseModel("Others"))
            }
        }else{
            val h = linkedSetOf<ExpenseModel>()
            h.apply {
                add(ExpenseModel("Diary (Milk,Butter, Curd)"))
                add(ExpenseModel("Meat , Fish , Eggs"))
                add(ExpenseModel("Fruit && Vegetables"))
                add(ExpenseModel("Street Food"))
                add(ExpenseModel("Cafe"))
                add(ExpenseModel("Pub"))
                add(ExpenseModel("Restaurant"))
                add(ExpenseModel("Clothes"))
                add(ExpenseModel("Footwear"))
                add(ExpenseModel("Dal, Masala, Oil"))
                add(ExpenseModel("Bakery"))
                add(ExpenseModel("Beverages"))
                add(ExpenseModel("Snacks"))
                add(ExpenseModel("Beauty & Hygiene"))
                add(ExpenseModel("Kitchen"))
                add(ExpenseModel("Transport"))
                add(ExpenseModel("Others"))
            }
            val x = linkedSetOf<ExpenseModel>()
            x.addAll(h)
            for (item in AppInitializer.userAddedList){
                for(i in h)
                if (item.name == i.name ){
                    x.remove(i)
                    x.add(item)
                }
            }
            x.reversed()
//            h.addAll(AppInitializer.userAddedList)
            dummyList.addAll(x)
            addedList = AppInitializer.userAddedList.toHashSet()
        }
    }

    override fun onItemSelected(variantName: ExpenseModel?) {
        variantName?.let { addedList.add(it) }
        Log.i("info","addedList : $addedList ")
    }

    private val onSubmitClicked = View.OnClickListener {
        if (AppInitializer.salary.isNullOrBlank()) {
            Toast.makeText(activity, "Please enter salary first", Toast.LENGTH_SHORT).show()
        } else {
            AppInitializer.apply {
                userAddedList.addAll(addedList)
                savings = calculateTotalExpense()
            }
            (activity as? DashboardActivity?)?.bottom_navigation?.selectedItemId =
                R.id.action_profile
        }
    }

    private fun calculateTotalExpense() : Int {
        for (variantName in addedList) {
            val exp_type = when (variantName?.type) {
                Type.DAILY -> {
                    variantName.value?.let { it.toInt() * 30 }
                }
                Type.WEEKLY -> {
                    variantName.value?.let { it.toInt() * 4 }
                }
                else -> {
                    variantName?.value?.toInt()
                }
            }
            exp_type?.let { totalExpense += it }
            Log.i("info","exp type = $exp_type , expense = $totalExpense")

        }
        return AppInitializer.salary.toInt() - totalExpense
    }

}