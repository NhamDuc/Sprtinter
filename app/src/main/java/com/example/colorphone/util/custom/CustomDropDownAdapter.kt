package com.example.colorphone.util.custom

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.colorphone.R

class CustomDropDownAdapter(
    context: Context,
    private val layoutResId: Int,
    private val items: Array<String>,
    private val autoCompleteTextView: AutoCompleteTextView
): ArrayAdapter<String>(context, layoutResId, items) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(layoutResId, parent, false)
        val textView = view.findViewById<TextView>(R.id.randomItemTextView)

        val item = getItem(position)
        textView.text = item

        val currentTextInInput = autoCompleteTextView.text.toString()

        if (item == currentTextInInput) {
            if (position == 0) {
                view.setBackgroundResource(R.drawable.dropdown_item_top_selected)
            } else if (position == items.lastIndex) {
                view.setBackgroundResource(R.drawable.dropdown_item_bottom_selected)
            } else {
                view.setBackgroundResource(R.drawable.dropdown_item_selected)
            }
        } else {
            view.setBackgroundResource(R.drawable.dropdown_item_unselected)
        }

        return view
    }

    override fun getCount(): Int {
        val count = super.getCount()
        Log.d("DropDownDebug", "CustomDropDownAdapter: getCount() returning = ${count}") // <--- Add this
        return count
    }
}