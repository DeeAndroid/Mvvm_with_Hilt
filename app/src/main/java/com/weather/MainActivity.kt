package com.weather

import android.animation.ObjectAnimator
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.TranslateAnimation
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.weather.adapter.ForecastAdapter
import com.weather.data.viewmodel.MainViewModel
import com.weather.databinding.ActivityMainBinding
import com.weather.di.utility.Resource
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewmodels: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        callApi()
        getForecastResponce()
        getWeatherResponce()
        binding.apply {
            btnRetry.setOnClickListener {
                callApi()
            }
        }
    }

    private fun callApi() {
        viewmodels.getForecast(
            resources.getString(R.string.city_name),
            resources.getString(R.string.app_id)
        )
        viewmodels.getTemprature(
            resources.getString(R.string.city_name),
            resources.getString(R.string.app_id)
        )
    }

    private fun getForecastResponce() {
        lifecycleScope.launchWhenCreated {
            viewmodels.getForecast.collect {
                when (it) {
                    is Resource.Failure -> {
                        binding.isLoading = false
                        binding.isError = true
                    }
                    Resource.Loading -> {
                        animateSync()
                        binding.isLoading = true
                        binding.isError = false
                    }
                    is Resource.Success -> {
                        binding.isLoading = false
                        binding.isError = false
                        val fourDayList = it.value.list?.take(4)
                        val adapter = ForecastAdapter()
                        binding.rvForecast.adapter = adapter
                        Handler(Looper.getMainLooper()).postDelayed({
                            binding.rvForecast.slideUp(1000)
                        }, 50)
                        fourDayList?.let { it1 -> adapter.setModelArrayList(it1) }

                    }
                }
            }
        }
    }

    private fun getWeatherResponce() {
        lifecycleScope.launchWhenCreated {
            viewmodels.getTempData.collect {
                when (it) {
                    is Resource.Failure -> {
                        binding.isLoading = false
                        binding.isError = true
                    }
                    Resource.Loading -> {
                        animateSync()
                        binding.isLoading = true
                        binding.isError = false
                    }
                    is Resource.Success -> {
                        binding.isLoading = false
                        binding.isError = false
                        binding.cityData = it.value
                        binding.cityTemp = it.value.main?.temp?.minus(273.15)?.toInt().toString()

                    }
                }
            }
        }
    }

    fun View.slideUp(duration: Int = 500) {
        visibility = View.VISIBLE
        val animate = TranslateAnimation(0f, 0f, this.height.toFloat(), 0f)
        animate.duration = duration.toLong()
        animate.fillAfter = true
        this.startAnimation(animate)
    }

    private fun animateSync() {
        val imageViewObjectAnimator = ObjectAnimator.ofFloat(
            binding.ivSync,
            "rotation", 0f, 360f
        )
        imageViewObjectAnimator.repeatCount = ObjectAnimator.INFINITE
        imageViewObjectAnimator.repeatMode = ObjectAnimator.RESTART
        imageViewObjectAnimator.interpolator = AccelerateInterpolator()
        imageViewObjectAnimator.start()
        if (binding.isLoading == false) {
            binding.ivSync.visibility = View.GONE
        }
    }
}