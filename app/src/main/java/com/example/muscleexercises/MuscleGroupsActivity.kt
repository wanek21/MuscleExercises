package com.example.muscleexercises

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton

class MuscleGroupsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_muscle_groups)

        findViewById<ImageButton>(R.id.btnBack).setOnClickListener { finish() }

        findViewById<Button>(R.id.btnArms).setOnClickListener {
            toMuscleActivity("arms")
        }
        findViewById<Button>(R.id.btnChest).setOnClickListener {
            toMuscleActivity("chest")
        }
        findViewById<Button>(R.id.btnAbs).setOnClickListener {
            toMuscleActivity("abs")
        }
        findViewById<Button>(R.id.btnLegs).setOnClickListener {
            toMuscleActivity("legs")
        }
    }

    private fun toMuscleActivity(type: String) {
        val intent = Intent(this, MuscleActivity::class.java)
        intent.putExtra("type", type)
        startActivity(intent)
    }
}