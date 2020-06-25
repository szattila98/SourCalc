package ren.practice.sourcalc

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    var leavenMode = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        flourET.addTextChangedListener(object : TextWatcherExtended() {
            override fun afterTextChanged(s: Editable, backSpace: Boolean) {
                calc(leavenMode)
            }
        })
        flourET.transformationMethod = HideReturnsTransformationMethod.getInstance()
        toggle_leaven_mode(View.GONE)
        leaven_switch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                leavenMode = isChecked
                toggle_leaven_mode(View.VISIBLE)
                leaven_switch.text = getString(R.string.leaven_switch_off)
                calc(isChecked)
            } else {
                leavenMode = isChecked
                toggle_leaven_mode(View.GONE)
                leaven_switch.text = getString(R.string.leaven_switch_on)
                calc(isChecked)
            }
        }
    }

    private fun calc(leavenMode: Boolean) {
        // TODO changeable percentages
        if (!flourET.text.toString().isBlank()) {
            val flour = flourET.text.toString().toInt()
            val water = (flour * 0.7).toInt()
            val starter = (flour * 0.25).toInt()
            val salt = (flour * 0.02).toInt()
            if (leavenMode) {
                flourTV.text = (flour - starter).toString()
                waterTV.text = ((flour * 0.7).toInt() - starter).toString()
                starterTV.text = "0"
                saltTV.text = (flour * 0.02).toInt().toString()
                flour_leaven_TV.text = starter.toString()
                starter_leaven_TV.text = starter.toString()
                water_leaven_TV.text = starter.toString()
            } else {
                flourTV.text = flour.toString()
                waterTV.text = water.toString()
                starterTV.text = starter.toString()
                saltTV.text = salt.toString()
            }
        } else {
            flourTV.text = getString(R.string.invalid_str)
            waterTV.text = getString(R.string.invalid_str)
            starterTV.text = getString(R.string.invalid_str)
            saltTV.text = getString(R.string.invalid_str)
            flour_leaven_TV.text = getString(R.string.invalid_str)
            starter_leaven_TV.text = getString(R.string.invalid_str)
            water_leaven_TV.text = getString(R.string.invalid_str)
        }
    }

    private fun toggle_leaven_mode(mode: Int) {
        leaven_label.visibility = mode
        water_label_leaven.visibility = mode
        starter_label_leaven.visibility = mode
        salt_label_leaven.visibility = mode
        flour_leaven_TV.visibility = mode
        starter_leaven_TV.visibility = mode
        water_leaven_TV.visibility = mode
    }
}

abstract class TextWatcherExtended : TextWatcher {

    private var lastLength: Int = 0

    abstract fun afterTextChanged(s: Editable, backSpace: Boolean)

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
        lastLength = s.length
    }

    override fun afterTextChanged(s: Editable) {
        afterTextChanged(s, lastLength > s.length)
    }

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
    }
}