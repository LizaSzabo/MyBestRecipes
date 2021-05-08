package hu.bme.aut.android.recipes

import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

abstract class BaseActivity : Fragment() {

    private val firebaseUser: FirebaseUser?
        get() = FirebaseAuth.getInstance().currentUser

    protected val uid: String?
        get() = firebaseUser?.uid

    protected val userName: String?
        get() = firebaseUser?.displayName

    protected val userEmail: String?
        get() = firebaseUser?.email


    protected fun toast(message: String?) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

}