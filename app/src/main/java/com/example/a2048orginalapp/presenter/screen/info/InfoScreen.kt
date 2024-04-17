package com.example.a2048orginalapp.presenter.screen.info

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.a2048orginalapp.R
import com.example.a2048orginalapp.databinding.ScreenInfoBinding

class InfoScreen:Fragment(R.layout.screen_info) {
    private val binding by viewBinding(ScreenInfoBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
         binding.back.setOnClickListener {
             findNavController().popBackStack()
         }
        
    }
}