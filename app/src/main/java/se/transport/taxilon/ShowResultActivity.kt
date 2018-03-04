package se.transport.taxilon

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import org.w3c.dom.Text
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*


class ShowResultActivity : AppCompatActivity() {

    private var passedSalary: String = ""
    private var calculateHolidayPay = 0
    private lateinit var calculatedSalaryTextview: TextView
    private lateinit var calculatedTotalSalaryTextView: TextView
    private lateinit var closeButton: Button
    private lateinit var vaccationPay: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_result)
        initViews()
        receivingValue()

        val formatter = NumberFormat.getInstance(Locale.US) as DecimalFormat
        val symbols = formatter.decimalFormatSymbols

        symbols.groupingSeparator = ' '
        formatter.decimalFormatSymbols = symbols
        val formattedSalary = formatter.format(passedSalary.toInt())


        calculatedSalaryTextview.text = getString(R.string.tvCalculatedSalary, formattedSalary.toString())

        calculateHolidayPay = passedSalary.toInt() / 100 * 13

        val totalSalaryWithHoliday = passedSalary.toInt() + calculateHolidayPay
        val formattedTotalSalary = formatter.format(totalSalaryWithHoliday)
        val formattedVaccationPay= formatter.format(calculateHolidayPay)
        calculatedTotalSalaryTextView.text = getString(R.string.tvCalculatedSalary, formattedTotalSalary.toString())

        vaccationPay.text = getString(R.string.tvCalculatedSalary, formattedVaccationPay.toString())



    }


    private fun initViews(){
        calculatedSalaryTextview = findViewById(R.id.tvCalculatedSalary)

        calculatedTotalSalaryTextView = findViewById(R.id.tvCalculatedTotal)
        vaccationPay = findViewById(R.id.tvVaccationPay)
        closeButton = findViewById(R.id.bClose)


        closeButton.setOnClickListener {
            finish()
        }



    }

    private fun receivingValue() {
        val intent = intent
        passedSalary = intent.getStringExtra(AppConstants.PASSDATAKEY)

    }


}
