package com.example.pricerecommender.ui.screen.product

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import coil.compose.rememberAsyncImagePainter
import com.example.pricerecommender.R
import com.example.pricerecommender.ui.theme.PriceRecommenderTheme
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Objects

@Composable
fun ReceiptCaptureScreen(
    imageUri: Uri,
    updateImageUri: (Uri) -> Unit,
    deleteButton: () -> Unit,
    confirmButton: () -> Unit
) {
    val context = LocalContext.current
    val file = context.createImageFile()
    val uri = FileProvider.getUriForFile(
        Objects.requireNonNull(context),
        context.packageName + ".provider", file
    )

    val cameraLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()){
            updateImageUri(uri)
        }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ){
        if (it) {
            Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT).show()
            cameraLauncher.launch(uri)
        } else {
            Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
        }
    }

    Column(
        Modifier
            .fillMaxSize()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 24.dp)
        ){
            if (imageUri != Uri.EMPTY) {
                RoundedButton(
                    onClick = deleteButton,
                    icon = Icons.Default.Clear,
                    contentDescription = stringResource(R.string.delete)
                )
            }
            FloatingActionButton(
                onClick = {
                    val permissionCheckResult =
                        ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)

                    if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
                        cameraLauncher.launch(uri)
                    } else {
                        permissionLauncher.launch(Manifest.permission.CAMERA)
                    }
                },
                shape = RoundedCornerShape(48.dp),
                modifier = Modifier.size(90.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.camera),
                    contentDescription = stringResource(R.string.capture),
                    modifier = Modifier.size(48.dp)
                )
            }
            if (imageUri != Uri.EMPTY) {
                RoundedButton(
                    onClick = confirmButton,
                    icon = Icons.Default.Check,
                    contentDescription = stringResource(R.string.confirm)
                )
            }
        }
    }


    if (imageUri.path?.isNotEmpty() == true) {
        Image(
            modifier = Modifier
                .padding(16.dp, 8.dp),
            painter = rememberAsyncImagePainter(imageUri),
            contentDescription = null
        )
    } else {
        NoImageCaptured()
    }
}

@Composable
fun RoundedButton(
    onClick: () -> Unit = {},
    icon: ImageVector,
    contentDescription: String
) {
    FloatingActionButton(
        onClick = onClick,
        shape = RoundedCornerShape(36.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription
        )
    }
}

@Composable
fun NoImageCaptured() {
    Box(
        modifier = Modifier.padding(36.dp)
    ){
        Card(
            shape = RoundedCornerShape(8.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.camera),
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(60.dp)
                )
                Text(
                    text = "No image captured",
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@SuppressLint("SimpleDateFormat")
fun Context.createImageFile(): File {
    return try {
        val timeStamp = SimpleDateFormat("yyyy_MM_dd_HHmmss").format(Date())
        val imageFileName = "IHC_${timeStamp}_"
        File.createTempFile(
            imageFileName,
            ".jpg",
            externalCacheDir
        )
    } catch (e: IOException) {
        e.printStackTrace()
        throw e
    }
}

@Preview
@Composable
fun NoImageCapturedPreview() {
    PriceRecommenderTheme {
        NoImageCaptured()
    }
}

@Preview
@Composable
fun ConfirmButtonPreview() {
    PriceRecommenderTheme {
        RoundedButton(
            icon = Icons.Default.Check,
            contentDescription = "")
    }
}