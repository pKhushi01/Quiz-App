package com.khushi.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.khushi.quizapp.databinding.ActivityStartBinding

class Start : AppCompatActivity() {
    private lateinit var binding: ActivityStartBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityStartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.play.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
        }

    }
}