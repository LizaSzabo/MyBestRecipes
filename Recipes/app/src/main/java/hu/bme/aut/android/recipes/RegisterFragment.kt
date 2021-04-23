package hu.bme.aut.android.recipes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import hu.bme.aut.android.recipes.databinding.FragmentRegisterBinding

class RegisterFragment: Fragment() {
    private lateinit var fragmentBinding: FragmentRegisterBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        fragmentBinding = FragmentRegisterBinding.inflate(inflater, container, false)


        fragmentBinding.btnRegister.setOnClickListener{ navigateToLogin()}
        return fragmentBinding.root
    }

    private fun navigateToLogin() {
        val action = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
        findNavController().navigate(action)
    }
}