package com.hoamz.toeic.base

import android.app.Activity
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import java.lang.ref.WeakReference

object AddMod {
    private var interstitialAd: InterstitialAd? = null
    private var isLoading : Boolean = false
    private var activityRef: WeakReference<Activity>? = null

    fun initialize(act : Activity){
        activityRef = WeakReference(act)
        MobileAds.initialize(act) {}
        loadAddMod(act)
    }

    fun loadAddMod(context: Context){
        if(isLoading) return//neu dang load thi khong load nua(tranh de task)
        isLoading = true//dang load
        val adRequest = AdRequest.Builder().build()
        InterstitialAd.load(
            context,
            "ca-app-pub-3940256099942544/1033173712",//id ads test
            adRequest,
            object : InterstitialAdLoadCallback(){
                override fun onAdLoaded(p0: InterstitialAd) {
                    super.onAdLoaded(p0)
                    interstitialAd = p0
                    isLoading = false
                }

                override fun onAdFailedToLoad(p0: LoadAdError) {
                    super.onAdFailedToLoad(p0)
                    interstitialAd = null
                    isLoading = false
                }
            }
        )
    }

    fun showAdd(){
        val act = activityRef?.get()
        if(act == null) return
        interstitialAd?.let {
            interstitialAd?.show(act)
            interstitialAd = null//reset
            loadAddMod(act)
        }
    }
}


@Composable
fun BannerAdView(
    modifier: Modifier = Modifier
) {
    AndroidView(
        factory = { context ->
            AdView(context).apply {
                setAdSize(AdSize.BANNER)
                adUnitId = "ca-app-pub-3940256099942544/6300978111" // Banner test ID
                loadAd(AdRequest.Builder().build())
            }
        }
    )
}

