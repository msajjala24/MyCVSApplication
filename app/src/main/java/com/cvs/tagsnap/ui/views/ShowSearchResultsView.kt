package com.cvs.tagsnap.ui.views

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.cvs.tagsnap.model.SnapImage

@Composable
internal fun ShowSearchResultsView(images: List<SnapImage>, navController: NavHostController) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(images.size) { index ->
            val image = images[index]
            FlickrImageItem(image, navController)
        }
    }
}

@Composable
private fun FlickrImageItem(image: SnapImage, navController: NavHostController) {
    val encodedImageUrl = Uri.encode(image.media.media)
    val encodedTitle = Uri.encode(image.title)
    val encodedDescription = Uri.encode(image.description)
    val encodedAuthor = Uri.encode(image.author)
    val encodedPublished = Uri.encode(image.published)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp, top = 8.dp)
            .clickable {
                navController.navigate(
                    "detail_screen/${encodedImageUrl}/" +
                            "${encodedTitle}/${encodedDescription}/${encodedAuthor}/${encodedPublished}"
                )
            },
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 4.dp,
            pressedElevation = 8.dp,
            focusedElevation = 6.dp
        )
    ) {
        ThumbnailWithTitle(imageUrl = image.media.media, title = image.title)
    }
}


@Composable
private fun ThumbnailWithTitle(imageUrl: String, title: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val painter = rememberAsyncImagePainter(
            model = imageUrl, // The image URL
            placeholder = painterResource(id = android.R.drawable.ic_menu_report_image),
            error = painterResource(id = android.R.drawable.ic_menu_report_image)
        )

        Image(
            painter = painter,
            contentDescription = title,
            modifier = Modifier
                .size(80.dp)
                .padding(end = 8.dp),
            contentScale = ContentScale.Crop,
            alignment = Alignment.Center
        )

        // Display the title next to the image
        Text(
            text = title,
            style = MaterialTheme.typography.body2,
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 8.dp),
            maxLines = 3,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewThumbnailWithTitle() {
    ThumbnailWithTitle(
        imageUrl = "https://example.com/image.jpg",
        title = "Sample Image Title"
    )
}