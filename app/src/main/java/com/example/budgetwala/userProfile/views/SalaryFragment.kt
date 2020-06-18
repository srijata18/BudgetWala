package com.example.budgetwala.userProfile.views

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.budgetwala.dashboard.views.DashboardActivity
import com.example.budgetwala.R
import com.example.budgetwala.utils.AppInitializer
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.activity_salary.*
import kotlinx.android.synthetic.main.row_item_expense.*


class SalaryFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.activity_salary, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(!AppInitializer.salary.isNullOrBlank())
            tv_salary.setText(AppInitializer.salary)
        btn_go.setOnClickListener {
            when {
                tv_salary.text.isNullOrBlank() -> {
                    tv_salary.error = getString(R.string.cannot_be_empty)
                }
                tv_salary.text.toString().toInt() < 0 -> {
                    tv_salary.error = getString(R.string.enter_valid)
                }
                else -> {
                    AppInitializer.salary = tv_salary.text.toString()
                    (activity as? DashboardActivity?)?.bottom_navigation?.selectedItemId =
                        R.id.action_expense
                }
            }
        }

        et_value?.setOnEditorActionListener(object  : TextView.OnEditorActionListener{
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if(actionId == EditorInfo.IME_ACTION_DONE){
                    btn_go?.performClick()
                }
                return false
            }

        })
    }
}