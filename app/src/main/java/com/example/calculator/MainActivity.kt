package com.example.calculator

import android.icu.text.DecimalFormat
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.calculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var mainBinding: ActivityMainBinding
    var number: String? = null

    var firstNumber: Double = 0.0
    var lastNumber: Double = 0.0

    var status: String? = null
    var operator: Boolean = false

    var myFormatter = DecimalFormat("######.######")

    var history: String = ""
    var currentResult: String = ""

    var dotControl: Boolean = true
    var buttonEqualControl: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        val view = mainBinding.root
        enableEdgeToEdge()
        setContentView(view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        mainBinding.textViewResult.text = "0"

        mainBinding.btnZero.setOnClickListener{
            onNumberClicked("0")
        }
        mainBinding.btnOne.setOnClickListener{
            onNumberClicked("1")
        }
        mainBinding.btnTwo.setOnClickListener{
            onNumberClicked("2")
        }
        mainBinding.btnThree.setOnClickListener{
            onNumberClicked("3")
        }
        mainBinding.btnFour.setOnClickListener{
            onNumberClicked("4")
        }
        mainBinding.btnFive.setOnClickListener{
            onNumberClicked("5")
        }
        mainBinding.btnSix.setOnClickListener{
            onNumberClicked("6")
        }
        mainBinding.btnSeven.setOnClickListener{
            onNumberClicked("7")
        }
        mainBinding.btnEight.setOnClickListener{
            onNumberClicked("8")
        }
        mainBinding.btnNine.setOnClickListener{
            onNumberClicked("9")
        }

        mainBinding.btnAC.setOnClickListener{

            onButtonACClicked()

        }
        mainBinding.btnDel.setOnClickListener{

            number?.let{

                if(it.length == 1){
                    onButtonACClicked()
                }else{
                    number = it.substring(0,it.length-1)
                    mainBinding.textViewResult.text = number
                    dotControl = !number!!.contains(".")
                }
            }

        }
        mainBinding.btnDivide.setOnClickListener{

            history = mainBinding.textViewHistory.text.toString()
            currentResult = mainBinding.textViewResult.text.toString()
            mainBinding.textViewHistory.text = history.plus(currentResult).plus("/")

            if (operator){
                when(status){
                    "multiplication" -> multiply()
                    "division" -> divide()
                    "subtraction" -> minus()
                    "addition" -> plus()

                    else -> firstNumber = mainBinding.textViewResult.text.toString().toDouble()
                }
            }
            status = "division"
            operator = false
            number = null
            dotControl = true

        }
        mainBinding.btnMulti.setOnClickListener{

            history = mainBinding.textViewHistory.text.toString()
            currentResult = mainBinding.textViewResult.text.toString()
            mainBinding.textViewHistory.text = history.plus(currentResult).plus("*")

            if (operator){
                when(status){
                    "multiplication" -> multiply()
                    "division" -> divide()
                    "subtraction" -> minus()
                    "addition" -> plus()

                    else -> firstNumber = mainBinding.textViewResult.text.toString().toDouble()
                }
            }
            status = "multiplication"
            operator = false
            number = null
            dotControl = true

        }
        mainBinding.btnMinus.setOnClickListener{

            history = mainBinding.textViewHistory.text.toString()
            currentResult = mainBinding.textViewResult.text.toString()
            mainBinding.textViewHistory.text = history.plus(currentResult).plus("-")

            if (operator){
                when(status){
                    "multiplication" -> multiply()
                    "division" -> divide()
                    "subtraction" -> minus()
                    "addition" -> plus()

                    else -> firstNumber = mainBinding.textViewResult.text.toString().toDouble()
                }
            }
            status = "subtraction"
            operator = false
            number = null
            dotControl = true

        }
        mainBinding.btnPlus.setOnClickListener{

            history = mainBinding.textViewHistory.text.toString()
            currentResult = mainBinding.textViewResult.text.toString()
            mainBinding.textViewHistory.text = history.plus(currentResult).plus("+")

            if (operator){
               when(status){
                   "multiplication" -> multiply()
                   "division" -> divide()
                   "subtraction" -> minus()
                   "addition" -> plus()

                   else -> firstNumber = mainBinding.textViewResult.text.toString().toDouble()
               }
            }
            status = "addition"
            operator = false
            number = null
            dotControl = true
        }
        mainBinding.btnEquals.setOnClickListener{

            history = mainBinding.textViewHistory.text.toString()
            currentResult = mainBinding.textViewResult.text.toString()

            if (operator){
                when(status){
                    "multiplication" -> multiply()
                    "division" -> divide()
                    "subtraction" -> minus()
                    "addition" -> plus()

                    else -> firstNumber = mainBinding.textViewResult.text.toString().toDouble()
                }

                mainBinding.textViewHistory.text = history.plus(currentResult).plus("=").plus(mainBinding.textViewResult.text.toString())
            }
            operator = false
            dotControl = true
            buttonEqualControl = true

        }
        mainBinding.btnDot.setOnClickListener{

            if(dotControl){
                number = if (number == null){
                    "0."
                }else if (buttonEqualControl){
                    if(mainBinding.textViewResult.text.toString().contains(".")){
                        mainBinding.textViewResult.text.toString()
                    }else{
                        mainBinding.textViewResult.text.toString().plus(".")
                    }
                }else{
                    "$number."
                }
                mainBinding.textViewResult.text = number
            }

            dotControl = false
        }

    }

    private fun onButtonACClicked() {
        number = null
        status = null
        mainBinding.textViewResult.text = "0"
        mainBinding.textViewHistory.text = ""
        firstNumber = 0.0
        lastNumber = 0.0
        dotControl = true
        buttonEqualControl = false
    }

    fun onNumberClicked(clickedNumber: String){
        if(number == null){
            number = clickedNumber
        }else if (buttonEqualControl){

            number = if(dotControl){
                clickedNumber
            }else{
                mainBinding.textViewResult.text.toString().plus(clickedNumber)
            }

            firstNumber = number!!.toDouble()
            lastNumber = 0.0
            status = null
            mainBinding.textViewHistory.text = ""

        }else{
            number += clickedNumber
        }

        mainBinding.textViewResult.text = number

        operator = true
        buttonEqualControl = false
    }

    fun plus(){
        lastNumber = mainBinding.textViewResult.text.toString().toDouble()
        firstNumber += lastNumber
        mainBinding.textViewResult.text = myFormatter.format(firstNumber)
    }
    fun minus(){
        lastNumber = mainBinding.textViewResult.text.toString().toDouble()
        firstNumber -= lastNumber
        mainBinding.textViewResult.text = myFormatter.format(firstNumber)
    }
    fun multiply(){
        lastNumber = mainBinding.textViewResult.text.toString().toDouble()
        firstNumber *= lastNumber
        mainBinding.textViewResult.text = myFormatter.format(firstNumber)
    }
   fun divide(){
        lastNumber = mainBinding.textViewResult.text.toString().toDouble()

       if (lastNumber == 0.0){
           Toast.makeText(applicationContext,"The divisor cannot be zero",Toast.LENGTH_LONG).show()

       }else{
           firstNumber /= lastNumber
           mainBinding.textViewResult.text = myFormatter.format(firstNumber)
       }
    }

}