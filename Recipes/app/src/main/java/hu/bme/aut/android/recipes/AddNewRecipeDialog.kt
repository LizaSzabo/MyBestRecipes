package hu.bme.aut.android.recipes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.DialogFragment
import hu.bme.aut.android.recipes.Model.Recipe
import hu.bme.aut.android.recipes.databinding.DialogAddNewRecipeBinding
import hu.bme.aut.android.recipes.databinding.DialogEditFragmentBinding

class AddNewRecipeDialog : DialogFragment(), AdapterView.OnItemSelectedListener {
    private lateinit var binding: DialogAddNewRecipeBinding
    lateinit var addRecipeListener: AddRecipeListener

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DialogAddNewRecipeBinding.inflate(inflater, container, false)

        binding.btnCancel.setOnClickListener{
            dismiss()
        }

        binding.btnSave.setOnClickListener{
            val recipe = Recipe(binding.editTextRecipeTitle.text.toString(), binding.editTextRecipeCategory.text.toString(), false, "aaa", null)
            addRecipeListener?.onNewRecipe(recipe)
            dismiss()
        }

        val spinner: Spinner = binding.spinnerCategory
        context?.let {
            ArrayAdapter.createFromResource(
                    it,
                    R.array.categories,
                    android.R.layout.simple_spinner_item
            )
                    .also { adapter ->
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        spinner.adapter = adapter
                    }
        }
        spinner.onItemSelectedListener = this
        spinner.dropDownHorizontalOffset = -20
        return binding.root
    }

    interface AddRecipeListener{
        fun onNewRecipe(recipe: Recipe)
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
         when(position){
             0 -> setSelectedItem("Breakfast")
             1 -> setSelectedItem("Lunch")
             2 -> setSelectedItem("Beverage")
             3 -> setSelectedItem("Appetizer")
             4 -> setSelectedItem("Soup")
             5 -> setSelectedItem("Main dish: Beef")
             6 -> setSelectedItem("Main dish: Poultry")
             7 -> setSelectedItem("Main dish: Pork")
             8 -> setSelectedItem("Main dish: Seafood")
             9 -> setSelectedItem("Main dish: Vegetarian")
             10 -> setSelectedItem("Side dish: Vegetables")
             11 -> setSelectedItem("Side dish: Other")
             12 -> setSelectedItem("Dessert")
             13 -> setSelectedItem("Canning / Freezing")
             14 -> setSelectedItem("Bread")
             15 -> setSelectedItem("Holidays")
            else -> setSelectedItem("Entertaining")
        }

    }
    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

   private fun  setSelectedItem(categorySelected: String){
       binding.editTextRecipeCategory.setText(categorySelected)
   }
}