package myapplication.android.vkvideoviewer.presentation.dialogs

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
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
                quality = TINY_ID
                dismiss()
            }
            buttonQualityMedium.setOnClickListener {
                quality = MEDIUM_ID
                dismiss()
            }
            buttonQualitySmall.setOnClickListener {
                quality = SMALL_ID
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
                    QUALITY,
                    quality
                )
            })
        }
    }

    companion object{
        const val TINY_ID = "270р"
        const val SMALL_ID = "360р"
        const val MEDIUM_ID = "720р"
        const val QUALITY = "QualityId"
    }

}