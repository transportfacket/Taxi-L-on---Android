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
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.View
import android.widget.*
import android.widget.RadioGroup




class CalculatorActivity : AppCompatActivity() {




    var TAG: String = ""


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

    var percentageOfFullTime = 0
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
                        alertDialog()

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


    fun calculatorButton(v: View){

        when(hasCollectiveAgreement){

            true -> {

                calculateSalaryBy(whereDoIWork(etLocation.text.toString()), etWorkedHours.text.toString().toInt(), percentageOfFullTime, true )
            }

            false -> {

            }

        }




    }



    @SuppressLint("MissingPermission")
    fun alertDialog (){


        val builder: AlertDialog.Builder
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = AlertDialog.Builder(this)
        } else {
            builder = AlertDialog.Builder(this)
        }
        builder.setTitle("Inget kollektivavtal - Ingen garantilön")
                .setCancelable(false)
                .setMessage("Denna app utvecklades av Patrik Persson på Transports avdelning 12 i Malmö")
                .setPositiveButton("OK", { _, _ ->


                })
                .setNeutralButton("Ring Transport", { _, _ ->
                    setupPermissions()
                    val intent = Intent(Intent.ACTION_CALL)
                    intent.data = Uri.parse("tel:0104803000")
                    startActivity(intent)


                })



                .show()

    }



    private fun setupPermissions() {
        val permission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.CALL_PHONE)

        if (permission != PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "Permission to record denied")
        }
    }



    fun whereDoIWork(location: String): Boolean {

    val location2 = location.toLowerCase().trim()

        if (location2 == "sthlm" || location2 == "stockholm" || location2 == "stokholm" || location2 == "stocholm" ) {

            return true
        }
        return false
    }


    fun calculateSalaryBy(isLocationSthlm: Boolean, workedHours: Int, percentageOfFulltime: Int, workAllWeekDays: Boolean): Int{





        return 0

    }


}
