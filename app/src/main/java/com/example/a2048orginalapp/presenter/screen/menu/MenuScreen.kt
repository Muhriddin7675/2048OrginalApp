package com.example.a2048orginalapp.presenter.screen.menu

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.a2048orginalapp.R
import com.example.a2048orginalapp.databinding.ScreenMenuBinding
import com.example.a2048orginalapp.domain.AppRepositoryImpl
import com.example.a2048orginalapp.presenter.dialog.ExitDialog
import com.example.a2048orginalapp.presenter.dialog.ExitListener
import com.example.a2048orginalapp.presenter.screen.game.GameViewModel

class MenuScreen : Fragment(R.layout.screen_menu) {
    private val binding by viewBinding(ScreenMenuBinding::bind)
    private val viewModel = GameViewModel()
    private var myDialog: ExitDialog? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.newGame.setOnClickListener {
            findNavController().navigate(R.id.action_menuScreen_to_gameScreen)
        }

        binding.info.setOnClickListener {
            findNavController().navigate(R.id.action_menuScreen_to_infoScreen)
        }
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                myDialog = ExitDialog("Do you want to exit Memory Game")
                myDialog!!.setExitListener(object : ExitListener {
                    override fun yes() {
                        requireActivity().finish()
                    }
                    override fun no() {}

                })
                myDialog!!.show(parentFragmentManager,"exit dialog")
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }
}