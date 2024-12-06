package com.example.zodipair.ui.user_validation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.zodipair.R
import com.example.zodipair.ui.user_validation.PersonalInformationFragment
import com.example.zodipair.ui.user_validation.RegisterActivity

class CredentialsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_credentials, container, false)

        val nextButton = view.findViewById<Button>(R.id.next_button)
        nextButton.setOnClickListener {
            val activity = activity as RegisterActivity
            activity.showFragment(PersonalInformationFragment())
        }

        return view
    }
}
