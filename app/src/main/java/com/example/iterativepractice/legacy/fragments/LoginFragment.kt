package com.example.iterativepractice.legacy.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.iterativepractice.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val loginView = inflater.inflate(R.layout.fragment_login, container, false)
        return loginView
    }

    private suspend fun performAuthServiceOne(): Boolean {
        delay(500L)
        val res = true
        return res
    }

    private suspend fun performAuthServiceTwo(): Boolean {
        delay(1000L)
        val res = true
        return res
    }

    private suspend fun authCheck() = coroutineScope {
        withContext(Dispatchers.IO) {
            val firstApiCall = performAuthServiceOne()
            val secondApiCall = performAuthServiceTwo()

            if (firstApiCall && secondApiCall) {
                return@withContext true
            } else return@withContext false
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val button = view.findViewById<Button>(R.id.button)
        val signUpButton = view.findViewById<Button>(R.id.button2)

        button.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                val res = authCheck()
                if (res) {
                    val action = LoginFragmentDirections.actionFragmentLoginToFragmentMenu()
                    findNavController().navigate(action)
                } else {
                    println("Something went wrong")
                   }
                }
            }
        signUpButton.setOnClickListener {
            findNavController().navigate(R.id.fragment_login_to_sign_up_bottom_sheet)
        }
        /*signUpButton.setOnClickListener {
            val bottomSheet = SignUpBottomSheetDialog()
            bottomSheet.show(parentFragmentManager, "SignUpBottomSheet")
        }*/
        }
    }
