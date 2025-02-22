package myapplication.android.vkvideoviewer.presentation.dialogs

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import myapplication.android.vkvideoviewer.app.Constants
import myapplication.android.vkvideoviewer.databinding.DialogUpdateQualityBinding
import myapplication.android.vkvideoviewer.presentation.listener.DialogDismissedListener

class VideoQualityDialogFragment: DialogFragment() {

    private var dialogDismissedListener: DialogDismissedListener? = null
    private var quality: String? = null

    fun setDialogDismissedListener(dialogDismissedListener: DialogDismissedListener) {
        this.dialogDismissedListener = dialogDismissedListener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = MaterialAlertDialogBuilder(requireContext())
        val binding = DialogUpdateQualityBinding.inflate(layoutInflater)

        with(binding) {
            buttonQualityTiny.setOnClickListener {
                quality = Constants.TINY_ID
                dismiss()
            }
            buttonQualityMedium.setOnClickListener {
                quality = Constants.MEDIUM_ID
                dismiss()
            }
            buttonQualitySmall.setOnClickListener {
                quality = Constants.SMALL_ID
                dismiss()
            }
        }

        return builder.setView(binding.root).create()
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        if (quality != null && dialogDismissedListener != null) {
            dialogDismissedListener!!.handleDialogClose(Bundle().apply {
                putString(
                    Constants.QUALITY,
                    quality
                )
            })
        }
    }

}