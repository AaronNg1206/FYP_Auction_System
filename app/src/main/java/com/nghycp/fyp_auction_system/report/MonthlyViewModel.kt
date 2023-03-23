package com.nghycp.fyp_auction_system.report

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.*


class MonthlyViewModel(application: Application) :
    AndroidViewModel(application) {

    fun loadRecordList(date: String, callback : ReportCallback) {
        val database = FirebaseDatabase.getInstance()
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        val fdate = formatter.parse(date)

        val cal = Calendar.getInstance()
        cal.setTime(fdate)
        cal.add(Calendar.DATE, -30)
        //cal.add(Calendar.MONTH, "")
        cal.add(Calendar.YEAR, 0)
        val dateBefore30Days = cal.time.time


       val cal2 = Calendar.getInstance()
        cal2.setTime(fdate)
        cal2.add(Calendar.DATE, 1)
        val dateTmr = cal.time.time

        val databaseReference =  database.getReference("paid")
            .orderByChild("timestamp").startAt(dateBefore30Days.toString())
            .endAt(dateTmr.toString())

        databaseReference.get().addOnCompleteListener {
            if (it.isSuccessful) {
                val report = it.result.children.mapNotNull { doc ->

                    doc.getValue(ModelReport::class.java)
                }
                callback.onCallBack(report)
            } else {
                Toast.makeText(getApplication(), it.exception?.message.toString(), Toast.LENGTH_LONG).show()
            }
        }
    }
}