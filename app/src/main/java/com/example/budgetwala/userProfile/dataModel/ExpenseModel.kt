package com.example.budgetwala.userProfile.dataModel

data class ExpenseModel(var name : String,
                        var type : Type?=null,
                        var value : String?=null)
enum class Type {
    DAILY,
    MONTHLY,
    WEEKLY
}