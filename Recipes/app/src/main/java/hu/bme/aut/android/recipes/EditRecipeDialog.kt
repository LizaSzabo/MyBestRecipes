package hu.bme.aut.android.recipes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import hu.bme.aut.android.recipes.Model.Recipe
import hu.bme.aut.android.recipes.databinding.DialogEditFragmentBinding

class EditRecipeDialog(val pos: Int, val actualRecipeData: Recipe): DialogFragment() {
    private lateinit var binding: DialogEditFragmentBinding
     lateinit var listener: EditRecipeListener

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DialogEditFragmentBinding.inflate(inflater, container, false)

        binding.editTextRecipeTitle.setHint(actualRecipeData.title)
        binding.editTextRecipeCategory.setHint(actualRecipeData.category)
        binding.editTextRecipeTitle.setText(actualRecipeData.title)
        binding.editTextRecipeCategory.setText(actualRecipeData.category)

        binding.btnCancel.setOnClickListener{
            dismiss()
        }

        binding.btnSave.setOnClickListener{
            val recipe = Recipe(binding.editTextRecipeTitle.text.toString(), binding.editTextRecipeCategory.text.toString(), false, "aaa")
            listener?.onRecipeEdited(recipe, pos)
            dismiss()
        }
        return binding.root
    }

    interface EditRecipeListener{
        fun onRecipeEdited(recipe: Recipe, pos: Int)
    }
}