package com.marvelcharactercatalog

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.marvelcharactercatalog.ui.theme.ComicModel
import com.marvelcharactercatalog.ui.theme.MarvelCharacterCatalogTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {

    var comicName = mutableStateOf("DefaultText")
    var comicDescription = mutableStateOf("DefaultText")
    var comicImageUrl = mutableStateOf("DefaultText")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var comicModel = ComicModel("Default value","Default value","Default value" )
        runBlocking {
            launch {
                comicModel = getComicData(109961)
            }
        }
        setContent {
            MarvelCharacterCatalogTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = colorResource(id = R.color.dark_gray_background)
                ) {
                  ComicElements(comicModel =comicModel )
                }
            }
        }
    }
    @Composable
    fun ComicElements(comicModel: ComicModel) {
        Column(modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(model = comicImageUrl.value, contentDescription = "comic Image url" )
                // Buttons
                Column(modifier = Modifier.padding(start = 16.dp)) {

                    Button(
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                            .border(1.dp, Color.White, shape = MaterialTheme.shapes.small),
                        colors = ButtonDefaults.buttonColors(colorResource(id = R.color.marvel_purple)),
                        shape = MaterialTheme.shapes.small,
                        onClick = { /* Do something when button 1 is clicked */ }
                    ) {
                        Text("Read Now")
                    }
                    Button(
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                            .border(1.dp, Color.White, shape = MaterialTheme.shapes.small),
                        colors = ButtonDefaults.buttonColors(colorResource(id = R.color.dark_gray_background)),
                        shape = MaterialTheme.shapes.small,
                        onClick = { /* Do something when button 2 is clicked */ }) {
                        Text("Mark as Read")
                    }
                    Button(
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                            .border(1.dp, Color.White, shape = MaterialTheme.shapes.small),
                        colors = ButtonDefaults.buttonColors(colorResource(id = R.color.dark_gray_background)),
                        shape = MaterialTheme.shapes.small,                        onClick = { /* Do something when button 2 is clicked */ }) {
                        Text("Add to library")
                    }
                    Button(
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                            .border(1.dp, Color.White, shape = MaterialTheme.shapes.small),
                        colors = ButtonDefaults.buttonColors(colorResource(id = R.color.dark_gray_background)),
                        shape = MaterialTheme.shapes.small,                        onClick = { /* Do something when button 2 is clicked */ }) {
                        Text("Read offline")
                    }
                }
            }
            Text(
                fontSize = 30.sp,
                color = Color.White,
                text = comicName.value
            )
            Divider(color = Color.White, thickness = 1.dp)

            Text(
                modifier = Modifier.padding(vertical = 15.dp),
                fontSize = 20.sp,
                color = Color.White,
                text = "The Story"
            )
            Text(
                color = Color.White,
                text = comicDescription.value
            )
        }
    }

    fun getComicData(id: Int): ComicModel {
        val comicModel = ComicModel("Default value", "Default value", "Default value")
        MarvelApiClient.getComic(
            comicId = id,
            onResponse = { comicResponse ->
                if (comicResponse != null) {
                    if(comicResponse.code == 200) {

                        val comic = comicResponse.data.results.firstOrNull()
                        if (comic != null) {
                            comicName.value = comic.title
                            comicDescription.value = comic.description
                            comicImageUrl.value = convertHttpToHttps(comic.images[0].path +"."+ comic.images[0].extension)
                        }
                    }
                }
                // Handle successful response
            },
            onFailure = { error ->
                comicName.value = "No Comic Found"
                comicDescription.value = "No Comic Found"
                comicImageUrl.value = "No Comic Found"
            }
        )
        return comicModel
    }



    @Preview(showBackground = true)
    @Composable
    fun ComicPreview() {
        val textComicModel = ComicModel("test","test", "test")
        MarvelCharacterCatalogTheme {
            ComicElements(textComicModel)
        }
    }
    fun convertHttpToHttps(httpUrl: String): String {
        val httpsPrefix = "https://"
        val httpPrefix = "http://"

        return if (httpUrl.startsWith(httpPrefix)) {
            // Replace 'http://' with 'https://'
            httpsPrefix + httpUrl.substring(httpPrefix.length)
        } else {
            // If the URL already starts with 'https://', return it as is
            if (httpUrl.startsWith(httpsPrefix)) {
                httpUrl
            } else {
                // If the URL does not start with either 'http://' or 'https://', assume it's a relative URL and prepend 'https://'
                httpsPrefix + httpUrl
            }
        }
    }

}



