package myapplication.android.vkvideoviewer.presentation.dialogs

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import myapplication.android.vkvideoviewer.app.Constants
import myapplication.android.vkvideoviewer.databinding.DialogUpdateSpeedBinding
import myapplication.android.vkvideoviewer.presentation.listener.DialogDismissedListener

class VideoSpeedDialogFragment: DialogFragment() {

    private var dialogDismissedListener: DialogDismissedListener? = null
    private var speed: Float? = null

    fun setDialogDismissedListener(dialogDismissedListener: DialogDismissedListener) {
        this.dialogDismissedListener = dialogDismissedListener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = MaterialAlertDialogBuilder(requireContext())
        val binding = DialogUpdateSpeedBinding.inflate(layoutInflater)

        with(binding) {
            buttonSlowest.setOnClickListener {
                speed = Constants.SLOWEST_ID
                dismiss()
            }
            buttonNormal.setOnClickListener {
                speed = Constants.NORMAL_ID
                dismiss()
            }
            buttonFaster.setOnClickListener {
                speed = Constants.FASTER_ID
                dismiss()
            }
            buttonFastest.setOnClickListener {
                speed = Constants.FASTEST_ID
                dismiss()
            }
        }

        return builder.setView(binding.root).create()
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        if (speed != null && dialogDismissedListener != null) {
            dialogDismissedListener!!.handleDialogClose(Bundle().apply {
                putFloat(
                    Constants.SPEED,
                    speed!!
                )
            })
        }
    }
}