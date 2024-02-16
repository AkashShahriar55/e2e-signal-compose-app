package com.nsa.ui.component

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size


@Composable
fun GallaryImage(modifier:Modifier = Modifier,image: Uri){
    Image(
        painter = rememberAsyncImagePainter(
            model = ImageRequest.Builder(LocalContext.current)
                .data(image)
                .size(Size.ORIGINAL) // Set the target size to load the image at.
                .build()
        ),
        contentDescription = "Gallary image",
        modifier = modifier
            .aspectRatio(1f)
            .padding(5.dp)
            .clip(RoundedCornerShape(10.dp)),
        contentScale = ContentScale.Crop
    )
}