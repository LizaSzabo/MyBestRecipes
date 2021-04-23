package hu.bme.aut.android.recipes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import hu.bme.aut.android.recipes.Model.Recipe
import hu.bme.aut.android.recipes.databinding.DialogAddNewRecipeBinding
import hu.bme.aut.android.recipes.databinding.DialogEditFragmentBinding

class AddNewRecipeDialog : DialogFragment() {
    private lateinit var binding: DialogAddNewRecipeBinding
    lateinit var addRecipeListener: AddRecipeListener

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DialogAddNewRecipeBinding.inflate(inflater, container, false)

        binding.btnCancel.setOnClickListener{
            dismiss()
        }

        binding.btnSave.setOnClickListener{
            val recipe = Recipe(binding.editTextRecipeTitle.text.toString(), binding.editTextRecipeCategory.text.toString(), false, "aaa")
            addRecipeListener?.onNewRecipe(recipe)
            dismiss()
        }
        return binding.root
    }

    interface AddRecipeListener{
        fun onNewRecipe(recipe: Recipe)
    }
}