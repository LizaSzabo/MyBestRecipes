package hu.bme.aut.android.recipes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import hu.bme.aut.android.recipes.databinding.FragmentRegisterBinding

class RegisterFragment: BaseActivity() {
    private lateinit var fragmentBinding: FragmentRegisterBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        fragmentBinding = FragmentRegisterBinding.inflate(inflater, container, false)


        fragmentBinding.btnRegister.setOnClickListener{ navigateToLogin()}

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

        showProgressDialog()

        firebaseAuth
                .createUserWithEmailAndPassword(fragmentBinding.editTextTextEmailAddress.text.toString(), fragmentBinding.editTextTextPassword.text.toString())
                .addOnSuccessListener { result ->
                    hideProgressDialog()

                    val firebaseUser = result.user
                    val profileChangeRequest = UserProfileChangeRequest.Builder()
                            .setDisplayName(firebaseUser?.email?.substringBefore('@'))
                            .build()
                    firebaseUser?.updateProfile(profileChangeRequest)

                    toast("Registration successful")
                }
                .addOnFailureListener { exception ->
                    hideProgressDialog()

                    toast(exception.message)
                }
    }
}