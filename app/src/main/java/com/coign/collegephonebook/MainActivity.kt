package com.coign.collegephonebook

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    var sqlitedb:SQLiteDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sqlitedb=SQLiteDatabase.openOrCreateDatabase("localphonebook",null)
        sqlitedb?.execSQL("create table if not exists Users(NAME varchar(50),DATEOFBIRTH varchar(50),GENDER varchar(50),MOBILE varchar(50),EMAIL varchar(50),QUALIFICATION varchar(50),GUARDIAN varchar(50))")

        btn_signin.setOnClickListener {

            var susrnm=et_usrnm_login.text
            var spswrd=et_pswrd_login.text
            if(susrnm.isNullOrBlank()){

                Toast.makeText(applicationContext,"please enter username",Toast.LENGTH_SHORT).show()

            }else if(spswrd.isNullOrBlank()){

                Toast.makeText(applicationContext,"please enter password",Toast.LENGTH_SHORT).show()

            }else{


               var  c=sqlitedb?.rawQuery("select * from Users where MOBILE=$susrnm and DATEOFBIRTH=$spswrd", null)

                if (c != null) {

                    if(c.count>0){

                        Toast.makeText(applicationContext,"Login successfull",Toast.LENGTH_SHORT).show()

                    }else{

                        Toast.makeText(applicationContext,"please check your credentials",Toast.LENGTH_SHORT).show()

                    }
                }

            }

        } //button click

        signuptitle_login.setOnClickListener {

            startActivity(Intent(this,RegisterActivity::class.java))
        }

    }

}
