package com.example.iterativepractice.legacy.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.navigation.fragment.findNavController
import com.example.iterativepractice.R
import com.example.iterativepractice.legacy.dialog.SignUpBottomSheetDialogDirections
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SignUpBottomSheetDialog: BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.layout_bottom_sheet_sign_up, container, false)

        val etEmail = view.findViewById<EditText>(R.id.etEmail)
        val etPassword = view.findViewById<EditText>(R.id.etPassword)
        val btnSubmit = view.findViewById<Button>(R.id.btnSubmit)

        btnSubmit.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()
            val direction =
                SignUpBottomSheetDialogDirections.actionSignUpBottomSheetToFragmentWelcome(email)
            findNavController().navigate(direction)
        }
        return view
        }
    }