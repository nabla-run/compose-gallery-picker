package run.nabla.gallerypicker.example

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        val permission = Manifest.permission.READ_EXTERNAL_STORAGE
        val permissionGranted =
            ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
        val startDestination = if (permissionGranted) GALLERY_SCREEN else PERMISSION_SCREEN

        setContent {
            val navController = rememberNavController()
            NavHost(
                navController = navController,
                startDestination = startDestination
            ) {
                permissionScreen(
                    onPermissionGranted = {
                        navController.navigate(GALLERY_SCREEN)
                    }
                )
                galleryPicker(
                    onImageSelected = {
                        navController.navigate("$IMAGE_EDITOR_SCREEN/${Uri.encode(it.toString())}")
                    }
                )
                imageEditor(
                    onBackClick = navController::navigateUp
                )
            }
        }
    }
}