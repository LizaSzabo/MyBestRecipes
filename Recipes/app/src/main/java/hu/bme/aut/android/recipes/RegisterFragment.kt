package hu.bme.aut.android.recipes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import hu.bme.aut.android.recipes.databinding.FragmentRegisterBinding

class RegisterFragment: BaseActivity() {
    private lateinit var fragmentBinding: FragmentRegisterBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        fragmentBinding = FragmentRegisterBinding.inflate(inflater, container, false)

        firebaseAuth = FirebaseAuth.getInstance()
        fragmentBinding.btnRegister.setOnClickListener{
            registerClick()
        }
        return fragmentBinding.root
    }

    private fun navigateToLogin() {
        val action = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
        findNavController().navigate(action)
    }

    private fun registerClick() {

        if (!validateForm()) {
            return
        }

        firebaseAuth
                .createUserWithEmailAndPassword(fragmentBinding.editTextTextEmailAddress.text.toString(), fragmentBinding.editTextTextPassword.text.toString())
                .addOnSuccessListener { result ->

                    val firebaseUser = result.user
                    val profileChangeRequest = UserProfileChangeRequest.Builder()
                            .setDisplayName(firebaseUser?.email?.substringBefore('@'))
                            .build()
                    firebaseUser?.updateProfile(profileChangeRequest)

                    toast("Registration successful")
                    navigateToLogin()
                }
                .addOnFailureListener { exception ->
                    toast(exception.message)
                }
    }


    private fun validateForm(): Boolean {
        if (fragmentBinding.editTextTextEmailAddress.text.isEmpty()) {
            fragmentBinding.editTextTextEmailAddress.error = "Required"
            return false
        }
        if (fragmentBinding.editTextTextPassword.text.isEmpty()) {
            fragmentBinding.editTextTextPassword.error = "Required"
            return false
        }
        if (fragmentBinding.editTextTextPassword2.text.isEmpty()) {
            fragmentBinding.editTextTextPassword2.error = "Required"
            return false
        }
        if (fragmentBinding.editTextTextPassword.text.toString() != fragmentBinding.editTextTextPassword2.text.toString()) {
            fragmentBinding.editTextTextPassword.error = "Error password confirmation"
            return false
        }
        return true
    }
}