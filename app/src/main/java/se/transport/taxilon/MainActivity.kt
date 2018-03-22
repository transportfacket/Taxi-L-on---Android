package se.transport.taxilon

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import android.support.v7.app.AlertDialog
import android.media.MediaPlayer
import android.net.Uri
import android.widget.Button
import android.widget.ImageView
import spencerstudios.com.bungeelib.Bungee


class MainActivity : AppCompatActivity() {

    private lateinit var calculatorButton: Button
    private lateinit var monthlyButton: Button
    private lateinit var faqButton: Button
    private lateinit var logoButton: ImageView
    private lateinit var joinButton: Button
    private var tapCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        calculatorButton = findViewById(R.id.calculatorButton)
        monthlyButton = findViewById(R.id.monthlyButton)
        faqButton = findViewById(R.id.faqButton)
        logoButton = findViewById(R.id.logo)
        joinButton = findViewById(R.id.joinButton)

        calculatorButton.setOnClickListener{
            val intent = Intent(this, CalculatorActivity::class.java)
            startActivity(intent)
            Bungee.slideLeft(this)

        }

        monthlyButton.setOnClickListener {
            val intent = Intent(this, MonthlyActivity::class.java)
            startActivity(intent)
            Bungee.slideLeft(this)
        }

        faqButton.setOnClickListener {
            val intent = Intent(this, FaqActivity::class.java)
            startActivity(intent)
            Bungee.slideLeft(this)
        }

        joinButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.transport.se/blimedlem/"))
            startActivity(intent)
            Bungee.shrink(this)

        }


        logoButton.setOnClickListener {
            when {
                tapCount < 11 -> tapCount += 1
                else -> {
                    alertDialog()
                    tapCount = 0
                }
            }
        }

    }









    private fun alertDialog (){
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

    override fun onBackPressed() {
        super.onBackPressed()

        Bungee.shrink(this)

    }

    override fun onStop() {
        super.onStop()
        Bungee.shrink(this)


    }

}