package hu.bme.aut.android.recipes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import hu.bme.aut.android.recipes.databinding.FragmentLoginBinding

class LoginFragment: BaseActivity() {
    private lateinit var fragmentBinding: FragmentLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        fragmentBinding = FragmentLoginBinding.inflate(inflater, container, false)

        fragmentBinding.btnRegister.setOnClickListener{ navigateToRegister()}
        fragmentBinding.btnLogin.setOnClickListener{ loginClick() }

        firebaseAuth = FirebaseAuth.getInstance()
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

    private fun loginClick() {
        if (!validateForm()) {
            return
        }

        firebaseAuth
            .signInWithEmailAndPassword(fragmentBinding.editTextTextEmailAddress.text.toString(), fragmentBinding.editTextTextPassword.text.toString())
            .addOnSuccessListener {
                navigateToRecipes()
            }
            .addOnFailureListener { exception ->
                toast(exception.localizedMessage)
            }
    }

    private fun validateForm(): Boolean {
        if (fragmentBinding.editTextTextEmailAddress.text.toString().isEmpty()) {
            fragmentBinding.editTextTextEmailAddress.error = "Required"
            return false
        }
        if (fragmentBinding.editTextTextPassword.text.toString().isEmpty()) {
            fragmentBinding.editTextTextPassword.error = "Required"
            return false
        }
        return true
    }
}