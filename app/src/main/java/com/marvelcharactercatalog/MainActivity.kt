package com.marvelcharactercatalog

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.marvelcharactercatalog.ui.theme.ComicModel
import com.marvelcharactercatalog.ui.theme.MarvelCharacterCatalogTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {

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
                    color = MaterialTheme.colorScheme.background
                ) {
                   // Greeting("Android")
                  ComicElements(comicModel =comicModel )
                }
            }
        }
    }

}
//@Composable
//fun ComicDetails(comicId: Int) {
//    var comic by remember { mutableStateOf<Comic?>(null) }
//    var isLoading by remember { mutableStateOf(false) }
//    var error by remember { mutableStateOf(false) }
//
//    // Launch the coroutine when the composable is first composed
//    LaunchedEffect(comicId) {
//        isLoading = true
//        try {
//            // Fetch comic details from the API
//            val fetchedComic = fetchComic(comicId)
//            comic = fetchedComic
//        } catch (e: Exception) {
//            // Handle errors
//            error = true
//        } finally {
//            isLoading = false
//        }
//    }
//
//    // Display loading state, error state, or fetched comic details
//    Column {
//        if (isLoading) {
//            Text(text = "Loading...")
//        } else if (error) {
//            Text(text = "Error occurred while fetching data")
//        } else {
//            comic?.let {
//                ComicItem(comic = it)
//            }
//        }
//    }
//}

@Composable
fun ComicElements(comicModel: ComicModel) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Comic name: " + comicModel.comicName)
        Text(text = "Comic description: " + comicModel.comicDescription)
        Text(text = "Comic cover URL: " + comicModel.coverImageUrl)
    }
}
suspend fun getComicData(id: Int): ComicModel {
    val comicModel = ComicModel("Default value", "Default value", "Default value")
    MarvelApiClient.getComic(
        comicId = id,
        onResponse = { comicResponse ->
            if (comicResponse != null) {
                if(comicResponse.code == 200) {
                    val comic = comicResponse.data.results.firstOrNull()
                    if (comic != null) {
                        comicModel.comicName = comic.title
                        comicModel.comicDescription = comic.description
                        comicModel.coverImageUrl = comic.images[0].path +"."+ comic.images[0].extension
                    }
                }
            }
            // Handle successful response
        },
        onFailure = { error ->
            comicModel.comicName = "No Comic Found"
            comicModel.comicDescription = "No Comic Found"
            comicModel.coverImageUrl = "No Comic Found"
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
