package myapplication.android.vkvideoviewer.presentation.dialogs

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
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
                speed = SLOWEST_ID
                dismiss()
            }
            buttonNormal.setOnClickListener {
                speed = NORMAL_ID
                dismiss()
            }
            buttonFaster.setOnClickListener {
                speed = FASTER_ID
                dismiss()
            }
            buttonFastest.setOnClickListener {
                speed = FASTEST_ID
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
                    SPEED,
                    speed!!
                )
            })
        }
    }

    companion object{
        const val SLOWEST_ID = 0.5f
        const val NORMAL_ID = 1f
        const val FASTER_ID = 1.5f
        const val FASTEST_ID = 2f
        const val SPEED = "SpeedId"
    }
}