package com.example.myviewpager

import android.app.Application

class MyViewpager : Application() {
    override fun onCreate() {
        super.onCreate()
        VolleyService.initialize(this)
    }
}