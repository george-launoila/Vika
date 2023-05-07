package com.example.george

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.*


class LatestDataView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    var maxRows = 5
    // konstruktori, säädetään orientaatio
    init {
        this.orientation = VERTICAL

        // idea: luodaan ohjelman muistiin yksi textview, ja otetaan sen koko talteen
        // asetetaan LatestDataViewin alkukorkeudeuksi peruskorkeus + viiden TextViewin yhteenlaskettu korkeus
        var someTextView : TextView = TextView(context)

        // pyydetään Androidia mittaamaan tämän komponentin koko,
        // jos se nyt laitettaisiin tämän puhelimen näytölle esille
        someTextView.measure(0,0)

        // lasketaan viiden textviewin tarvitsema pystytila
        var additionalHeight = someTextView.measuredHeight * maxRows

        // mitataan myös itse LinearLayoutin alkukoko
        this.measure(0, 0)
        var startHeight = this.measuredHeight

        // asetetaan laskennallinen korkeus, johon mahtuu tasan viisi textviewiä
        this.minimumHeight = startHeight + additionalHeight
    }

    // this function can be called where it's needed, init() or an Activity.
    fun addData(message : String)
    {
        // poistetaan vanhin viesti että näkyy vain max 5 riviä
        while(this.childCount >= maxRows) {
            this.removeViewAt(0)
        }

        // luodaan uusi textview lennosta ja laitetaan linearlayoutiin
        var newTextView : TextView = TextView(context) as TextView
        newTextView.setText(message)
        newTextView.setBackgroundColor(Color.BLACK)
        newTextView.setTextColor(Color.YELLOW)
        this.addView(newTextView)

        // animoidaan fade in -animaatio
        val animation = AnimationUtils.loadAnimation(context, R.anim.customfade)
        newTextView.startAnimation(animation)

    }

    // haetaan aikaleima ja rakennetaan viesti LatestDataViewiin
    //val timeStamp: String = SimpleDateFormat("HH:mm:ss").format(Date())
    //var message = "${timeStamp} - Temperature: ${temperature}℃, humidity: ${item.d.get3().v.toInt()}%"

}