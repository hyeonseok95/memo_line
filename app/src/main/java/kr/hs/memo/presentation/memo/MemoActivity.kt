package kr.hs.memo.presentation.memo

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.SystemClock
import android.provider.MediaStore
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_memo.*
import kotlinx.android.synthetic.main.dialog_memo_external_url.view.*
import kr.hs.memo.R
import kr.hs.memo.base.BaseMemoActivity
import kr.hs.memo.presentation.memo.adapter.MemoImageAdapter

class MemoActivity : BaseMemoActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_memo)

        bindView()
    }

    private fun bindView() {
        toolbar.apply {
            setNavigationOnClickListener {
                onBackPressed()
            }

            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.add_image -> {
                        showAddImageDialog()
                    }

                    R.id.save -> {
                    }
                }
                true
            }
        }

        linear_memo.setOnTouchListener { _, motionEvent ->
            if (motionEvent.action == MotionEvent.ACTION_UP) {
                edit_content.requestFocus()
                (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).showSoftInput(
                    edit_content,
                    0
                )
                true
            } else {
                false
            }
        }

        viewpager_images.adapter = MemoImageAdapter()
    }

    private fun showAddImageDialog() {
        val method = arrayOf("갤러리", "카메라", "외부URL")

        MaterialAlertDialogBuilder(this)
            .setTitle("이미지 첨부 방식")
            .setItems(method) { _, position ->
                when (position) {
                    0 -> showGalleryActivity()
                    1 -> showCameraActivity()
                    2 -> showAddExternalUrlDialog()
                }
            }
            .show()
    }

    private fun showGalleryActivity() {
        startActivityForResult(
            Intent.createChooser(
                Intent().setType("image/*").setAction(Intent.ACTION_GET_CONTENT).putExtra(
                    Intent.EXTRA_ALLOW_MULTIPLE,
                    true
                ),
                "Select Picture"
            ), ACTIVITY_PHOTO_SELECT_FROM_GALLERY
        )
    }

    private var contentUri: Uri? = null

    private fun showCameraActivity() {
        // TODO: Camera Permission 받아야함 : 요구하는 경우 있음.
        val values = ContentValues().apply {
            val fileName = "HSNote-${SystemClock.currentThreadTimeMillis()}.jpg"

            put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpg")

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                put(MediaStore.Images.Media.IS_PENDING, 0)
            }
        }

        Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
            putExtra(
                MediaStore.EXTRA_OUTPUT,
                contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values).apply {
                    contentUri = this
                }
            )
            startActivityForResult(this, ACTIVITY_PHOTO_FORM_CAMERA)
        }
    }

    private fun showAddExternalUrlDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_memo_external_url, null)

        MaterialAlertDialogBuilder(this)
            .setTitle("외부 이미지 URL입력")
            .setView(dialogView)
            .setNegativeButton("취소") { dialogInterface, _ ->
                dialogInterface.cancel()
            }
            .setPositiveButton("확인") { _, _ ->
                Toast.makeText(this, dialogView.edit_external_url.text, Toast.LENGTH_SHORT).show()
            }
            .setCancelable(false)
            .show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK) {
            if (contentUri != null) {
                contentResolver.delete(contentUri ?: return, null, null)
                contentUri = null
            }
            return
        }

        //TODO : 세로이미지 처리
        when (requestCode) {
            ACTIVITY_PHOTO_SELECT_FROM_GALLERY -> {
                if (data?.data != null) {
                    val imageUri = data.data ?: return
                    Picasso.get().load(imageUri).fit().centerCrop().into(img)
                } else if (data?.clipData != null) {
                    for (i in 0 until (data.clipData?.itemCount ?: 0)) {
                        val imageUri = data.clipData?.getItemAt(i)?.uri ?: continue
                        Picasso.get().load(imageUri).fit().centerCrop().into(img)
                    }
                }
            }

            ACTIVITY_PHOTO_FORM_CAMERA -> {
                Picasso.get().load(contentUri).fit().centerCrop().into(img)
            }
        }
    }

    companion object {
        fun newIntent(context: Context) = Intent(context, MemoActivity::class.java)

        private const val ACTIVITY_PHOTO_SELECT_FROM_GALLERY = 1
        private const val ACTIVITY_PHOTO_FORM_CAMERA = 2
    }
}