package se.transport.taxilon



import android.content.Intent
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.*
import android.widget.RadioGroup
import kotlin.math.roundToInt


class CalculatorActivity : AppCompatActivity() {

    var percentageOfFullTime = 100
    private lateinit var kollektivYes : RadioButton
    private lateinit var kollektivNo : RadioButton
    private lateinit var seekBar : SeekBar
    lateinit var currentPercentage : TextView
    private lateinit var etLocation : EditText
    private lateinit var etWorkedHours : EditText
    private lateinit var kollektivGroup: RadioGroup
    private lateinit var workAllWeekGroup: RadioGroup
    private lateinit var calculateButton: Button
    private lateinit var infoCollectiveAgreement: ImageView
    private lateinit var infoLocation: ImageView
    private lateinit var infoWorkedHours: ImageView
    private lateinit var infoPercentageOfFulltime: ImageView
    private lateinit var infoWorkAllDays: ImageView
    private var hasCollectiveAgreement: Boolean = true
    private var workAllWeek: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculator)
        setupActionBar()
        initViews()
        seekBar()
        alertInfoDialogs()
        calculateButton.setOnClickListener {
            startCheckAndCalculateBeforePassing()


        }

    }



    private fun initViews(){
        kollektivYes = findViewById(R.id.radioButtonYes)
        kollektivNo = findViewById(R.id.radioButtonNo)
        seekBar = findViewById(R.id.seekBar)
        currentPercentage = findViewById(R.id.tvCurrentPercentage)
        etLocation = findViewById(R.id.etLocation)
        etWorkedHours = findViewById(R.id.etWorkedHours)
        calculateButton = findViewById(R.id.bBerakna)
        infoCollectiveAgreement = findViewById(R.id.infoCollectiveAgreement)
        infoLocation = findViewById(R.id.infoLocation)
        infoWorkedHours = findViewById(R.id.infoWorkedHours)
        infoPercentageOfFulltime = findViewById(R.id.infoPercentageOfFulltime)
        infoWorkAllDays = findViewById(R.id.infoWorkAllDays)


        workAllWeekGroup = findViewById<View>(R.id.rgWorkAllWeek) as RadioGroup
        workAllWeekGroup.setOnCheckedChangeListener { _, checkedId ->
            when(checkedId){
                R.id.radioButtonYes2 ->{
                    workAllWeek = true
                }
                R.id.radioButtonNo2 ->{
                    workAllWeek = false
                }
            }
        }

        kollektivGroup = findViewById<View>(R.id.rgCollectiveAgreement) as RadioGroup
        kollektivGroup.setOnCheckedChangeListener { _, checkedId ->
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








    }



    private fun alertInfoDialogs(){
        infoCollectiveAgreement.setOnClickListener {
            infoDialog(getString(R.string.titleCollectiveAgreementInfoDialog), getString(R.string.bodyCollectiveAgreementInfoDialog) )
        }
        infoLocation.setOnClickListener {
            infoDialog(getString(R.string.titleLocationInfoDialog), getString(R.string.bodyLocationInfoDialog))
        }
        infoWorkedHours.setOnClickListener {
            infoDialog(getString(R.string.titleWorkedHoursInfoDialog), getString(R.string.bodyWorkedHoursInfoDialog))

        }
        infoWorkAllDays.setOnClickListener {
            infoDialog(getString(R.string.titleWorkAllDaysInfoDialog), getString(R.string.bodyWorkAllDaysInfoDialog))

        }
        infoPercentageOfFulltime.setOnClickListener {
            infoDialog(getString(R.string.titlePercentageOfFulltimeInfoDialog), getString(R.string.bodyPercentageOfFulltimeInfoDialog))

        }
    }




    private fun seekBar(){

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





    private fun startCheckAndCalculateBeforePassing(){
        when(hasCollectiveAgreement){

            true -> {
                if (etLocation.text.isNullOrEmpty() && etWorkedHours.text.isNullOrEmpty()){

                    etLocation.setError(getString(R.string.notEmpty))
                    etWorkedHours.setError(getString(R.string.notEmpty))



                }else if (etLocation.text.isNullOrEmpty()){
                    etLocation.setError(getString(R.string.notEmpty))
                }else if (etWorkedHours.text.isNullOrEmpty()){
                    etWorkedHours.setError(getString(R.string.notEmpty))

                } else{
                    val calculatedResult = calculateSalaryBy(whereDoIWork(etLocation.text.toString()), etWorkedHours.text.toString().toInt(), percentageOfFullTime, workAllWeek )
                    val intent = Intent(this, ShowResultActivity::class.java)
                    intent.putExtra(AppConstants.PASSDATAKEY, calculatedResult.roundToInt().toString())
                    startActivity(intent)
                }

            }



            false -> {

            }

        }
    }






    private fun setupActionBar(){
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



    private fun infoDialog (title: String, message: String){

        val builder: AlertDialog.Builder = when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP -> AlertDialog.Builder(this)
            else -> AlertDialog.Builder(this)
        }
        builder.setTitle(title)
                .setCancelable(false)
                .setMessage(message)
                .setPositiveButton("OK", { _, _ ->
                })
                .show()

    }










    private fun whereDoIWork(location: String): Boolean {

    val location2 = location.toLowerCase().trim()

        when (location2) {
            "sthlm", "stockholm", "stokholm", "stocholm" -> return true
            else -> return false
        }
    }


    private fun calculateSalaryBy(isLocationSthlm: Boolean, workedHours: Int, percentageOfFulltime: Int, workAllWeekDays: Boolean): Double{

        val baseSalaryToCalculateFrom: Double = when (isLocationSthlm) {
            true -> AppConstants.GUARANTEESALARYSTHLM
            else -> AppConstants.GUARANTEESALARY
        }
        val baseHoursToCalculateFrom: Double = when (workAllWeekDays) {
            true -> AppConstants.GUARANTEEHOURS
            else -> AppConstants.GUARANTEEHOURSWEEKDAYS
        }


        val returnSalary: Double

        val percentageOfGuaranteeHours: Double
        percentageOfGuaranteeHours = (baseHoursToCalculateFrom / 100) * percentageOfFulltime



        returnSalary = when {
            workedHours <= percentageOfGuaranteeHours -> baseSalaryToCalculateFrom / 100 * percentageOfFulltime
            else -> baseSalaryToCalculateFrom / baseHoursToCalculateFrom * workedHours
        }
        return returnSalary
    }




}
