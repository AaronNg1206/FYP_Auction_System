package com.nghycp.fyp_auction_system.report

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.mikephil.charting.charts.BarChart
import com.nghycp.fyp_auction_system.R
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.nghycp.fyp_auction_system.customer.ArtworkAdapter
import com.nghycp.fyp_auction_system.databinding.FragmentBarChartBinding
import com.nghycp.fyp_auction_system.history_return.AdapterHistory
import com.nghycp.fyp_auction_system.history_return.ModelShow
import kotlinx.android.synthetic.main.fragment_add_to_cart_layout.*

class BarChartFragment : Fragment() {
    private lateinit var reportAdapter: ReportAdapter
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var recyclerView: RecyclerView
    private lateinit var showArrayList: ArrayList<ModelReport>
    private val ref = Firebase.database("https://artwork-e6a68-default-rtdb.asia-southeast1.firebasedatabase.app/")
        .getReference("paid")
    private var _binding: FragmentBarChartBinding? = null
    private val binding get() = _binding!!
    //private lateinit var pieChartList: ArrayList<ModelReport>

    // Creating
    // variables for our bar chart
    lateinit var barChart: BarChart

    // Creating
    // a variable for bar data
    lateinit var barData: BarData

    //    creating a
    // variable for bar data set
    lateinit var barDataSet: BarDataSet

    //    creating array list for bar data
    lateinit var barEntriesList: ArrayList<BarEntry>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentBarChartBinding.inflate(inflater, container, false)
        barChart = binding.idBarChart
        //    initializing
        // our variable with their ids.

       // barChart = findViewById(R.id.idTVHead)

        //    calling get bar
        // chart data to add data to our array list
        getBarChartData()

        //    initializing our bar data set
        barDataSet = BarDataSet(barEntriesList, "Bar Chart Data")

        //   initializing our bar data
        barData = BarData(barDataSet)

        //   setting data to our bar chart
        barChart.data = barData

        //   setting colors for our bar chart text
        barDataSet.valueTextColor = Color.BLACK

        //   setting color for our bar data set
        barDataSet.setColor(resources.getColor(R.color.purple_200))

        //   setting text size
        barDataSet.valueTextSize = 16f

        //   enabling description as false
        barChart.description.isEnabled = false

        return binding.root
    }

        private fun getBarChartData() {
            barEntriesList = ArrayList()

            //   adding data
            // to our bar entries list
            barEntriesList.add(BarEntry(1f, 1f))
            barEntriesList.add(BarEntry(2f, 2f))
            barEntriesList.add(BarEntry(3f, 3f))
            barEntriesList.add(BarEntry(4f, 4f))
            barEntriesList.add(BarEntry(5f, 5f))

        }

    private fun loadShow() {
        showArrayList = ArrayList()

        val ref = Firebase.database("https://artwork-e6a68-default-rtdb.asia-southeast1.firebasedatabase.app/")
            .getReference("paid")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()) {
                    val user = firebaseAuth.currentUser
                    val uid = user!!.uid
                    showArrayList.clear()
                    for (ds in snapshot.children) {

                        val model = ds.getValue(ModelReport::class.java)
                        if (model != null){
                            showArrayList.add(model!!)
                        }
                        val author = model?.artAuthor
                        val  price = model?.artPrice

                    }

                    val context = context
                    if (context != null) {
                        reportAdapter = ReportAdapter(showArrayList,context!!)

                    }
                }
            }


            override fun onCancelled(error: DatabaseError) {
                try{

                }catch(e: Exception) {
                    Log.d("ccc",e.toString())
                }
            }
        })
    }
}