package com.example.a2048orginalapp.presenter.screen.game

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.view.get
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.a2048orginalapp.R
import com.example.a2048orginalapp.data.model.SideEnum
import com.example.a2048orginalapp.databinding.ScreenGamesBinding
import com.example.a2048orginalapp.presenter.dialog.ExitDialog
import com.example.a2048orginalapp.presenter.dialog.ExitListener
import com.example.a2048orginalapp.utils.MyBackgroundUtil
import com.example.a2048orginalapp.utils.MyTouchListener

class GameScreen : Fragment(R.layout.screen_games) {
    private val binding by viewBinding(ScreenGamesBinding::bind)
    private val viewModel = GameViewModel()
    private var maxScore = 0
    private var finishBool = false
    private var myDialog: ExitDialog? = null
    private val views = ArrayList<AppCompatTextView>(16)

    @SuppressLint("FragmentLiveDataObserve")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        loadViews()
        attachToListener()
        loadData()
        restart()
        home()
        goBack()

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                exitDialog("Do you want to quit the game")
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)

    }

    private fun backVisible() {
        val boolean = viewModel.backVisible()
        if (!boolean) {
            binding.goBack.setBackgroundResource(R.drawable.bg_back)
        } else {
            binding.goBack.setBackgroundResource(R.drawable.bg_appcompat_button)
        }

    }

    private fun goBack() {
        binding.goBack.setOnClickListener {
            if (finishBool) {
                binding.gameOver.isVisible = false
            }
            viewModel.goBack()
            binding.scoreText.text = viewModel.getScore().toString()
            backVisible()
            loadData()
        }
    }


    private fun home() {
        binding.home.setOnClickListener {
            exitDialog("Do you want to quit the game")
        }
    }

    private fun exitDialog(msg: String) {
        myDialog = ExitDialog(msg)
        myDialog!!.setExitListener(object : ExitListener {
            override fun yes() {
                findNavController().popBackStack()
            }

            override fun no() {}

        })
        myDialog!!.show(parentFragmentManager,"back dialog")


    }

    private fun restart() {
        binding.restart.setOnClickListener {
            val back = binding.goBack
            back.setBackgroundResource(R.drawable.bg_back)
            back.isClickable = false
            if (finishBool) {
                binding.gameOver.isVisible = false
                viewModel.restart()
                loadData()
            } else {
                val res = binding.gameRestart
                val bg = binding.restartBg
                res.isVisible = true
                bg.isVisible = true
                binding.no.setOnClickListener {
                    res.isVisible = false
                    bg.isVisible = false
                    back.isClickable = true
                    back.setBackgroundResource(R.drawable.bg_appcompat_button)
                }
                binding.yes.setOnClickListener {
                    res.isVisible = false
                    bg.isVisible = false
                    viewModel.restart()
                    back.isClickable = true
                    back.setBackgroundResource(R.drawable.bg_appcompat_button)
                    loadData()
                }
            }
        }
    }

    private fun loadData() {
        val matrix = viewModel.getMatrix()
        for (i in matrix.indices) {
            for (j in matrix[i].indices) {
                if (matrix[i][j] == 0) views[i * 4 + j].text = ""
                else views[i * 4 + j].text = "${matrix[i][j]}"

                views[i * 4 + j].setBackgroundResource(MyBackgroundUtil.backgroundByAmount(matrix[i][j]))
            }
        }
        binding.scoreText.text = viewModel.getScore().toString()
    }

    private fun loadViews() {
        for (i in 0 until binding.mainView.childCount) {
            val line = binding.mainView[i] as LinearLayoutCompat
            for (j in 0 until line.childCount) {
                views.add(line[j] as AppCompatTextView)
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun attachToListener() {
        val myTouchListener = MyTouchListener(requireContext())
        myTouchListener.setActionSideEnumListener {
//            Toast.makeText(requireContext(), it.name, Toast.LENGTH_SHORT).show()
//            Log.d("TTT", it.name)
            when (it) {
                SideEnum.DOWN -> viewModel.moveDown()
                SideEnum.UP -> viewModel.moveUp()
                SideEnum.LEFT -> viewModel.moveLeft()
                SideEnum.RIGHT -> viewModel.moveRight()
            }
            val score = viewModel.getScore()
            binding.scoreText.text = score.toString()
            if (maxScore < score) {
                maxScore = score
                binding.scoreHighText.text = maxScore.toString()
            }
            backVisible()
            loadData()
            finishBool = viewModel.finish()
            finish()
        }
        binding.root.setOnTouchListener(myTouchListener)
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadDate()
        loadData()
        maxScore = viewModel.getMaxScore()
        binding.scoreHighText.text = maxScore.toString()
    }

    override fun onStop() {
        super.onStop()
        viewModel.saveMaxScore(maxScore)
        viewModel.saveDate()
    }

    private fun finish() {
        if (finishBool) {
            binding.gameOver.isVisible = true
        }
    }
}

