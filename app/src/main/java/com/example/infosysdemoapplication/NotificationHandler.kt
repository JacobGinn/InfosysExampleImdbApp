package com.example.infosysdemoapplication

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService

class NotificationHandler : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("FCM", "Refreshed the token -> $token")
    }
}