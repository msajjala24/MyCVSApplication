package com.cvs.tagsnap.ui.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.cvs.tagsnap.util.convertToLocalTime
import com.cvs.tagsnap.R

@Composable
internal fun DetailScreen(
    title: String,
    imageUrl: String,
    description: String,
    author: String,
    published: String
) {
    val scrollState = rememberScrollState()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 8.dp, end = 8.dp)
            .verticalScroll(scrollState)
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Image(
            painter = rememberAsyncImagePainter(model = imageUrl),
            contentDescription = title,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            alignment = Alignment.Center
        )
        Text(
            text = stringResource(R.string.name, title),
            style = MaterialTheme.typography.titleMedium.copy(fontStyle = FontStyle.Italic)
        )
        Spacer(modifier = Modifier.height(8.dp))
        DescriptionView(description)
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(R.string.author, author),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(
                    top = 24.dp
                )
        )
        Spacer(modifier = Modifier.height(8.dp))
        PublishedView(published)
        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
private fun DescriptionView(description: String) {
    Column {
        Text(
            text = stringResource(R.string.description),
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold // Make title bold
            ),
            modifier = Modifier.Companion
                .align(Alignment.Start)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = description,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

@Composable
private fun PublishedView(published: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.published),
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier
                .padding(end = 8.dp)
        )
        Text(
            text = convertToLocalTime(published),
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewDetailScreen() {
    DetailScreen(
        title = "Title",
        imageUrl = "https://example.com/image.jpg",
        description = "About this image is a image about me",
        author = "Author",
        published = "2023-07-15T12:00:00Z"
    )
}

@Preview(showBackground = true)
@Composable
private fun PreviewDescription() {
    DescriptionView(description = "About this image is a image about me")
}

@Preview(showBackground = true)
@Composable
private fun PreviewPublishedView() {
    PublishedView(published = "January 19, 2025")
}