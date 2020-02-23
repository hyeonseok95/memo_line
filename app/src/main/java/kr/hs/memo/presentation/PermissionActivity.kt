package kr.hs.memo.presentation

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kr.hs.memo.base.BaseMemoActivity
import kr.hs.memo.presentation.main.MainActivity

class PermissionActivity : BaseMemoActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                PERMISSIONS_REQUEST_EXTERNAL_STORAGE
            )

            return
        }

        startActivity(MainActivity.newIntent(this))
        finish()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            PERMISSIONS_REQUEST_EXTERNAL_STORAGE -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    startActivity(MainActivity.newIntent(this))
                    finish()
                } else {
                    finish()
                }
            }
        }
    }


    companion object {
        private const val PERMISSIONS_REQUEST_EXTERNAL_STORAGE = 1
    }
}