package ren.practice.sourcalc

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        flourET.addTextChangedListener(object : TextWatcherExtended() {
            override fun afterTextChanged(s: Editable, backSpace: Boolean) {
                calc()
            }
        })
        flourET.transformationMethod = HideReturnsTransformationMethod.getInstance()
        toggle_leaven_mode(View.GONE)
        leaven_switch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                toggle_leaven_mode(View.VISIBLE)
                leaven_switch.text = getString(R.string.leaven_switch_off)
                // alternate calc
            } else {
                toggle_leaven_mode(View.GONE)
                leaven_switch.text = getString(R.string.leaven_switch_on)
            }
        }
    }

    private fun calc() {
        if (!flourET.text.toString().isBlank()) {
            val flour = flourET.text.toString().toInt()
            waterTV.text = (flour * 0.7).toInt().toString()
            starterTV.text = (flour * 0.25).toInt().toString()
            saltTV.text = (flour * 0.02).toInt().toString()
        } else {
            waterTV.text = getString(R.string.invalid_str)
            starterTV.text = getString(R.string.invalid_str)
            saltTV.text = getString(R.string.invalid_str)
        }
    }

    private fun toggle_leaven_mode(mode: Int) {
        leaven_label.visibility = mode
        water_label_leaven.visibility = mode
        starter_label_leaven.visibility = mode
        salt_label_leaven.visibility = mode
        water_leaven_TV.visibility = mode
        starter_leaven_TV.visibility = mode
        salt_leaven_TV.visibility = mode
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