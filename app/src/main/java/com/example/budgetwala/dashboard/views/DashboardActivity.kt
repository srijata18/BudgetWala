package com.example.budgetwala.dashboard.views

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.budgetwala.R
import com.example.budgetwala.userProfile.views.ExpenseFragment
import com.example.budgetwala.userProfile.views.ProfileFragment
import com.example.budgetwala.userProfile.views.SalaryFragment
import com.example.budgetwala.utils.Constants
import kotlinx.android.synthetic.main.activity_dashboard.*

class DashboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        addFragment(SalaryFragment(),
            Constants.SalaryFragment
        )
        bottom_navigation?.setOnNavigationItemSelectedListener { item ->
            when(item.itemId){
                R.id.action_home -> {
                    addFragment(SalaryFragment(),
                    Constants.SalaryFragment
                    )
                }
                R.id.action_expense -> {
                    addFragment(ExpenseFragment(),
                        Constants.ExpenseFragment
                    )
                }
                R.id.action_profile -> {
                    addFragment(
                        ProfileFragment(),
                        Constants.ProfileFragment
                    )
                }
            }
            true
        }
    }


    private fun addFragment(
        fragment: Fragment,
        fragmentTag: String?
    ) {
        hideKeyBoard()
        supportFragmentManager
            .beginTransaction()
            .add(R.id.container, fragment, fragmentTag)
            .disallowAddToBackStack()
            .commit()
    }

    fun hideKeyBoard(){
        val imm = this.getSystemService(Activity.INPUT_METHOD_SERVICE) as? InputMethodManager
        var view = this.currentFocus
        if (view == null){
            view = View(this)
        }
        imm?.hideSoftInputFromWindow(view.windowToken, 0)
    }

}