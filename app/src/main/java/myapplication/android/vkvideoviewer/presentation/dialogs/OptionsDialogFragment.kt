package myapplication.android.vkvideoviewer.presentation.dialogs

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
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
                option = OPTION_QUALITY
                dismiss()
            }
            buttonSpeed.setOnClickListener {
                option = OPTION_SPEED
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
                    OPTIONS,
                    option
                )
            })
        }
    }

    companion object{
        const val OPTIONS = "OptionsId"
        const val OPTION_QUALITY = "OptionQuality"
        const val OPTION_SPEED = "OptionSpeed"
    }

}