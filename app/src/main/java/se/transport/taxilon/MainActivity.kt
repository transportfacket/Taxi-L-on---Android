package se.transport.taxilon

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View


import android.os.Build
import android.support.v7.app.AlertDialog
import android.media.MediaPlayer
import android.widget.Button
import android.widget.ImageView


class MainActivity : AppCompatActivity() {

    lateinit var calculatorButton: Button
    lateinit var monthlyButton: Button
    lateinit var faqButton: Button
    lateinit var logoButton: ImageView
    var tapCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        calculatorButton = findViewById(R.id.calculatorButton)
        monthlyButton = findViewById(R.id.monthlyButton)
        faqButton = findViewById(R.id.faqButton)
        logoButton = findViewById(R.id.logo)

        calculatorButton.setOnClickListener{
            val intent = Intent(this, CalculatorActivity::class.java)
            startActivity(intent)
        }

        monthlyButton.setOnClickListener {
            val intent = Intent(this, MonthlyActivity::class.java)
            startActivity(intent)
        }

        faqButton.setOnClickListener {
            val intent = Intent(this, FaqActivity::class.java)
            startActivity(intent)
        }


        logoButton.setOnClickListener {
            if (tapCount < 11) {
                tapCount += 1
            } else {
                alertDialog()
                tapCount = 0
            }
        }

    }









    fun alertDialog (){
        val mPlayer = MediaPlayer.create(this, R.raw.audio)
        mPlayer.start()

        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle("Du hittade hit!")
                .setCancelable(false)
                .setMessage("Denna app utvecklades av Patrik Persson på Transports avdelning 12 i Malmö")

                .setNeutralButton(android.R.string.yes, { _, _ ->
                    mPlayer.stop()

                })



                .show()

    }

}