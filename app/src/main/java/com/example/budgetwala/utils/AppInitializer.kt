package com.example.budgetwala.utils

import com.example.budgetwala.userProfile.dataModel.ExpenseModel

class AppInitializer {
    companion object {
        var salary = ""
        var savings = 0
        var userAddedList = arrayListOf<ExpenseModel>()
    }
}