package se.transport.taxilon



import android.content.Intent

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.constraint.ConstraintLayout

import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.*
import android.widget.RadioGroup
import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar
import org.jetbrains.anko.alert
import spencerstudios.com.bungeelib.Bungee





class CalculatorActivity : AppCompatActivity() {

    private lateinit var kollektivYes : RadioButton
    private lateinit var kollektivNo : RadioButton
    private lateinit var etLocation : EditText
    private lateinit var etWorkedHours : EditText
    private lateinit var kollektivGroup: RadioGroup
    private lateinit var calculateButton: Button
    private lateinit var infoCollectiveAgreement: ImageView
    private lateinit var layout: ConstraintLayout
    private lateinit var infoLocation: ImageView
    private lateinit var infoWorkedHours: ImageView
    private lateinit var infoWorkingTimescale: ImageView
    private lateinit var seekBar: DiscreteSeekBar
    private lateinit var tvPercentage: TextView
    private var hasCollectiveAgreement: Boolean = true
    private var workingTimescalePercentage: Int = 0
    data class PassOnValues(val salary: Double, val hours: Double)



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculator)
        setupActionBar()
        initViews()
        alertInfoDialogs()
        calculateButton.setOnClickListener {
            startCheckAndCalculateBeforePassing()




        }

    }


    override fun onBackPressed() {
        super.onBackPressed()
        Bungee.slideRight(this)

    }
    private fun initViews(){
        kollektivYes = findViewById(R.id.radioButtonYes)
        kollektivNo = findViewById(R.id.radioButtonNo)
        etLocation = findViewById(R.id.etLocation)
        etWorkedHours = findViewById(R.id.etWorkedHours)
        calculateButton = findViewById(R.id.bBerakna)
        infoCollectiveAgreement = findViewById(R.id.infoCollectiveAgreement)
        infoLocation = findViewById(R.id.infoLocation)
        infoWorkedHours = findViewById(R.id.infoWorkedHours)
        infoWorkingTimescale = findViewById(R.id.infoWorkingTimescale)
        layout = findViewById(R.id.layout)
        seekBar = findViewById(R.id.seekBar)
        tvPercentage = findViewById(R.id.tvPercentage)
        tvPercentage.text = "0 %"


        seekBar.setOnProgressChangeListener(object : DiscreteSeekBar.OnProgressChangeListener {


            override fun onProgressChanged(seekBar: DiscreteSeekBar, value: Int, fromUser: Boolean) {


            }


            override fun onStartTrackingTouch(seekBar: DiscreteSeekBar) {


            }


            override fun onStopTrackingTouch(seekBar: DiscreteSeekBar) {

                tvPercentage.text = seekBar.progress.toString() + " %"
                workingTimescalePercentage = seekBar.progress

            }
        })

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
                    infoDialog(getString(R.string.no_collective_agreement),  getString(R.string.bodyCollectiveAgreementInfoDialog))

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
        infoWorkingTimescale.setOnClickListener {
            infoDialog(getString(R.string.titleWorkingTimescaleInfoDialog), getString(R.string.bodyWorkingTimescaleInfoDialog))

        }

    }














    private fun startCheckAndCalculateBeforePassing(){
        when(hasCollectiveAgreement){

            true -> {
                checkingParametersBeforeCalculating()
            }
            false -> {

            }

        }
    }


    private fun checkingParametersBeforeCalculating(){
        if (etLocation.text.isNullOrEmpty() && etWorkedHours.text.isNullOrEmpty()){

            etLocation.error = getString(R.string.notEmpty)
            etWorkedHours.error = getString(R.string.notEmpty)

        }else if (etLocation.text.isNullOrEmpty()){
            etLocation.error = getString(R.string.notEmpty)
        }else if (etWorkedHours.text.isNullOrEmpty()){
            etWorkedHours.error = getString(R.string.notEmpty)

        } else if (workingTimescalePercentage == 0){
            zeroPercentWorkingTimescaleDialog(getString(R.string.do_you_wanna_continue), getString(R.string.do_you_wanna_continue_message))


        }else{
            getValuesAndStartIntent()

        }


    }




private fun getValuesAndStartIntent(){
    val (salary, hours) = calculateSalaryBy(whereDoIWork(etLocation.text.toString()), etWorkedHours.text.toString().replace(",", ".").toDouble(), workingTimescalePercentage)
    val intent = Intent(this, ShowResultActivity::class.java)

    intent.putExtra(AppConstants.PASSDATAKEY, salary)
    intent.putExtra(AppConstants.PASSDATAKEY2, hours)
    intent.putExtra(AppConstants.PASSDATAKEY3, workingTimescalePercentage)

    startActivity(intent)
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
        Bungee.slideRight(this)
    }

    private fun infoDialog (withTitle: String, andMessage: String){
        alert(andMessage) {
            title = withTitle
            positiveButton("Ok") {  }
        }.show()
    }

    private fun zeroPercentWorkingTimescaleDialog (withTitle: String, andMessage: String){
        alert(andMessage) {
            title = withTitle
            positiveButton(getString(R.string.yes)) { getValuesAndStartIntent() }
            negativeButton(getString(R.string.no)) { }
        }.show()
    }







    private fun whereDoIWork(location: String): Boolean {

    val location2 = location.toLowerCase().trim()

        when (location2) {
            "sthlm", "stockholm", "stokholm", "stocholm" -> return true
            else -> return false
        }
    }


    private fun calculateSalaryBy(isLocationSthlm: Boolean, hours: Double, workingTimescale: Int): PassOnValues{

        var calculateWithPercentage = 0
        if (workingTimescalePercentage == 0) {
            calculateWithPercentage = 100
        }else{
            calculateWithPercentage = workingTimescale
        }

        val baseSalaryToCalculateFrom: Double = when (isLocationSthlm) {
            true -> AppConstants.GUARANTEESALARYSTHLM
            else -> AppConstants.GUARANTEESALARY
        }



        val returnSalary: Double



        returnSalary = when {
            hours <= AppConstants.GUARANTEEHOURS -> {
                baseSalaryToCalculateFrom

                var workedHours = AppConstants.GUARANTEEHOURS / 100 * calculateWithPercentage
                 baseSalaryToCalculateFrom / AppConstants.GUARANTEEHOURS * workedHours

            }
            else -> baseSalaryToCalculateFrom / AppConstants.GUARANTEEHOURS * hours
        }



        return PassOnValues(returnSalary, hours)
    }




}
