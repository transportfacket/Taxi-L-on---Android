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


        val etCity = findViewById<EditText>(R.id.etCity) as EditText
        val etHours = findViewById<EditText>(R.id.etHours) as EditText
        val etArbetstidsmatt = findViewById<EditText>(R.id.etArbetstidsmatt) as EditText
        val kollektivYes = findViewById<RadioButton>(R.id.radioButtonYes) as RadioButton
        val kollektivNo = findViewById<RadioButton>(R.id.radioButtonNo) as RadioButton
        val calculateButton = findViewById<Button>(R.id.bBerakna) as Button



        val toolBar = findViewById<Toolbar>(R.id.app_bar) as Toolbar
        toolBar.title=""
        setSupportActionBar(toolBar)
        var tvToolBar = findViewById<TextView>(R.id.tvToolbarTitle) as TextView
        tvToolBar.text = "Räkna ut din garantilön"
        toolBar.setNavigationIcon(R.drawable.abc_ic_ab_back_material)
        toolBar.setNavigationOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                onBackPressed()
            }})


        calculateButton.setOnClickListener {

            city = etCity.text.toString().toLowerCase().trim()
            hours = etHours.text.toString().toInt()
            arbetstidsmatt = etArbetstidsmatt.toString().toInt()


            }





           if( kollektivYes.isChecked){

            calculate(city, hours, arbetstidsmatt)

           }else{
               Toast.makeText(this, city, Toast.LENGTH_SHORT).show()

           }
        }


fun calculate(city: String, hours: Int, procent: Int ) {



    if(procent > 100){
        arbetstidsmatt = 100
    }

    if(city == "stockholm" || city == "stokholm" || city=="stocholm"){
        garanti = garantiLonSthml * arbetstidsmatt

    }else{
        garanti = garantiLon * arbetstidsmatt


    }
    timLon = (garanti /(garantiTimmar * arbetstidsmatt)).toInt()


    Toast.makeText(this, garanti, Toast.LENGTH_SHORT).show()

    }





}
