package com.example.alarmdemo

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var mediaPlayer: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize MediaPlayer
        mediaPlayer = MediaPlayer.create(this, R.raw.wakeup_audio) // Replace 'wakeup_audio' with your file name

        // Initialize views
        val button: Button = findViewById(R.id.buttonSetAlarm)
        val editText: EditText = findViewById(R.id.editTextNumber)

        // Set OnClickListener for the button
        button.setOnClickListener {
            // Start playing the audio immediately when the button is clicked
            if (!mediaPlayer.isPlaying) {
                mediaPlayer.start() // Start playback
            }

            try {
                val sec: Int = editText.text.toString().toInt()
                val intent = Intent(applicationContext, MyBroadcastReceiver::class.java)
                val pendingIntent = PendingIntent.getBroadcast(
                    applicationContext,
                    111,
                    intent,
                    PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
                )
                val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
                alarmManager.set(
                    AlarmManager.RTC_WAKEUP,
                    System.currentTimeMillis() + (sec * 1000),
                    pendingIntent
                )
                Toast.makeText(
                    applicationContext,
                    "Alarm is set for $sec seconds",
                    Toast.LENGTH_LONG
                ).show()
            } catch (e: NumberFormatException) {
                Toast.makeText(applicationContext, "Please enter a valid number", Toast.LENGTH_SHORT).show()
            }
        }

        // Release MediaPlayer resources when activity is destroyed
        mediaPlayer.setOnCompletionListener {
            mediaPlayer.release()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // Make sure to stop and release the mediaPlayer if it's playing
        if (::mediaPlayer.isInitialized && mediaPlayer.isPlaying) {
            mediaPlayer.stop()
            mediaPlayer.release()
        }
    }
}
