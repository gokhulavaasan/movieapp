package com.gvapps.movie.di

import android.app.Application
import androidx.room.Room
import com.gvapps.movie.movieList.data.local.MovieDao
import com.gvapps.movie.movieList.data.local.MovieDatabase
import com.gvapps.movie.movieList.data.remote.MovieApi
import com.gvapps.movie.movieList.data.repository.MovieRepositoryImpl
import com.gvapps.movie.movieList.domain.repository.MovieRepository
import com.gvapps.movie.movieList.domain.usecases.MovieUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

	private val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
		level = HttpLoggingInterceptor.Level.BODY
	}

	private val client: OkHttpClient = OkHttpClient.Builder()
		.addInterceptor(interceptor)
		.build()

	@Provides
	@Singleton
	fun providesMovieApi(): MovieApi {
		return Retrofit.Builder()
			.addConverterFactory(GsonConverterFactory.create())
			.client(client)
			.baseUrl(MovieApi.BASE_URL)
			.build()
			.create(MovieApi::class.java)
	}

	@Provides
	@Singleton
	fun provideMovieDatabase(
		application: Application
	): MovieDatabase {
		return Room.databaseBuilder(
			context = application,
			MovieDatabase::class.java,
			"moviedb.db"
		).build()
	}

	@Provides
	@Singleton
	fun providesMovieDao(
		movieDatabase: MovieDatabase
	): MovieDao {
		return movieDatabase.movieDao
	}

	@Provides
	@Singleton
	fun providesMovieRepository(
		movieDao: MovieDao,
		movieApi: MovieApi
	): MovieRepository = MovieRepositoryImpl(
		movieDao = movieDao, movieApi = movieApi,
	)

	@Provides
	@Singleton
	fun providesMovieUseCases(
		movieRepository: MovieRepository
	): MovieUseCases {
		return MovieUseCases(
			upcomingMovieUseCases = UpcomingMovieUseCases(movieRepository),
			popularMovieUseCases = PopularMovieUseCases(movieRepository),
		)
	}
}