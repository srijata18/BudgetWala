package com.example.budgetwala.userProfile.views

/*import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.layout_home.*
import kotlinx.android.synthetic.main.layout_toolbar.**/

import android.Manifest
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.budgetwala.R
import com.example.budgetwala.dashboard.views.DashboardActivity
import com.example.budgetwala.utils.AppInitializer
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.utils.ColorTemplate
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.io.FileOutputStream


/*
* Launching activity, which is responsible for displaying the output of data based on the
* user salary provided and total expense calculated.
* */
class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadData()
        btn_print?.setOnClickListener{
            if (checkWriteExternalPermission())
                sendViaEmail()
            else{
                Toast.makeText((activity as? DashboardActivity), "Please enable write permission for this app", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loadData() {
        if (AppInitializer.salary.isNullOrBlank()) {
            tv_emptystate.visibility = View.VISIBLE
            cl_main.visibility = View.GONE
        } else {
            tv_emptystate.visibility = View.GONE
            cl_main.visibility = View.VISIBLE
            tv_salary?.text = "Your Monthly Salary : ${AppInitializer.salary}"
            if (AppInitializer.savings < 0) {
                tv_savings?.text =
                    "Your Debt : ${AppInitializer.savings * -1}"
            } else {
                tv_savings?.text = "Your Monthly Savings : ${AppInitializer.savings}"
            }
            var salary = (AppInitializer.salary.toFloat() / 100) * 50
            arc_progress1?.bottomText = "$salary"
            salary = (AppInitializer.salary.toFloat() / 100) * 75
            arc_progress2?.bottomText = "$salary"
            arc_progress3?.bottomText = AppInitializer.salary
            salary = ((AppInitializer.salary.toFloat() - AppInitializer.savings) / 100) * 10
            arc_expense1?.bottomText = "$salary"
            salary = ((AppInitializer.salary.toFloat() - AppInitializer.savings) / 100) * 20
            arc_expense2?.bottomText = "$salary"
            salary = ((AppInitializer.salary.toFloat() - AppInitializer.savings) / 100) * 30
            arc_expense3?.bottomText = "$salary"

            val arrListForchart = ArrayList<BarEntry>()
            arrListForchart.add(BarEntry(800f, 0, "Rs 800"))
            arrListForchart.add(BarEntry(1000f, 1, "Rs 4500"))
            arrListForchart.add(BarEntry(1500f, 2, "Rs 14000"))
            arrListForchart.add(BarEntry(2000f, 3, "Rs 36000"))
            arrListForchart.add(BarEntry(2500f, 4, "Rs 91000"))

            val year = ArrayList<String>()
            year.add("1 month")
            year.add("1 year")
            year.add("5 years")
            year.add("10 years")
            year.add("20 years")

            val bardataset = BarDataSet(arrListForchart, "( Amount in Rs )")
            bardataset.valueTextSize = 12f;
            chart.animateY(5000)
            val data = BarData(year, bardataset)
            bardataset.setColors(ColorTemplate.COLORFUL_COLORS)
            val leftAxis = chart.axisLeft
            leftAxis.isEnabled = false
            val rightAxis = chart.axisRight
            rightAxis.isEnabled = false
            chart.setDescription(" ")
            chart.setDrawGridBackground(false)
            chart.setDrawBorders(false)
            chart.xAxis.position = XAxis.XAxisPosition.BOTTOM
            chart.setScaleEnabled(false)
            chart.setPinchZoom(false)
            data.setValueTextSize(15f)
            chart.setExtraBottomOffset(5f)
            chart.data = data
        }

    }

    private fun sendViaEmail(){
        val rootView: View =
            (activity as DashboardActivity)?.window.decorView.findViewById(android.R.id.content)
        val bm = getScreenShot(cl_root)
        if (bm != null) {
            Log.i("info",bm.toString())
            store(bm, "Abc")
        }
    }

    private fun getScreenShot(view: View): Bitmap? {
        val screenView = view.rootView
        screenView.isDrawingCacheEnabled = true
        val bitmap = Bitmap.createBitmap(screenView.drawingCache)
        screenView.isDrawingCacheEnabled = false
        return bitmap
    }
    private fun store(bm: Bitmap, fileName: String?) {
        val dirPath: String =
            Environment.getExternalStorageDirectory().getAbsolutePath().toString() + "/Screenshots"
        val dir = File(dirPath)
        if (!dir.exists()) dir.mkdirs()
        val file = File(dirPath, fileName)
        try {
            val fOut = FileOutputStream(file)
            bm.compress(Bitmap.CompressFormat.PNG, 85, fOut)
            fOut.flush()
            shareImage(file)
            fOut.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun checkWriteExternalPermission(): Boolean {
        val permission = Manifest.permission.WRITE_EXTERNAL_STORAGE
        val res = (activity as? DashboardActivity?)?.checkCallingOrSelfPermission(permission)
        return res == PackageManager.PERMISSION_GRANTED
    }

    private fun shareImage(file: File) {
        val uri: Uri = Uri.fromFile(file)
        val intent = Intent()
        intent.action = Intent.ACTION_SEND
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_SUBJECT, "")
        intent.putExtra(Intent.EXTRA_TEXT, "")
        intent.putExtra(Intent.EXTRA_STREAM, uri)
        try {
            startActivity(Intent.createChooser(intent, "Share Screenshot"))
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(context, "No App Available", Toast.LENGTH_SHORT).show()
        }
    }
}