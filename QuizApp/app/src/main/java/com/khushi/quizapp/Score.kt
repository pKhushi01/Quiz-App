package com.khushi.quizapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.khushi.quizapp.databinding.ActivityScoreBinding

class Score : AppCompatActivity() {
    private lateinit var binding: ActivityScoreBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScoreBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        val totalscore = intent.getStringExtra("total")
//        val correctscore = intent.getStringExtra("score")
//        binding.score.text = "$correctscore/$totalscore"


    }
}