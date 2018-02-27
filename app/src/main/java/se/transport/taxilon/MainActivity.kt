package se.transport.taxilon

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View

import android.view.animation.AnimationUtils




class MainActivity : AppCompatActivity() {





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }



    fun calculatorButtonOnClick(v: View) {

        val intent = Intent(this, CalculatorActivity::class.java)
        startActivity(intent)
    }


    fun monthlyButtonOnClick(v: View) {

        val intent = Intent(this, MonthlyActivity::class.java)
        startActivity(intent)
    }


    fun faqButtonOnClick(v: View) {

        val intent = Intent(this, FaqActivity::class.java)
        startActivity(intent)
    }
}
