package hu.bme.aut.android.recipes

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.DatePicker
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import hu.bme.aut.android.recipes.Model.Recipe
import java.util.*

class DatePickerDialogFragment(val item: Recipe?) : DialogFragment(), DatePickerDialog.OnDateSetListener{
   lateinit var onDateSelectedListener: OnDateSelectedListener



    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        return DatePickerDialog(requireContext(), this, year, month, day)
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        onDateSelectedListener.onDateSelected(year, month, dayOfMonth, item)
    }

    interface OnDateSelectedListener {
        fun onDateSelected(year: Int, month: Int, day: Int, item:Recipe?)
    }
}