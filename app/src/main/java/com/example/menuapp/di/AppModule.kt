package com.example.menuapp.di

import android.content.Context
import com.example.menuapp.util.loadJSONFromAssets
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import org.json.JSONObject
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Named("Restra")
    fun getRestaurantJson(
        @ApplicationContext context: Context
    ): JSONObject {
        return JSONObject(context.loadJSONFromAssets("restra.json"))
    }

    @Provides
    @Named("Menu")
    fun getMenuJson(
        @ApplicationContext context: Context
    ): JSONObject {
        return JSONObject(context.loadJSONFromAssets("menu.json"))
    }

}
