package hu.bme.aut.android.recipes

import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import hu.bme.aut.android.recipes.databinding.DialogEditFragmentBinding

class EditRecipeDialog(val pos: Int): DialogFragment() {
    private lateinit var binding: DialogEditFragmentBinding
    //lateinit var listener: EditRecipeListener

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DialogEditFragmentBinding.inflate(inflater, container, false)


        return binding.root
    }
}