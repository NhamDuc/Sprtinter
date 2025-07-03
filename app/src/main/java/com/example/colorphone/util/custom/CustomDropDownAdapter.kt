package com.example.colorphone.util.custom

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.colorphone.R

class CustomDropDownAdapter(
    context: Context,
    private val layoutResId: Int,
    private val items: List<String>,
    private val autoCompleteTextView: AutoCompleteTextView
): ArrayAdapter<String>(context, layoutResId, items) {

    private val selectedItemColor: Int = ContextCompat.getColor(context, R.color.csk_500)
    private val unselectedItemcolor: Int = ContextCompat.getColor(context, R.color.white)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(layoutResId, parent, false)
        val textView = view.findViewById<TextView>(R.id.randomItemTextView)

        val item = getItem(position)
        textView.text = item

        val currentTextInInput = autoCompleteTextView.text.toString()

        if (item == currentTextInInput) {
            view.setBackgroundColor(selectedItemColor)
        } else {
            view.setBackgroundColor(unselectedItemcolor)
        }

        return view

    }
}