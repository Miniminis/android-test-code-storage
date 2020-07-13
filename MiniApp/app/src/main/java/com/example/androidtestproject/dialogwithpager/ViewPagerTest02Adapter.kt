package com.example.androidtestproject.dialogwithpager

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

import com.example.androidtestproject.databinding.ItemViewPagerTest02Binding

class ViewPagerTest02Adapter(
    private val context: Context,
    private val titleList: ArrayList<Int>,
    private val firstList: ArrayList<Int>,
    private val secondList: ArrayList<Int>,
    private val thirdList: ArrayList<Int>
) : RecyclerView.Adapter<ViewPagerTest02Adapter.ViewPagerTest02ViewHolder>() {

    inner class ViewPagerTest02ViewHolder(private val binding: ItemViewPagerTest02Binding) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(title: Int, first: Int, second:Int, third:Int) {
//            textView.text = src.toString()
            binding.itemTitle.setImageResource(title)
            binding.itemImgFirst.setImageResource(first)
            binding.itemImgSecond.setImageResource(second)
            binding.itemImgThird.setImageResource(third)

            binding.imgClose.setOnClickListener {
                Toast.makeText(context, "close", Toast.LENGTH_LONG).show()
//                (context as MainActivity).dialogFragment.dismiss()
            }

            binding.chkNeverAgain.setOnCheckedChangeListener { buttonView, isChecked ->
                var pref = context.getSharedPreferences("SETTING", 0)
                var prefEdit = pref.edit()
                prefEdit.putBoolean("SETTING_CHK", isChecked)
                prefEdit.commit()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerTest02ViewHolder {
        return ViewPagerTest02ViewHolder(ItemViewPagerTest02Binding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun getItemCount(): Int = titleList.size

    override fun onBindViewHolder(holder: ViewPagerTest02ViewHolder, position: Int) {
        holder.onBind(titleList[position],
            firstList[position],
            secondList[position],
            thirdList[position]
        )
    }
}