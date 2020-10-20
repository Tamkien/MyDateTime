package com.kienct.mydatetime

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main2.*
import java.text.SimpleDateFormat
import java.util.*

// given Friday, 29 May 2015 05:50:06, I had to covert to yyyy年MM月dd日 HH:mm pattern.
// After that, subtract 10 min. Finally, compare to "05/29/2015 15:50".

class MainActivity2 : AppCompatActivity() {
    // kudos to https://gist.github.com/maiconhellmann/796debb4007139d50e39f139be08811c
    private fun Date.add(field: Int, amount: Int): Date = Calendar.getInstance().let { calendar ->
        calendar.time = this@add
        calendar.add(field, amount)
        return@let calendar.time
    }

    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        val givenDate = "Friday, 29 May 2015 05:50:06"
        val comparingDate = "05/29/2015 15:50"
        val inputFormat = SimpleDateFormat("EEEE, dd MMM yyyy HH:mm:ss")// format of givenDate
        val outputFormat = SimpleDateFormat("yyyy年MM月dd日 HH:mm")//format to display
        val compareFormat = SimpleDateFormat("MM/dd/yyyy HH:mm")//format of comparingDate

        val date =
            inputFormat.parse(givenDate)//convert givenDate into "machine-understandable" Date.
        btFormattedDateTime.text =
            outputFormat.format(date)//better put this thing into try catch but i'm bored.

        val afterDate = date?.add(Calendar.MINUTE, -10)
        btAfterDateTime.text = outputFormat.format(afterDate)//into try catch too

        val compareDate = compareFormat.parse(comparingDate)
        btComparison.text = when {
            compareDate?.after(afterDate) == true -> "$compareDate is after $afterDate"
            compareDate?.before(afterDate) == true -> "$compareDate is before $afterDate"
            else -> "same time"
        }
        //next screen
        btNext.setOnClickListener {
            btNext.setOnClickListener {
                val intent = Intent(this, MainActivity3::class.java)
                startActivity(intent)
            }
        }
    }
}