package com.example.pricerecommender.ui.screen.bestRoute

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.pricerecommender.ui.ApiUIState
import com.example.pricerecommender.ui.screen.api.ErrorScreen
import com.example.pricerecommender.ui.screen.api.LoadingScreen
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker

@Composable
fun CheckBestRouteScreen(
    bestRouteState: BestRouteUIState,
    cameraPosition: CameraPositionState,
    isMapLoaded: Boolean,
    updateIsMapLoaded: (Boolean) -> Unit,
    updateCameraPosition: (LatLng, Float) -> Unit,
    onRetryClick: () -> Unit
) {
    when (bestRouteState.apiState) {
        is ApiUIState.Loading -> {
            LoadingScreen()
        }
        is ApiUIState.Error -> {
            val errorState = bestRouteState.apiState
            ErrorScreen(
                message = errorState.defaultMessage,
                onRetry = onRetryClick
            )
        }
        is ApiUIState.Success -> {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                GoogleMapView(
                    bestRouteState,
                    modifier = Modifier
                        .fillMaxSize(0.9f)
                        .clip(RoundedCornerShape(12.dp)),
                    cameraPosition = cameraPosition,
                    onMapLoaded = { updateIsMapLoaded(true) },
                    updateCameraPosition
                )
                if (!isMapLoaded) {
                    AnimatedVisibility(
                        visible = !isMapLoaded,
                        enter = EnterTransition.None,
                        exit = fadeOut()
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .wrapContentSize()
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun GoogleMapView(
    bestRouteState: BestRouteUIState,
    modifier: Modifier = Modifier,
    cameraPosition: CameraPositionState,
    onMapLoaded: () -> Unit = {},
    updateCameraPosition: (LatLng, Float) -> Unit,
    content: @Composable () -> Unit = {}
) {
    val details = bestRouteState.details
    GoogleMap(
        modifier = modifier,
        cameraPositionState = cameraPosition,
        onMapLoaded = onMapLoaded,
    ) {
        details.forEach {
            Marker(
                state = it.storeLatLng,
                title = it.storeName
            )
        }
        content()
    }
    LaunchedEffect(cameraPosition.isMoving) {
        if (!cameraPosition.isMoving) {
            val currentPosition = cameraPosition.position.target
            val currentZoom = cameraPosition.position.zoom
            updateCameraPosition(currentPosition, currentZoom)
        }
    }
}