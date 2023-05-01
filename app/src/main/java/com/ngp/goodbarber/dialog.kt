package com.ngp.goodbarber


import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import androidx.fragment.app.DialogFragment


/**
 * Created by HP on 20/02/2016.
 */
class mDialogFragment : DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_dialog, container,
                false)
        isCancelable = true

        dialog?.window!!.requestFeature(Window.FEATURE_NO_TITLE)

        return v
    }

    fun close() {
        dismiss()
    }

}

