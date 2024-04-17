package com.example.a2048orginalapp.presenter.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.example.a2048orginalapp.R

class ExitDialog(msg: String) : DialogFragment() {
    var listener: ExitListener? = null
    var message = msg
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return inflater.inflate(R.layout.exit_dialog, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val messageText = view.findViewById<View>(R.id.messageText) as TextView
        messageText.text = message

        view.findViewById<View>(R.id.yesButton).setOnClickListener { v: View? ->
            listener!!.yes()
            dismiss()
        }
        view.findViewById<View>(R.id.noButton).setOnClickListener { v: View? ->
            listener!!.no()
            dismiss()
        }
    }

    fun setExitListener(listener: ExitListener?) {
        this.listener = listener
    }

    override fun onStart() {
        super.onStart()
        val width = resources.displayMetrics.widthPixels * 0.96    // 90% of screen width
        val height = ViewGroup.LayoutParams.WRAP_CONTENT         // Automatic height
        dialog?.window?.setLayout(width.toInt(), height)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.setCanceledOnTouchOutside(false)
    }
}