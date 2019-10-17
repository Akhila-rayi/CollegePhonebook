package com.coign.collegephonebook

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        btn_add.setOnClickListener {

            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }
        btn_view.setOnClickListener {

            var it = Intent(this, ViewlistActivity::class.java)
            it.putExtra("navgtn", "fromview")
            startActivity(it)
            finish()
        }



        btn_search.setOnClickListener {

            var searchstr = et_searchby.text.toString()
            if (searchstr.isEmpty()) {
                Toast.makeText(applicationContext, "enter name to search", Toast.LENGTH_SHORT).show()

            } else {

                var it = Intent(this, ViewlistActivity::class.java)
                it.putExtra("navgtn", "fromsearch")
                it.putExtra("name", searchstr)
                startActivity(it)
                finish()
            }

        }
        btn_quit.setOnClickListener {

            moveTaskToBack(true)
        }

    }
}
