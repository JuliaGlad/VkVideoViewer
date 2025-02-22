package myapplication.android.vkvideoviewer.presentation.dialogs

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import myapplication.android.vkvideoviewer.app.Constants
import myapplication.android.vkvideoviewer.databinding.DialogVideoOptionsBinding
import myapplication.android.vkvideoviewer.presentation.listener.DialogDismissedListener

class OptionsDialogFragment : DialogFragment() {

    private var dialogDismissedListener: DialogDismissedListener? = null
    private var option: String? = null

    fun setDialogDismissedListener(dialogDismissedListener: DialogDismissedListener) {
        this.dialogDismissedListener = dialogDismissedListener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = MaterialAlertDialogBuilder(requireContext())
        val binding = DialogVideoOptionsBinding.inflate(layoutInflater)

        with(binding) {
            buttonQuality.setOnClickListener {
                option = Constants.OPTION_QUALITY
                dismiss()
            }
            buttonSpeed.setOnClickListener {
                option = Constants.OPTION_SPEED
                dismiss()
            }
        }

        return builder.setView(binding.root).create()
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        if (option != null && dialogDismissedListener != null) {
            dialogDismissedListener!!.handleDialogClose(Bundle().apply {
                putString(
                    Constants.OPTIONS,
                    option
                )
            })
        }
    }

}