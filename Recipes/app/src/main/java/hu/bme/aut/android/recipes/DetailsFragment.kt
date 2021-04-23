package hu.bme.aut.android.recipes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import hu.bme.aut.android.recipes.databinding.FragmentDetailsBinding
import hu.bme.aut.android.recipes.databinding.FragmentRecipesBinding

class DetailsFragment : Fragment(){
    private lateinit var fragmentBinding: FragmentDetailsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        fragmentBinding =  FragmentDetailsBinding.inflate(inflater, container, false)

        return fragmentBinding.root
    }
}