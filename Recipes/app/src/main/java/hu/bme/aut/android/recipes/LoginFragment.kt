package hu.bme.aut.android.recipes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import hu.bme.aut.android.recipes.databinding.FragmentLoginBinding

class LoginFragment: Fragment() {
    private lateinit var fragmentBinding: FragmentLoginBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        fragmentBinding = FragmentLoginBinding.inflate(inflater, container, false)

        fragmentBinding.btnRegister.setOnClickListener{ navigateToRegister()}
        fragmentBinding.btnLogin.setOnClickListener{ navigateToRecipes()}

        return fragmentBinding.root
    }

    private fun navigateToRegister() {
        val action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
        findNavController().navigate(action)
    }

    private fun navigateToRecipes() {
        val action = LoginFragmentDirections.actionLoginFragmentToRecipesFragment()
        findNavController().navigate(action)
    }
}