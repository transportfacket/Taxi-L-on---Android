package se.transport.taxilon

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast



class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }



    fun calculatorButtonOnClick(v: View) {
        val intent = Intent(this, CalculatorActivity::class.java)
        startActivity(intent)
    }
}
