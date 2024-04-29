package com.khushi.quizapp

//import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import com.google.gson.Gson
import com.khushi.quizapp.databinding.ActivityMainBinding
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var quizQuestions: Array<QuizQuestion>
    private var questionIndex = 0
    var count = 0

    private var score = 0
    private var timer: CountDownTimer? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadQuestions()
        startQuestion()

        binding.option1.setOnClickListener { checkAnswer(0) }
        binding.option2.setOnClickListener { checkAnswer(1) }
        binding.option3.setOnClickListener { checkAnswer(2) }
        binding.option4.setOnClickListener { checkAnswer(3) }



        binding.next.visibility = View.GONE
        binding.next.setOnClickListener {
//            questionIndex++
            startQuestion()

//
//            if (questionIndex >= quizQuestions.size) {
//                // End of the quiz
////                showScore()
////                startActivity(Intent(this,Score::class.java))
////                finish()
//
//
//            } else {
//                // Start the next question
//                binding.next.visibility=View.GONE
//
//                startQuestion()
//                binding.option1.isEnabled=true
//                binding.option2.isEnabled=true
//                binding.option3.isEnabled=true
//                binding.option4.isEnabled=true
//                binding.option1.setBackgroundDrawable(getDrawable(R.drawable.options_bck))
//                binding.option2.setBackgroundDrawable(getDrawable(R.drawable.options_bck))
//                binding.option3.setBackgroundDrawable(getDrawable(R.drawable.options_bck))
//                binding.option4.setBackgroundDrawable(getDrawable(R.drawable.options_bck))
//
//            }
//            timer?.start()
        }

    }

    private fun startQuestion() {
        val totalques = quizQuestions.size
        binding.questionNo.text = "Question:${(questionIndex + 1)}/$totalques"

        val quizQuestion = quizQuestions[questionIndex]

        binding.question.text = quizQuestion.question
        binding.option1.text = quizQuestion.options[0]
        binding.option2.text = quizQuestion.options[1]
        binding.option3.text = quizQuestion.options[2]
        binding.option4.text = quizQuestion.options[3]


        // Start the timer
        timer = object : CountDownTimer(5000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val secondsLeft = millisUntilFinished / 1000
                binding.timer.text = "Time left: $secondsLeft seconds"
            }

            override fun onFinish() {
                // Handle the timer finish
//                showAnswer(-1)
                questionIndex++
                if (questionIndex >= quizQuestions.size) {
                    // End of the quiz
//                showScore()
                    val intent = Intent(this@MainActivity, Score::class.java)
                    intent.putExtra("score", score)
                    intent.putExtra("total", totalques)
                    startActivity(intent)
                    finish()


                } else {
                    // Start the next question
                    binding.questionNo.text = "$questionIndex/$totalques"

                    binding.next.visibility = View.GONE

                    startQuestion()
                    binding.option1.isEnabled = true
                    binding.option2.isEnabled = true
                    binding.option3.isEnabled = true
                    binding.option4.isEnabled = true
                    binding.option1.setBackgroundDrawable(getDrawable(R.drawable.options_bck))
                    binding.option2.setBackgroundDrawable(getDrawable(R.drawable.options_bck))
                    binding.option3.setBackgroundDrawable(getDrawable(R.drawable.options_bck))
                    binding.option4.setBackgroundDrawable(getDrawable(R.drawable.options_bck))

                }


            }
        }

        timer?.start()
    }

    private fun checkAnswer(selectedOptionIndex: Int) {
        // Cancel the timer
//        timer?.cancel()
        val optionButton1 = binding.option1
        val optionButton2 = binding.option2
        val optionButton3 = binding.option3
        val optionButton4 = binding.option4
        val correctAnswerIndex = quizQuestions[questionIndex].correctAnswer
        val isCorrectAnswer = selectedOptionIndex == correctAnswerIndex
        if (isCorrectAnswer) {
            score++
            when (selectedOptionIndex) {
                0 -> optionButton1.setBackgroundDrawable(getDrawable(R.drawable.bck_answer))
                1 -> optionButton2.setBackgroundDrawable(getDrawable(R.drawable.bck_answer))
                2 -> optionButton3.setBackgroundDrawable(getDrawable(R.drawable.bck_answer))
                3 -> optionButton4.setBackgroundDrawable(getDrawable(R.drawable.bck_answer))
            }
        } else {
            when (selectedOptionIndex) {
                0 -> optionButton1.setBackgroundDrawable(getDrawable(R.drawable.wrong_bck))
                1 -> optionButton2.setBackgroundDrawable(getDrawable(R.drawable.wrong_bck))
                2 -> optionButton3.setBackgroundDrawable(getDrawable(R.drawable.wrong_bck))
                3 -> optionButton4.setBackgroundDrawable(getDrawable(R.drawable.wrong_bck))
            }
            when (correctAnswerIndex) {
                0 -> optionButton1.setBackgroundDrawable(getDrawable(R.drawable.right_bck))
                1 -> optionButton2.setBackgroundDrawable(getDrawable(R.drawable.right_bck))
                2 -> optionButton3.setBackgroundDrawable(getDrawable(R.drawable.right_bck))
                3 -> optionButton4.setBackgroundDrawable(getDrawable(R.drawable.right_bck))
            }
        }

        optionButton1.isEnabled = false
        optionButton2.isEnabled = false
        optionButton3.isEnabled = false
        optionButton4.isEnabled = false

        // Show the "Next" button
//        binding.next.isEnabled=true
//        timer?.cancel()
//        binding.next.visibility=View.VISIBLE

    }


    private fun loadJSONFromAsset(filename: String): String? {
        var json: String? = null
        try {
            val inputStream = assets.open(filename)
            val bufferedReader = BufferedReader(InputStreamReader(inputStream))
            val stringBuilder = StringBuilder()
            var line: String?
            while (bufferedReader.readLine().also { line = it } != null) {
                stringBuilder.append(line)
            }
            bufferedReader.close()
            inputStream.close()
            json = stringBuilder.toString()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return json
    }

    private fun loadQuestions(): Array<QuizQuestion> {
        val jsonString = loadJSONFromAsset("questions.json")
        val gson = Gson()
        quizQuestions = gson.fromJson(jsonString, Array<QuizQuestion>::class.java)
//        Log.d("QuizActivity", "quizJsonString: $jsonString")

        return quizQuestions
    }


}
