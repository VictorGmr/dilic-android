package com.example.victor.dilic

import android.content.Intent
import android.content.pm.ActivityInfo
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_inicial.*

class Inicial : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val actionBar = supportActionBar

        actionBar!!.hide()

        requestedOrientation =  (ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)

        setContentView(R.layout.activity_inicial)


        button5.setOnClickListener{

            val intent: Intent = Intent(applicationContext, Game::class.java)

            startActivity(intent)

        }

        button8.setOnClickListener{

            val intent: Intent = Intent(applicationContext, CriarAmbiente::class.java)

            startActivity(intent)

        }


    }
}
