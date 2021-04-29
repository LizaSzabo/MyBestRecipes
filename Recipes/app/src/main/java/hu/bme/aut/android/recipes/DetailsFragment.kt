package hu.bme.aut.android.recipes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import hu.bme.aut.android.recipes.databinding.FragmentDetailsBinding

class DetailsFragment : Fragment(){
    private lateinit var fragmentBinding: FragmentDetailsBinding
    val args: DetailsFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        fragmentBinding =  FragmentDetailsBinding.inflate(inflater, container, false)

        fragmentBinding.tvRecipeTitleDetail.text = args.recipeTitle
        fragmentBinding.tvContent.setText(args.recipeContent)

        return fragmentBinding.root
    }
}