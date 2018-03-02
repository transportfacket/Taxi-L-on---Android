package se.transport.taxilon


import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.View
import android.widget.*
import android.widget.RadioGroup
import kotlin.math.roundToInt


class CalculatorActivity : AppCompatActivity() {

    private val CALL_PERMISSION_CODE = 1



    var TAG: String = ""


    val guaranteeSalarySthlm = 19572.0
    val guaranteeSalary = 19211.0
    val guaranteeHours = 166.4
    val guaranteeHoursWeekdays = 174.0
    var city = ""

    var arbetstidsmatt = 0
    var garanti = 0
    var timLon = 0
    var lon = 0
    var realLon = 0

    var percentageOfFullTime = 100
    lateinit var kollektivYes : RadioButton
    lateinit var kollektivNo : RadioButton
    lateinit var seekBar : SeekBar
    lateinit var currentPercentage : TextView
    lateinit var etLocation : EditText
    lateinit var etWorkedHours : EditText
    lateinit var workedYes: RadioButton
    lateinit var kollektivGroup: RadioGroup
    lateinit var calculateButton: Button
    var hasCollectiveAgreement: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculator)
        setupActionBar()

        kollektivYes = findViewById(R.id.radioButtonYes)
        kollektivNo = findViewById(R.id.radioButtonNo)
        seekBar = findViewById(R.id.seekBar)
        currentPercentage = findViewById(R.id.tvCurrentPercentage)
        etLocation = findViewById(R.id.etLocation)
        etWorkedHours = findViewById(R.id.etWorkedHours)
        calculateButton = findViewById(R.id.bBerakna)


        seekBar()

        radioGroupCollectiveAgreement()


        calculateButton.setOnClickListener {
            when(hasCollectiveAgreement){

                true -> {

                    val test = calculateSalaryBy(whereDoIWork(etLocation.text.toString()), etWorkedHours.text.toString().toInt(), percentageOfFullTime, true )

                    Toast.makeText(this, test.roundToInt().toString(), Toast.LENGTH_SHORT).show()
                }

                false -> {

                }

            }

        }

    }


    fun seekBar(){

        seekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, progress: Int, p2: Boolean) {
                percentageOfFullTime = progress
                val percentageText = percentageOfFullTime.toString() + "%"
                currentPercentage.text = percentageText

            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }
        })





    }


    fun radioGroupCollectiveAgreement(){
        kollektivGroup = findViewById<View>(R.id.rgCollectiveAgreement) as RadioGroup
        kollektivGroup.setOnCheckedChangeListener(object : RadioGroup.OnCheckedChangeListener {
            override fun onCheckedChanged(group: RadioGroup, checkedId: Int) {



                when(checkedId){
                    R.id.radioButtonYes ->{
                        hasCollectiveAgreement = true
                        calculateButton.setBackgroundResource(R.drawable.button_bg_rounded_corners)

                    }
                    R.id.radioButtonNo ->{
                       hasCollectiveAgreement = false
                        calculateButton.setBackgroundResource(R.drawable.button_bg_rounded_corners_grey)
                       infoDialog("Inget kollektivavtal - Ingen garantilön", "För att bli")

                    }
                }




            }
        })
    }




    fun setupActionBar(){
        val toolBar = findViewById<Toolbar>(R.id.app_bar) as Toolbar
        toolBar.title=""
        setSupportActionBar(toolBar)
        val tvToolBar = findViewById<TextView>(R.id.tvToolbarTitle) as TextView
        tvToolBar.text = getString(R.string.calculatorButtonString)
        toolBar.setPadding(0, 0, 0, 0)//for tab otherwise give space in tab
        toolBar.setContentInsetsAbsolute(0, 0)
    }

    fun backButton(v: View){
        onBackPressed()
    }










    fun infoDialog (title: String, message: String){

        val builder: AlertDialog.Builder
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = AlertDialog.Builder(this)
        } else {
            builder = AlertDialog.Builder(this)
        }
        builder.setTitle(title)
                .setCancelable(false)
                .setMessage(message)
                .setPositiveButton("OK", { _, _ ->
                })
                .show()

    }










    fun whereDoIWork(location: String): Boolean {

    val location2 = location.toLowerCase().trim()

        if (location2 == "sthlm" || location2 == "stockholm" || location2 == "stokholm" || location2 == "stocholm" ) {

            return true
        }
        return false
    }


    fun calculateSalaryBy(isLocationSthlm: Boolean, workedHours: Int, percentageOfFulltime: Int, workAllWeekDays: Boolean): Double{

        var baseSalaryToCalculateFrom: Double
        if (isLocationSthlm == true) {
            baseSalaryToCalculateFrom = guaranteeSalarySthlm


        }else {

            baseSalaryToCalculateFrom = guaranteeSalary
        }



        val returnSalary: Double

        val percantageOfGuaranteeHours: Double = (guaranteeHours / 100) * percentageOfFulltime



        if (workedHours <= percantageOfGuaranteeHours) {
            returnSalary = baseSalaryToCalculateFrom / 100 * percentageOfFulltime


        }else {

            returnSalary = baseSalaryToCalculateFrom / guaranteeHours * workedHours
        }
        return returnSalary
    }




}
