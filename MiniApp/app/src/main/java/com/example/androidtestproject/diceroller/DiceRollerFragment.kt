package com.example.androidtestproject.diceroller

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast

import com.example.androidtestproject.R
import kotlinx.android.synthetic.main.fragment_dice_roller.*
import kotlin.random.Random

class DiceRollerFragment : Fragment() {

    private lateinit var diceImage: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dice_roller, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val btn_roll = activity?.findViewById<Button>(R.id.btn_roll)
        btn_roll?.text = "Let's Roll!"
        btn_roll?.setOnClickListener {
//            Toast.makeText(activity, "button Clicked!!!!!!", Toast.LENGTH_SHORT).show()
            rollDice()
        }
        diceImage = activity?.findViewById(R.id.img_dice)!!
    }

    private fun rollDice() {
//        txt_roll_result.text = "Dice Rolled!!!!!!!!!!!!!!!!!!!!!!!!!!!!!YEAHY!!!!"
//        txt_roll_result.text = (Random.nextInt(6) + 1).toString()

        val randomNum = Random.nextInt(6) + 1
        var diceImg = when(randomNum) {
            1 -> R.drawable.dice_1
            2 -> R.drawable.dice_2
            3 -> R.drawable.dice_3
            4 -> R.drawable.dice_4
            5 -> R.drawable.dice_5
            6 -> R.drawable.dice_6
            else -> R.drawable.dice_1
        }
        diceImage.setImageResource(diceImg)
    }

}
