package com.example.lockon

import android.content.SharedPreferences
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import org.json.JSONArray

class QuestionActivity : AppCompatActivity() {
    lateinit var tvquestion: TextView
    lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_question)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val lockedPackage = intent.getStringExtra("LOCKED_PACKAGE")?:""


        var result = 0
        var currque = 0
        sharedPref = getSharedPreferences("MyPrefsFile", MODE_PRIVATE)
        var Queno : TextView = findViewById<TextView>(R.id.questionno)
        Queno.text = " ${currque + 1} question of 3"


        fun loadQuestions(): List<Question> {

            val inputStream = assets.open("questions.json")
            val json = inputStream.bufferedReader().use { it.readText() }
            val jsonArray = JSONArray(json)
            var currentQuestionIndex = 0
            var questions = mutableListOf<Question>()

            for (i in 0 until jsonArray.length()) {
                var question = jsonArray.getJSONObject(i).getString("question")
                var options = jsonArray.getJSONObject(i).getJSONArray("options")
                var answer = jsonArray.getJSONObject(i).getInt("answer")
                val optionsList = (0 until options.length()).map { index ->
                    options.getString(index)
                }
                val questionObject = Question(question, optionsList, answer)
                questions.add(questionObject)


            }
            return questions
        }

        val allQuestions = loadQuestions()
        val selectedQuestions = allQuestions.shuffled().take(3)


        fun showquestion(currque: Int) {
            if (currque < 3) {
                findViewById<RadioGroup>(R.id.optionsGroup).clearCheck()
                var question1 = selectedQuestions.get(currque)
                Queno.text = " ${currque + 1} question of 3"
                tvquestion = findViewById<TextView>(R.id.question)
                tvquestion.text = question1.question
                var radio1 = findViewById<RadioButton>(R.id.option1)
                var radio2 = findViewById<RadioButton>(R.id.option2)
                var radio3 = findViewById<RadioButton>(R.id.option3)

                radio1.text = question1.options.get(0)
                radio2.text = question1.options.get(1)
                radio3.text = question1.options.get(2)
            } else {
                if (lockedPackage != null) {
                    sharedPref.edit()
                        .putString("TEMP_UNLOCKED_PACKAGE", lockedPackage)
                        .apply()
                }

                val launchIntent = packageManager
                    .getLaunchIntentForPackage(lockedPackage)

                if (launchIntent != null) {
                    startActivity(launchIntent)
                }
                finish()
            }
        }

        showquestion(0)

        findViewById<Button>(R.id.submitButton).setOnClickListener {

            var optionsGroup = findViewById<RadioGroup>(R.id.optionsGroup)

            val selectedId = optionsGroup.checkedRadioButtonId
            val selectedAnswer = when (selectedId) {
                R.id.option1 -> 0
                R.id.option2 -> 1
                R.id.option3 -> 2
                else -> -1
            }
            var question1 = selectedQuestions.get(currque)

            if (selectedAnswer == -1) {
                var feedback = findViewById<TextView>(R.id.feedback)
                feedback.text = "Must Select One Radio button"
            } else if (selectedAnswer == question1.answer) {
                result += 1
                currque += 1
                showquestion(currque)
            } else {
                var feedback = findViewById<TextView>(R.id.feedback)
                feedback.text = "Answer Is Wrong Try Again !!!"
            }


        }

    }
}