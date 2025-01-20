package com.cvs.tagsnap.ui.views

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.cvs.tagsnap.model.UiState
import com.cvs.tagsnap.viewmodel.SnapViewModel
import com.cvs.tagsnap.R

@Composable
fun SearchScreen(navController: NavHostController) {

    var query by remember { mutableStateOf("") }
    val viewModel: SnapViewModel = hiltViewModel()
    val uiState = viewModel.uiState.value
    val context = LocalContext.current

    Column(modifier = Modifier.fillMaxSize()) {
        SearchBar(
            query = query,
            onQueryChange = { query = it },
            onSearch = {
                if (query.isEmpty()) {
                    Toast.makeText(
                        context,
                        context.getString(R.string.empty_query),
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    viewModel.searchImages(query)
                }
            }
        )
        Spacer(modifier = Modifier.height(16.dp))

        when (uiState) {
            is UiState.None -> {
                ShowDefaultView()
            }

            is UiState.Loading -> {
                Loading()
            }

            is UiState.Success -> {
                if (uiState.data.isEmpty()) {
                    NoResultsFoundView()
                } else {
                    ShowSearchResultsView(uiState.data, navController)
                }
            }

            is UiState.Error -> {
                ShowErrorView(
                    onRetry = { viewModel.searchImages(query) }
                )
            }
        }
    }
}

@Composable
private fun Loading() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: () -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = query,
            onValueChange = onQueryChange,
            modifier = Modifier.weight(1f),
            placeholder = { Text(text = stringResource(R.string.search_query_hint)) },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    onSearch()
                    keyboardController?.hide()
                }
            ),
            leadingIcon = {
                Icon(imageVector = Icons.Default.Search, contentDescription = "Search Icon")
            },
            trailingIcon = {
                if (query.isNotEmpty()) {
                    IconButton(onClick = { onQueryChange("") }) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Clear Query")
                    }
                }
            },
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = Color.Gray,
                focusedLeadingIconColor = MaterialTheme.colorScheme.primary,
                unfocusedLeadingIconColor = Color.Gray,
                focusedTrailingIconColor = MaterialTheme.colorScheme.primary,
                unfocusedTrailingIconColor = Color.Gray
            ),
            isError = query.isNotEmpty() && query.length < 3 // Optional validation
        )

        //Search button
        IconButton(onClick = {
            keyboardController?.hide()
            onSearch()
        }) {
            Icon(imageVector = Icons.Default.Search, contentDescription = "Search Button")
        }
    }
}

