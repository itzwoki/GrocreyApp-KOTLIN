package ghanam.com.abdo

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.navArgs
import com.google.firebase.auth.FirebaseAuth
import ghanam.com.abdo.databinding.FragmentLoginBinding
import ghanam.com.abdo.singletons.VirtualDB


class LoginFragment : Fragment() {

    lateinit var binding: FragmentLoginBinding
    lateinit var firebaseAuth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //VirtualDB.toString()
        firebaseAuth= FirebaseAuth.getInstance()
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        binding.loginButton.setOnClickListener {
            validateLogin(it)

        }
        binding.singUpLink.setOnClickListener {
            findNavController(it).navigate(R.id.action_loginFragment_to_signUpFragment)
        }
        firebaseAuth= FirebaseAuth.getInstance()
        return binding.root
    }



    private fun validateLogin(it: View?) {
        binding.apply {
            val email = emailTextInput.text.toString()
            val password = passwordTextInput.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(context, "please complete all fields", Toast.LENGTH_LONG).show()
                return
            }
            if (email.isNotEmpty() && password.isNotEmpty()) {

                firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {task->
                    if (task.isSuccessful) {

                        Toast.makeText(context, "You are logged in", Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.action_loginFragment_to_homeFragment)


                    } else {
                        Toast.makeText(context, "Please provide the right Credentials", Toast.LENGTH_LONG).show()

                    }
                }
                //if (it != null) { Navigation.findNavController(it).navigate(R.id.action_loginFragment_to_homeFragment)
                }//else{
                //Toast.makeText(context, "please complete ", Toast.LENGTH_LONG).show()
            }

            }}
        //}


//}




