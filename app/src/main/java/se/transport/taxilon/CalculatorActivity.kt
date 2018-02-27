package se.transport.taxilon

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.*
import kotlinx.android.synthetic.main.activity_calculator.*

class CalculatorActivity : AppCompatActivity() {


    val garantiLonSthml = 19572
    val garantiLon = 19211
    val garantiTimmar = 166.4
    var city = ""
    var hours = 0
    var arbetstidsmatt = 0
    var garanti = 0
    var timLon = 0
    var lon = 0
    var realLon = 0



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculator)
        setupActionBar()



        val kollektivYes = findViewById<RadioButton>(R.id.radioButtonYes) as RadioButton
        val kollektivNo = findViewById<RadioButton>(R.id.radioButtonNo) as RadioButton
        val calculateButton = findViewById<Button>(R.id.bBerakna) as Button


    }



    fun setupActionBar(){
        val toolBar = findViewById<Toolbar>(R.id.app_bar) as Toolbar
        toolBar.title=""
        setSupportActionBar(toolBar)
        var tvToolBar = findViewById<TextView>(R.id.tvToolbarTitle) as TextView
        tvToolBar.text = getString(R.string.calculatorButtonString)



        toolBar.setPadding(0, 0, 0, 0)//for tab otherwise give space in tab
        toolBar.setContentInsetsAbsolute(0, 0)



    }


    fun backButton(v: View){
        onBackPressed()
    }






}
