package com.marvelcharactercatalog.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.marvelcharactercatalog.clients.MarvelApiClient
import com.marvelcharactercatalog.R
import com.marvelcharactercatalog.ui.theme.MarvelCharacterCatalogTheme
import com.marvelcharactercatalog.utils.MarvelApiUtility
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainActivity : ComponentActivity() {

    private var comicName = mutableStateOf("No Comic Name found")
    private var comicDescription = mutableStateOf("No Description found")
    private var comicImageUrl = mutableStateOf("No Image Url found")
    private val MarvelAttribution = "Data provided by Marvel. © 2014 Marvel"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        runBlocking {
            launch {
                getComicData(109961)
            }
        }
        setContent {
            MarvelCharacterCatalogTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = colorResource(id = R.color.dark_gray_background)
                ) {
                    ComicElements()
                }
            }
        }
    }

    /**
     * composable function used for showing the comic title, image and description
     */
    @Composable
    fun ComicElements() {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
            ) {
                if (comicImageUrl.value.contains("https")) {
                    AsyncImage(model = comicImageUrl.value, contentDescription = "comic Image url")
                    // Buttons
                    Column(modifier = Modifier.padding(start = 16.dp)) {

                        Button(modifier = Modifier
                            .padding(vertical = 8.dp),
                            colors = ButtonDefaults.buttonColors(colorResource(id = R.color.marvel_purple)),
                            shape = MaterialTheme.shapes.small,
                            onClick = { /* Do something when button 1 is clicked */ }) {
                            Text("Read Now")
                        }
                        Button(modifier = Modifier
                            .padding(vertical = 8.dp)
                            .border(1.dp, Color.White, shape = MaterialTheme.shapes.small),
                            colors = ButtonDefaults.buttonColors(colorResource(id = R.color.dark_gray_background)),
                            shape = MaterialTheme.shapes.small,
                            onClick = { /* Do something when button 2 is clicked */ }) {
                            Text("Mark as Read")
                        }
                        Button(modifier = Modifier
                            .padding(vertical = 8.dp)
                            .border(1.dp, Color.White, shape = MaterialTheme.shapes.small),
                            colors = ButtonDefaults.buttonColors(colorResource(id = R.color.dark_gray_background)),
                            shape = MaterialTheme.shapes.small,
                            onClick = { /* Do something when button 2 is clicked */ }) {
                            Text("Add to library")
                        }
                        Button(modifier = Modifier
                            .padding(vertical = 8.dp)
                            .border(1.dp, Color.White, shape = MaterialTheme.shapes.small),
                            colors = ButtonDefaults.buttonColors(colorResource(id = R.color.dark_gray_background)),
                            shape = MaterialTheme.shapes.small,
                            onClick = { /* Do something when button 2 is clicked */ }) {
                            Text("Read offline")
                        }
                    }
                } else {
                    Text(fontSize = 30.sp, color = Color.White, text = "Image not found")
                }
            }
            Text(
                fontSize = 30.sp, color = Color.White, text = comicName.value
            )
            Divider(color = Color.White, thickness = 1.dp)

            Text(
                modifier = Modifier.padding(vertical = 15.dp),
                fontSize = 20.sp,
                color = Color.White,
                text = "The Story"
            )
            Text(
                color = Color.White, text = comicDescription.value + "\n\n $MarvelAttribution"
            )
        }
    }

    /**
     * Function to call the MarvelApiClient and get the comic details and set the values
     */
    private fun getComicData(id: Int) {
        MarvelApiClient.getComic(comicId = id, onResponse = { comicResponse ->
            if (comicResponse != null) {
                // Handle successful response
                if (comicResponse.code == 200) {

                    val comic = comicResponse.data.results.firstOrNull()
                    if (comic != null) {
                        if(comic.title.isNotEmpty()) {
                            comicName.value = comic.title
                        }
                        if(comic.description.isNotEmpty()) {
                            comicDescription.value = comic.description
                        }
                        if(comic.images.isNotEmpty()) {
                            comicImageUrl.value =
                                MarvelApiUtility.convertHttpToHttps(comic.images[0].path + "." + comic.images[0].extension)
                        }
                    }
                }
            }
            //handle failed response
        }, onFailure = {
            comicName.value = "No Comic Found, please check internet connection"
            comicDescription.value = "${it.message}"
            comicImageUrl.value = "No Comic Found"
        })
    }


    @Preview(showBackground = true)
    @Composable
    fun ComicPreview() {
        MarvelCharacterCatalogTheme {
            ComicElements()
        }
    }

}



