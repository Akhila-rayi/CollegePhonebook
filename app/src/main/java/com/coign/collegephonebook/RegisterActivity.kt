package com.coign.collegephonebook

import android.database.sqlite.SQLiteDatabase
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_register.*
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.view.inputmethod.InputMethodManager
import android.widget.*
import kotlinx.android.synthetic.main.activity_viewlist.*
import java.lang.StringBuilder
import java.util.*
import java.util.regex.Pattern
import kotlin.collections.ArrayList


class RegisterActivity : AppCompatActivity(), View.OnClickListener {


    var sqlitedb: SQLiteDatabase? = null
    var sname = ""
    var smobile = ""
    var semail = ""
    var sgender = ""
    var saddrs = ""
    var sdob = ""
    var sqlftctn = ""
    var stype = ""
    var sbranch = ""
    var qlfctnal = ArrayList<String>()
    var typeal = ArrayList<String>()
    var branchal = ArrayList<String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        sqlitedb = openOrCreateDatabase("localdb", Context.MODE_PRIVATE, null)
        sqlitedb!!.execSQL("create table if not exists Users(NAME varchar(50),MOBILE varchar(10),EMAIL varchar(50),ADDRESS varchar(50),GENDER varchar(50),DATEOFBIRTH varchar(50),TYPE varchar(50),QUALIFICATION varchar(50),BRANCH varchar(15))")


        var view = currentFocus
        if (view == null) {
            view = View(this)
        }
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)


        qlfctnal.clear()
        qlfctnal.add("--SELECT--")
        qlfctnal.add("B.Tech")
        qlfctnal.add("M.Tech")
        qlfctnal.add("others")

        typeal.clear()
        typeal.add("--SELECT--")
        typeal.add("Teaching")
        typeal.add("NonTeaching")
        typeal.add("Student")

        branchal.clear()
        branchal.add("--SELECT--")
        branchal.add("CSE")
        branchal.add("ECE")
        branchal.add("EEE")
        branchal.add("MECH")
        branchal.add("others")

        radioButton1.setOnClickListener(this)
        radioButton2.setOnClickListener(this)
        btn_registr.setOnClickListener(this)
        et_dob_signup.setOnClickListener(this)

        val qlfctnaryadptr = ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, qlfctnal)
        sp_qlfctn_signup.adapter = qlfctnaryadptr

        val typearyadptr = ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, typeal)
        sp_type_signup.adapter = typearyadptr

        val brncharyadptr = ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, branchal)
        sp_branch_signup.adapter = brncharyadptr

        sp_qlfctn_signup.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {


            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

                sqlftctn = sp_qlfctn_signup.selectedItem.toString()
                if (sqlftctn.equals("others")) {

                    sp_branch_signup.visibility = View.GONE
                } else {

                    sp_branch_signup.visibility = View.VISIBLE
                }

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        }
        sp_type_signup.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {


            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

                stype = sp_type_signup.selectedItem.toString()

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

            }

        }
        sp_branch_signup.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {


            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

                sbranch = sp_branch_signup.selectedItem.toString()

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

            }

        }


    }

    override fun onClick(v: View) {

        when (v) {

            radioButton1 -> {
                sgender = "Male"
            }
            radioButton2 -> {
                sgender = "Female"
            }
            et_dob_signup -> {


                val cal = Calendar.getInstance()
                val curntday = cal.get(Calendar.DAY_OF_MONTH)
                val curntmonth = cal.get(Calendar.MONTH)
                val curntyear = cal.get(Calendar.YEAR)
                val datepickr: DatePickerDialog

                datepickr = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->

                    var sb = ""
                    var apndday = ""
                    var apndmonth = ""

                    if (month < 10) {

                        apndmonth = "0$month"

                    } else {
                        apndmonth = (month + 1).toString()
                    }

                    if (dayOfMonth < 10) {

                        apndday = "0$dayOfMonth"
                    }

                    sb = "$apndday-$apndmonth-$year"

                    et_dob_signup.setText(sb).toString()


                }, curntyear, curntmonth, curntday)

                datepickr.show()
            }
            btn_registr -> {

                sname = et_nm_signup.text.toString()
                smobile = et_mobile_signup.text.toString()
                semail = et_email_signup.text.toString()
                saddrs = et_address_signup.text.toString()
                sdob = et_dob_signup.text.toString()


                val mobpatrn = "[6-9]{1}[0-9]{9}"
                val emailpatrn = "^[(a-zA-Z-0-9-\\-\\_\\.\\^\\&\\*\\?\\+\\$\\#\\!)]+@[(a-z)]+\\.[(a-z)]{1,10}$"

                val patrmob: Pattern = Pattern.compile(mobpatrn)
                val patremail: Pattern = Pattern.compile(emailpatrn)


                if (sname.isEmpty()) {
                    Toast.makeText(applicationContext, "enter your name", Toast.LENGTH_SHORT).show()

                } else if (smobile.isEmpty()) {
                    Toast.makeText(applicationContext, "enter your mobile", Toast.LENGTH_SHORT).show()

                } else if (!patrmob.matcher(smobile).matches()) {
                    Toast.makeText(applicationContext, "enter valid mobile number", Toast.LENGTH_SHORT).show()

                } else if (semail.isEmpty()) {
                    Toast.makeText(applicationContext, "enter your email", Toast.LENGTH_SHORT).show()

                } else if (!patremail.matcher(semail).matches()) {
                    Toast.makeText(applicationContext, "enter valid email address", Toast.LENGTH_SHORT).show()

                } else if (saddrs.isEmpty()) {
                    Toast.makeText(applicationContext, "enter your address", Toast.LENGTH_SHORT).show()

                } else if (sgender.isEmpty()) {
                    Toast.makeText(applicationContext, "please select gender", Toast.LENGTH_SHORT).show()

                } else if (sdob.isEmpty()) {

                    Toast.makeText(applicationContext, "please select your Dateofbirth", Toast.LENGTH_SHORT).show()

                } else if (sqlftctn.equals("--SELECT--")) {

                    Toast.makeText(applicationContext, "please select Qualification", Toast.LENGTH_SHORT).show()

                } else if (stype.equals("--SELECT--")) {

                    Toast.makeText(applicationContext, "please select Type", Toast.LENGTH_SHORT).show()

                } else if (sbranch.equals("--SELECT--")) {
                    
                    Toast.makeText(applicationContext, "please select branch", Toast.LENGTH_SHORT).show()

                } else {

                    sqlitedb?.execSQL("insert into Users values('" + sname + "','" + smobile + "','" + semail + "','" + saddrs + "','" + sgender + "','" + sdob + "','" + stype + "','" + sqlftctn + "','" + sbranch + "')")
                    Toast.makeText(applicationContext, "Registered", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, HomeActivity::class.java))
                    finish()

                }


            }


        }

    }


}
