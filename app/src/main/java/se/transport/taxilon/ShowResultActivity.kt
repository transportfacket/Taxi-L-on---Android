package se.transport.taxilon

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import org.w3c.dom.Text
import spencerstudios.com.bungeelib.Bungee
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*
import kotlin.math.roundToInt


class ShowResultActivity : AppCompatActivity() {

    private var passedSalary: Double = 0.0
    private var passedHours: Double = 0.0

    private var calculateHolidayPay = 0.0
    private var calculateAverageHourlyWage = 0.0
    private var hourlyWage = 0
    private lateinit var calculatedSalaryTextview: TextView
    private lateinit var calculatedTotalSalaryTextView: TextView
    private lateinit var closeButton: Button
    private lateinit var vaccationPay: TextView
    private lateinit var averageHourlyWage: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_result)
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        initViews()
        receivingValue()

        val formatter = NumberFormat.getInstance(Locale.US) as DecimalFormat
        val symbols = formatter.decimalFormatSymbols

        symbols.groupingSeparator = ' '
        formatter.decimalFormatSymbols = symbols
        val formattedSalary = formatter.format(passedSalary.toInt())


        calculatedSalaryTextview.text = getString(R.string.tvCalculatedSalary, formattedSalary.toString())

        calculateHolidayPay = passedSalary / 100 * 13



        calculateAverageHourlyWage = passedSalary / passedHours





        val formattedHourlyWage = "%.2f".format(calculateAverageHourlyWage)

        val totalSalaryWithHoliday = passedSalary + calculateHolidayPay
        val formattedTotalSalary = formatter.format(totalSalaryWithHoliday.roundToInt())
        val formattedVaccationPay= formatter.format(calculateHolidayPay.roundToInt())

        calculatedTotalSalaryTextView.text = getString(R.string.tvCalculatedSalary, formattedTotalSalary.toString())

        vaccationPay.text = getString(R.string.tvCalculatedSalary, formattedVaccationPay.toString())
        averageHourlyWage.text = getString(R.string.tvCalculatedHourlyWage, formattedHourlyWage)



    }
    override fun onBackPressed() {
        super.onBackPressed()


    }


    private fun initViews(){
        calculatedSalaryTextview = findViewById(R.id.tvCalculatedSalary)
        averageHourlyWage = findViewById(R.id.tvAverageHourlyWage)
        calculatedTotalSalaryTextView = findViewById(R.id.tvCalculatedTotal)
        vaccationPay = findViewById(R.id.tvVaccationPay)

        closeButton = findViewById(R.id.bClose)
        closeButton.setOnClickListener {

            finish()
        }

    }

    private fun receivingValue() {
        val intent = intent
        passedSalary = intent.getDoubleExtra(AppConstants.PASSDATAKEY, 0.0)
        passedHours = intent.getDoubleExtra(AppConstants.PASSDATAKEY2, 0.0 )

    }


}
