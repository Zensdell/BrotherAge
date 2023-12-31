package com.kkk.brotherage

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
//import com.facebook.ads.AdSettings
import com.google.android.gms.ads.*
//import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback


class FragmentAll() : Fragment() {
    private var mInterstitialAd: InterstitialAd? = null
    private val TAG = "FragmentAll"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // 여기가 메인액티비티에서 온크리에이트랑 비슷한 역할을하는함수야
        // 프래그먼트라 이름이 온크리에이트뷰임
        MobileAds.initialize(requireContext()) {}
        Log.d("애드몹전면광고","로드하나?")


        var adRequest = AdRequest.Builder().build()

        InterstitialAd.load(requireContext(),"ca-app-pub-8246055051187544/1390723898", adRequest, object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                Log.d(TAG, adError.toString())
                mInterstitialAd = null
            }
            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                Log.d(TAG, "Ad was loaded.")
                mInterstitialAd = interstitialAd
            }
        })


        val view: View = inflater.inflate(R.layout.fragment_all, container, false) as ViewGroup
        // view 를 inflate 로 받으면 view 안에 이제 context 가 들어가는것임 ! (원래는 Activity 안에 있던것을  Fragment 로 옮기면서 이렇게 변경됨)

        val recyclerView = view.findViewById<RecyclerView>(R.id.all_rv)
        val rvAdapter = RVAdapter(view.context)
        rvAdapter.datas = datas

        recyclerView.adapter = rvAdapter

        rvAdapter.itemClick=object : RVAdapter.ItemClick{
            override fun onClick(view: View, position: Int) {

                val intent = Intent(view.context, ViewActivity::class.java)
                intent.putExtra("img",datas[position].img)
                intent.putExtra("stageUrl", datas[position].stageUrl)
                intent.putExtra("singerId", datas[position].singerId)
                intent.putExtra("infoUrl",datas[position].infoUrl)
                intent.putExtra("commentCount",datas[position].commentCount)
                intent.putExtra("name",datas[position].name)

                startActivity(intent)

                if (mInterstitialAd != null) {
                    Log.d("애드몹전면광고","보여준다")
                    mInterstitialAd?.show(activity!!)
                } else {
                    //  Toast.makeText(context,"광고 로드 실패", Toast.LENGTH_SHORT).show()//광고를 불러오지 못했을때
                }
            }
        }
        recyclerView.layoutManager = GridLayoutManager(view.context,3)

        return view

    }
}