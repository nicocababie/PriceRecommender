package com.example.pricerecommender.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext

val Context.userDataStore: DataStore<Preferences> by preferencesDataStore(
    name = "price_recommender_datastore"
)

@Module
@InstallIn(ViewModelComponent::class)
object DatastoreModule {
    @Provides
    fun provideDatastore(
        @ApplicationContext context: Context
    ): DataStore<Preferences> {
        return context.userDataStore
    }
}