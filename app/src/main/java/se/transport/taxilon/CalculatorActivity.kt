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
import spencerstudios.com.bungeelib.Bungee
import kotlin.math.roundToInt


class CalculatorActivity : AppCompatActivity() {

    private lateinit var kollektivYes : RadioButton
    private lateinit var kollektivNo : RadioButton
    private lateinit var etLocation : EditText
    private lateinit var etWorkedHours : EditText
    private lateinit var kollektivGroup: RadioGroup
    private lateinit var calculateButton: Button
    private lateinit var infoCollectiveAgreement: ImageView
    private lateinit var infoLocation: ImageView
    private lateinit var infoWorkedHours: ImageView
    private var hasCollectiveAgreement: Boolean = true

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
                    val salary = calculateSalaryBy(whereDoIWork(etLocation.text.toString()), etWorkedHours.text.toString().toInt())
                    val intent = Intent(this, ShowResultActivity::class.java)



                    intent.putExtra(AppConstants.PASSDATAKEY, salary.roundToInt().toString())

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
        Bungee.slideRight(this)
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


    private fun calculateSalaryBy(isLocationSthlm: Boolean, workedHours: Int): Double{

        val baseSalaryToCalculateFrom: Double = when (isLocationSthlm) {
            true -> AppConstants.GUARANTEESALARYSTHLM
            else -> AppConstants.GUARANTEESALARY
        }



        val returnSalary: Double



        returnSalary = when {
            workedHours <= AppConstants.GUARANTEEHOURS -> {
                baseSalaryToCalculateFrom
            }
            else -> baseSalaryToCalculateFrom / AppConstants.GUARANTEEHOURS * workedHours
        }



        return returnSalary
    }




}
