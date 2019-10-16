package com.coign.collegephonebook

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class Splashscreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)
        Handler().postDelayed(Runnable {

            startActivity(Intent(this,HomeActivity::class.java))
            finish()

        },2000)

    }
}
