package com.example.iterativepractice.legacy.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.iterativepractice.R

class WelcomeFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val welcomePage = inflater.inflate(R.layout.fragment_welcome, container, false)
        return welcomePage
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = WelcomeFragmentArgs.fromBundle(requireArguments())
        val email = args.email
        val welcomeTextView: TextView = view.findViewById(R.id.welcome_message)
        val msg = "Welcome, please confirm your email: $email!"
        welcomeTextView.text = msg
    }
}