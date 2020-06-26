package ren.practice.sourcalc

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

private const val WATER_PERC = 0.7
private const val STARTER_PERC = 0.25
private const val SALT_PERC = 0.02

class MainActivity : AppCompatActivity() {

    private var prefermentMode = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        flourET.addTextChangedListener(object : TextWatcherExtended() {
            override fun afterTextChanged(s: Editable, backSpace: Boolean) {
                calc(prefermentMode)
            }
        })
        flourET.transformationMethod = HideReturnsTransformationMethod.getInstance()
        toggle_leaven_mode(View.GONE)
        preferment_switch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                prefermentMode = isChecked
                toggle_leaven_mode(View.VISIBLE)
                preferment_switch.text = getString(R.string.preferment_switch_off)
                calc(isChecked)
            } else {
                prefermentMode = isChecked
                toggle_leaven_mode(View.GONE)
                preferment_switch.text = getString(R.string.preferment_switch_on)
                calc(isChecked)
            }
        }
    }

    private fun calc(leavenMode: Boolean) {
        if (!flourET.text.toString().isBlank()) {
            val flour = flourET.text.toString().toInt()
            val water = (flour * WATER_PERC).toInt()
            val starter = (flour * STARTER_PERC).toInt()
            val salt = (flour * SALT_PERC).toInt()
            if (leavenMode) {
                flourTV.text = (flour - starter).toString()
                waterTV.text = ((flour * 0.7).toInt() - starter).toString()
                starterTV.text = "0"
                saltTV.text = (flour * 0.02).toInt().toString()
                flour_preferment_TV.text = starter.toString()
                starter_preferment_TV.text = starter.toString()
                water_preferment_TV.text = starter.toString()
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
            flour_preferment_TV.text = getString(R.string.invalid_str)
            starter_preferment_TV.text = getString(R.string.invalid_str)
            water_preferment_TV.text = getString(R.string.invalid_str)
        }
    }

    private fun toggle_leaven_mode(mode: Int) {
        preferment_label.visibility = mode
        water_label_preferment.visibility = mode
        starter_label_preferment.visibility = mode
        salt_label_preferment.visibility = mode
        flour_preferment_TV.visibility = mode
        starter_preferment_TV.visibility = mode
        water_preferment_TV.visibility = mode
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