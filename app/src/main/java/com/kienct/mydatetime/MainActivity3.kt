package com.kienct.mydatetime

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main3.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity3 : AppCompatActivity() {
    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)
        //given this list
        var originalList = arrayListOf("08:00:00", "05:00:00", "18:00:00", "12:00:00", "03:00:00")
        btOriginalList.text = originalList.toString()
        //convert string to date for calculation
        val myList = arrayListOf<Date?>()
        //if date is after 08:00 then add into this list
        val filteredList = arrayListOf<String>()

        val inputFormat = SimpleDateFormat("HH:mm:ss")//pattern of the strings, to parse
        for (str in originalList) {
            myList.add(inputFormat.parse(str))//add items into myList as Date
        }
        //bubble sort
        val size = myList.size
        for (i in 0 until size - 1) {
            for (j in 0 until size - i - 1)
                if (myList[j]?.before(myList[j + 1])!!) {
                    val temp = myList[j]
                    myList[j] = myList[j + 1]
                    myList[j + 1] = temp
                }
        }
        //refresh originalList
        originalList = arrayListOf()
        //both add into originalList to display and filter items for filteredList
        for (date in myList) {
            if (date?.after(inputFormat.parse("08:00:00"))!!)
                filteredList.add(inputFormat.format(date))
            originalList.add(inputFormat.format(date))
        }
        //display
        btMyList.text = originalList.toString()
        btFilteredList.text = filteredList.toString()
    }
}