package com.example.alarmdemo

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer

class MyBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val mediaPlayer = MediaPlayer.create(context, R.raw.wakeup_audio)
        mediaPlayer.start()
    }
}
