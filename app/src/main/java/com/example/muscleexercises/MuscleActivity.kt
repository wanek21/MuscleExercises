package com.example.muscleexercises

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import pl.droidsonroids.gif.GifImageView

class MuscleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_muscle)

        val tvMainTitle = findViewById<TextView>(R.id.tvMainTitle)
        val title1 = findViewById<TextView>(R.id.tvTitle1)
        val title2 = findViewById<TextView>(R.id.tvTitle2)
        val title3 = findViewById<TextView>(R.id.tvTitle3)
        val ex1 = findViewById<TextView>(R.id.ex1)
        val ex2 = findViewById<TextView>(R.id.ex2)
        val ex3 = findViewById<TextView>(R.id.ex3)
        val ex1Img = findViewById<GifImageView>(R.id.ex1Img)
        val ex2Img = findViewById<GifImageView>(R.id.ex2Img)
        val ex3Img = findViewById<GifImageView>(R.id.ex3Img)

        findViewById<ImageButton>(R.id.btnBack).setOnClickListener { finish() }

        when(intent.getStringExtra("type")) {
            "arms" -> {
                tvMainTitle.text = "Arms"
                title1.text = resources.getText(R.string.arm_title1)
                title2.text = resources.getText(R.string.arm_title2)
                title3.text = resources.getText(R.string.arm_title3)

                ex1.text = resources.getText(R.string.arm_ex1)
                ex2.text = resources.getText(R.string.arm_ex2)
                ex3.text = resources.getText(R.string.arm_ex3)

                ex1Img.setImageResource(R.drawable.arm1)
                ex2Img.setImageResource(R.drawable.arm2)
                ex3Img.setImageResource(R.drawable.arm3)
            }
            "chest" -> {
                tvMainTitle.text = "Chest"
                title1.text = resources.getText(R.string.chest_title1)
                title2.text = resources.getText(R.string.chest_title2)
                title3.text = resources.getText(R.string.chest_title3)

                ex1.text = resources.getText(R.string.chest_ex1)
                ex2.text = resources.getText(R.string.chest_ex2)
                ex3.text = resources.getText(R.string.chest_ex3)

                ex1Img.setImageResource(R.drawable.chest1)
                ex2Img.setImageResource(R.drawable.chest2)
                ex3Img.setImageResource(R.drawable.chest3)
            }
            "abs" -> {
                tvMainTitle.text = "Abs"
                title1.text = resources.getText(R.string.abs_title1)
                title2.text = resources.getText(R.string.abs_title2)
                title3.text = resources.getText(R.string.abs_title3)

                ex1.text = resources.getText(R.string.abs_ex1)
                ex2.text = resources.getText(R.string.abs_ex2)
                ex3.text = resources.getText(R.string.abs_ex3)

                ex1Img.setImageResource(R.drawable.abs1)
                ex2Img.setImageResource(R.drawable.abs2)
                ex3Img.setImageResource(R.drawable.abs3)
            }
            "legs" -> {
                tvMainTitle.text = "Legs"
                title1.text = resources.getText(R.string.legs_title1)
                title2.text = resources.getText(R.string.legs_title2)
                title3.text = resources.getText(R.string.legs_title3)

                ex1.text = resources.getText(R.string.legs_ex1)
                ex2.text = resources.getText(R.string.legs_ex2)
                ex3.text = resources.getText(R.string.legs_ex3)

                ex1Img.setImageResource(R.drawable.legs1)
                ex2Img.setImageResource(R.drawable.legs2)
                ex3Img.setImageResource(R.drawable.legs3)
            }
        }

    }
}