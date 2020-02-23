package kr.hs.memo.presentation.edit

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.SystemClock
import android.provider.MediaStore
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.activity_edit.*
import kotlinx.android.synthetic.main.dialog_memo_external_url.view.*
import kr.hs.memo.R
import kr.hs.memo.base.BaseMemoActivity
import kr.hs.memo.databinding.ActivityEditBinding
import kr.hs.memo.model.MemoPhoto
import kr.hs.memo.presentation.edit.adapter.MemoImageAdapter
import kr.hs.memo.visible
import org.koin.androidx.viewmodel.ext.android.viewModel

class EditActivity : BaseMemoActivity() {
    private val binding by lazy {
        ActivityEditBinding.inflate(
            layoutInflater,
            null,
            true
        )
    }

    private val editViewModel: EditViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.lifecycleOwner = this
        binding.editviewmodel = editViewModel

        bindView()

        intent.getLongExtra(EXTRA_MEMO_ID, -1).takeIf { it != -1L }?.let {
            editViewModel.requestMemo(it)
        }
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
                        editViewModel.saveMemo(
                            edit_title.text.toString(),
                            edit_content?.text.toString(),
                            (viewpager_images.adapter as MemoImageAdapter).getItems()
                        )
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

        viewpager_images.adapter = MemoImageAdapter(this)

        editViewModel.finishLiveData.observe {
            finish()
        }

        editViewModel.memoLiveData.observe {
            it.photoUrls.forEach {
                viewpager_images.visible()
                text_image_title.visible()
                (viewpager_images.adapter as MemoImageAdapter).add(it)
            }
        }
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
                Intent().setType("image/*").setAction(Intent.ACTION_OPEN_DOCUMENT).putExtra(
                    Intent.EXTRA_ALLOW_MULTIPLE,
                    true
                ),
                "Select Picture"
            ), ACTIVITY_PHOTO_SELECT_FROM_GALLERY
        )
    }

    private var contentUri: Uri? = null

    private fun showCameraActivity() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE),
                PERMISSIONS_REQUEST_CAMERA
            )

            return
        }

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
            startActivityForResult(this, ACTIVITY_PHOTO_FROM_CAMERA)
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
                addImage(dialogView.edit_external_url.text.toString())
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
                    contentResolver.takePersistableUriPermission(
                        imageUri, Intent.FLAG_GRANT_READ_URI_PERMISSION
                    )
                    addImage(imageUri)
                } else if (data?.clipData != null) {
                    for (i in 0 until (data.clipData?.itemCount ?: 0)) {
                        val imageUri = data.clipData?.getItemAt(i)?.uri ?: continue
                        contentResolver.takePersistableUriPermission(
                            imageUri,
                            Intent.FLAG_GRANT_READ_URI_PERMISSION
                        )
                        addImage(imageUri)
                    }
                }
            }

            ACTIVITY_PHOTO_FROM_CAMERA -> {
                addImage(contentUri ?: return)
            }
        }
    }

    private fun addImage(uri: Uri) {
        grantUriPermission(applicationContext.packageName, uri, FLAG_GRANT_READ_URI_PERMISSION)
        viewpager_images.visible()
        text_image_title.visible()
        (viewpager_images.adapter as MemoImageAdapter).add(MemoPhoto.InternalPhoto(uri))
    }

    private fun addImage(url: String) {
        viewpager_images.visible()
        text_image_title.visible()
        (viewpager_images.adapter as MemoImageAdapter).add(MemoPhoto.ExternalPhoto(url))
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            PERMISSIONS_REQUEST_CAMERA -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    showCameraActivity()
                } else {
                    Toast.makeText(
                        this,
                        "권한이 없어 카메라를 실행할 수 없습니다.",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    companion object {
        fun newIntent(context: Context, memoId: Long? = null) =
            Intent(context, EditActivity::class.java).putExtra(EXTRA_MEMO_ID, memoId)

        private const val EXTRA_MEMO_ID = "extra_memo_id"

        private const val ACTIVITY_PHOTO_SELECT_FROM_GALLERY = 1
        private const val ACTIVITY_PHOTO_FROM_CAMERA = 2

        private const val PERMISSIONS_REQUEST_CAMERA = 1
    }
}