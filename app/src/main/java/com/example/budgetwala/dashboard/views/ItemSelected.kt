package com.example.budgetwala.dashboard.views

import com.example.budgetwala.userProfile.dataModel.ExpenseModel


interface ItemSelected {
    fun onItemSelected(variantName : ExpenseModel?)
}