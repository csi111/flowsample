package com.csi.flowexample.ui.main.signin

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.csi.flowexample.R

class EmailVerifyFailedFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext()).setTitle(android.R.string.dialog_alert_title)
            .setMessage(R.string.verification_code_failed)
            .setCancelable(false)
            .setPositiveButton(android.R.string.ok) { dialog, _ ->
                dialog.dismiss()
            }.create()
    }
}