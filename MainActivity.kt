package com.example.instagram

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var timerTextView: TextView
    private lateinit var yesButton: Button
    private lateinit var noButton: Button
    private var countDownTimer: CountDownTimer? = null
    private var timeLeftInMillis: Long = 10000 // 10 seconds in milliseconds
    private var isTimerRunning: Boolean = false
    private var isNoPressed: Boolean = false // Flag to track if "No" was pressed

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        timerTextView = findViewById(R.id.timerTextView)
        yesButton = findViewById(R.id.yesButton)
        noButton = findViewById(R.id.noButton)

        yesButton.isEnabled = false // Disable initially
        noButton.isEnabled = false // Disable initially

        // Start countdown timer
        startCountdownTimer()

        yesButton.setOnClickListener {
            openApp()
        }

        noButton.setOnClickListener {
            isNoPressed = true // Set the flag to true
            finish() // Close the app
        }
    }

    private fun startCountdownTimer() {
        countDownTimer = object : CountDownTimer(timeLeftInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeLeftInMillis = millisUntilFinished
                updateTimerText()
            }

            override fun onFinish() {
                // Only open the app if "No" was not pressed
                if (!isNoPressed) {
                    openApp()
                }
            }
        }.start()

        isTimerRunning = true
        // Enable buttons when the countdown starts
        yesButton.isEnabled = true
        noButton.isEnabled = true
    }

    private fun updateTimerText() {
        val seconds = (timeLeftInMillis / 1000).toInt()
        timerTextView.text = String.format("%02d", seconds)
    }

    private fun openApp() = try {
        // Example: Opening Instagram
        val intent = packageManager.getLaunchIntentForPackage("com.instagram.android")
        if (intent != null) {
            startActivity(intent)
        } else {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com")))
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}
