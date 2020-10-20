package com.kienct.mydatetime

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {
    // kudos to https://gist.github.com/maiconhellmann/796debb4007139d50e39f139be08811c
    private fun Date.add(field: Int, amount: Int): Date = Calendar.getInstance().let { calendar ->
        calendar.time = this@add
        calendar.add(field, amount)
        return@let calendar.time
    }

    private val c: Calendar = Calendar.getInstance()
    private val dummyCalendar = Calendar.getInstance()

    //when date is set
    private var dateSetListener =
        DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            btDate.text =
                String.format(
                    "Ngày %02d tháng %02d năm %d",
                    dayOfMonth,
                    monthOfYear + 1,//months start with 0 so +1
                    year
                )
            dummyCalendar.set(year, monthOfYear, dayOfMonth, 0, 0, 0)// to compare
        }

    //when time is set
    private var timeSetListener = TimePickerDialog.OnTimeSetListener { _, hour, minute ->
        btTime.text =
            String.format("%02d giờ %02d phút", hour, minute)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        dummyCalendar.set(Calendar.DATE, c.get(Calendar.DATE) + 1) // assume that at first dummyCalendar is after c
        btDate.setOnClickListener {
            val datePickerDialog = DatePickerDialog(
                this, 0
            )
            datePickerDialog.setOnDateSetListener(dateSetListener)
            //min date is today, range is 2 weeks/14 days
            datePickerDialog.datePicker.minDate = c.timeInMillis
            datePickerDialog.datePicker.maxDate = c.time.add(Calendar.DATE, 13).time
            datePickerDialog.show()
        }
        btTime.setOnClickListener {
            //if datePicker picked today, timePicker focused on 8, else on 10
            val hour = if (dummyCalendar.after(Calendar.getInstance())) 10
            else 8
            val timePickerDialog = TimePickerDialog(
                this, timeSetListener, hour, 0, true
            )
            timePickerDialog.show()
        }
        //next screen
        btNext.setOnClickListener {
            val intent = Intent(this, MainActivity2::class.java)
            startActivity(intent)
        }
    }
}