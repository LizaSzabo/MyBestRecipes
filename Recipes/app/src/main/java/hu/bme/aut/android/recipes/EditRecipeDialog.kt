package hu.bme.aut.android.recipes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import hu.bme.aut.android.recipes.Model.Recipe
import hu.bme.aut.android.recipes.databinding.DialogEditFragmentBinding

class EditRecipeDialog(private val pos: Int, private val actualRecipeData: Recipe): DialogFragment(), AdapterView.OnItemSelectedListener  {
    private lateinit var binding: DialogEditFragmentBinding
     lateinit var listener: EditRecipeListener

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DialogEditFragmentBinding.inflate(inflater, container, false)

        binding.editTextRecipeTitle.hint = actualRecipeData.title
        binding.editTextRecipeCategory.hint = actualRecipeData.category
        binding.editTextRecipeTitle.setText(actualRecipeData.title)
        binding.editTextRecipeCategory.setText(actualRecipeData.category)

        binding.btnCancel.setOnClickListener{
            dismiss()
        }

        binding.btnSave.setOnClickListener{
            val recipe = Recipe(actualRecipeData.id, binding.editTextRecipeTitle.text.toString(), binding.editTextRecipeCategory.text.toString(), actualRecipeData.favourite, actualRecipeData.content, actualRecipeData.date)
            updateRecipe(recipe)
            listener.onRecipeEdited(recipe, pos)

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

    interface EditRecipeListener{
        fun onRecipeEdited(recipe: Recipe, pos: Int)
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when(position) {
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
            16 -> setSelectedItem("Entertaining")
            else -> setSelectedItem(binding.editTextRecipeCategory.text.toString())
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

     private fun setSelectedItem(categorySelected: String){
            binding.editTextRecipeCategory.setText(categorySelected)
        }

    private fun updateRecipe(recipe: Recipe) {

        val db = Firebase.firestore

        db.collection("recipes").document(actualRecipeData.id.toString()).update( "title", recipe.title)
        db.collection("recipes").document(actualRecipeData.id.toString()).update( "category", recipe.category)
    }
}