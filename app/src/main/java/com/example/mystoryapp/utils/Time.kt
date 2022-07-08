package com.example.mystoryapp.utils

import androidx.appcompat.app.AppCompatActivity
import net.time4j.PrettyTime
import net.time4j.android.ApplicationStarter
import net.time4j.format.expert.Iso8601Format
import java.util.*

fun AppCompatActivity.initializeTime4A() {
    ApplicationStarter.initialize(application, true)
}

fun convertToTimeAgo(date: String): String {
    val moment = Iso8601Format.EXTENDED_DATE_TIME_OFFSET.parse(date)
    return PrettyTime.of(Locale("id")).printRelativeInStdTimezone(moment)
}