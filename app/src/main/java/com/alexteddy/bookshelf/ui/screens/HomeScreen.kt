package com.alexteddy.bookshelf.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.alexteddy.bookshelf.R
import com.alexteddy.bookshelf.network.Book

@Composable
fun HomeScreen(
    booksUiState: BooksUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    when (booksUiState) {
        is BooksUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is BooksUiState.Success ->
//            ResultScreen(
//                booksUiState.searchResult.items[0].volumeInfo.imageLinks.thumbnail,
//                modifier.padding(top = contentPadding.calculateTopPadding())
//            )
//            ResultScreen(
//                booksUiState.searchResult.items[0].volumeInfo.imageLinks.thumbnail,
//                modifier.padding(top = contentPadding.calculateTopPadding())
//            )
        //ResultScreen(booksUiState.searchResult.items.size.toString(), modifier.padding(top = contentPadding.calculateTopPadding()))
        //ResultScreen("Success:  Amphibian photos retrieved", modifier.padding(top = contentPadding.calculateTopPadding()))
        // "Success: ${mpsUiState.amps.size} Amphibian photos retrieved" ${booksUiState}
        // hien grid chieu doc nhieu buc anh
        PhotosGridScreen(booksUiState.searchResult.items, modifier, contentPadding)
        // hien 1 buc anh
        //MarsPhotoCard(photo = marsUiState.photos, modifier = modifier.fillMaxSize())
        //ThumbnailBookCard(booksUiState.searchResult.items[0], modifier = modifier.fillMaxSize())
        is BooksUiState.Error -> ErrorScreen(retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun PhotosGridScreen(
    books: List<Book>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(150.dp),
        modifier = modifier.padding(horizontal = 4.dp),
        contentPadding = contentPadding,
    ) {
        items(items = books, key = { book -> book.id }) {
            book ->
            ThumbnailBookCard(
                book,
                modifier = modifier
                    .padding(4.dp)
                    .fillMaxWidth()
                    //.aspectRatio(0.8f)
                    // the desired width/height positive ratio
            )
        }
    }
}


@Composable
fun ThumbnailBookCard(book: Book, modifier: Modifier = Modifier) {
    // AsyncImage is a composable that executes an image request asynchronously
    // and renders the result.
    // Co hai dang tai, dung model = ImageRequest.url hoac lay luon model = ImageRequest
    // o day dung dang 2: model = ImageRequest
    // O day, you build an ImageRequest using the image URL (photo.imgSrc)
    // and pass it to the model argument.
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(0.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        AsyncImage(
            model = book.volumeInfo.imageLinks?.let{
                ImageRequest.Builder(context = LocalContext.current)
                    .data(book.volumeInfo.imageLinks.thumbnail?.replace("http:", "https:"))
                    .crossfade(false)
                    .build()
            },
            contentDescription = stringResource(R.string.book_image),
            contentScale = ContentScale.Crop,
            error = painterResource(R.drawable.ic_broken_image),
            placeholder = painterResource(R.drawable.loading_img),
            modifier = Modifier
                .height(220.dp)
                .fillMaxWidth()
        )
    }
}


/**
 * ResultScreen displaying number of photos retrieved.
 */
@Composable
fun ResultScreen(books: String, modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        Text(text = books)
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.loading_img),
        contentDescription = stringResource(R.string.loading)
    )
}

@Composable
fun ErrorScreen(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_connection_error), contentDescription = ""
        )
        Text(text = stringResource(R.string.loading_failed), modifier = Modifier.padding(16.dp))
        Button(onClick = retryAction) {
            Text(stringResource(R.string.retry))
        }
    }
}