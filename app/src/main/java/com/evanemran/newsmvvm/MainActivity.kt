package com.evanemran.newsmvvm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.evanemran.newsmvvm.presentation.ui.theme.NewsMVVMTheme
import com.evanemran.newsmvvm.presentation.viewmodel.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: NewsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.loadNewsData()
        setContent {
            NewsMVVMTheme {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = viewModel.state.newsInfo?.totalResult.toString(),
                        color = Color.Green
                    )
                }
                if(viewModel.state.isLoading) {
                    CircularProgressIndicator()
                }
                viewModel.state.error?.let { error ->
                    Text(
                        text = error,
                        color = Color.Red,
                        textAlign = TextAlign.Center,
                    )
                }
            }
        }
    }
}