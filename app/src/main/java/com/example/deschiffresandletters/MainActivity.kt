package com.example.deschiffresandletters


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout


class MainActivity : AppCompatActivity() {
    private val letterPicker by lazy { LetterPicker.buildFromResource(this, R.raw.letter_frequencies) }
    private val drawTextView by lazy { findViewById<TextView>(R.id.textView2) }
    //val resultText by lazy { findViewById<TextView>(R.id.textView) }
    private val errorLayout by lazy { findViewById<TextInputLayout>(R.id.textInput) }
    private val editTextInput by lazy {findViewById<TextInputEditText>(R.id.editTexts)}
    private var pickedWorkd: String = ""
    private var bestAnagrams = listOf<String>()
    private val button by lazy { findViewById<Button>(R.id.button2) }
    private val mDefaultInputType = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        "Click on the button to launch the game".also { drawTextView.text = it }
        pickedWorkd = letterPicker.pickLetters(9)
        drawTextView.text = pickedWorkd
        button.setText("Submit")

        attachingListeners()
    }


    fun attachingListeners() {
        editTextInput.afterTextChanged { word ->
            if (!(pickedWorkd.containsAnagram(word))) {
                setErrorCondition(button, errorLayout, "Enter a word contained in the anagram")
            } else {
                setNonErrorCondition(editTextInput, errorLayout, InputType.TYPE_CLASS_TEXT)
            }
        }

        button.setOnClickListener {


        }
    }


    /**
     * Common error condition for entry fields. Disables next entry field.
     */
    fun setErrorCondition(button: Button, error_layout: TextInputLayout?, errorMsg: String) {
        error_layout?.error = errorMsg
        button.isEnabled = false
    }

    /**
     * Common non error condition for entry fields. Enables the next entry field.
     */
    fun setNonErrorCondition(targetEdt: EditText?, error_layout: TextInputLayout?, inputType: Int) {
        error_layout?.error = null
        button.isEnabled = true
    }

    /**
     * Common function for enabling and setting input field according to type of targeted entry.
     */
    private fun EditText.setEnabled(inputTypeNew: Int) {
        isFocusable = true
        isFocusableInTouchMode = true
        when (inputTypeNew) {
            mDefaultInputType ->
                this.inputType = InputType.TYPE_CLASS_TEXT
            else ->
                this.inputType = inputTypeNew
        }
    }
}

