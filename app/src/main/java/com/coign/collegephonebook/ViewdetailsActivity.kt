package com.coign.collegephonebook

import android.app.DatePickerDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.telephony.SmsManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_viewdetails.*
import java.util.*
import java.util.regex.Pattern
import kotlin.collections.ArrayList
import android.widget.LinearLayout
import android.widget.EditText



class ViewdetailsActivity : AppCompatActivity(),View.OnClickListener {

    var sqlitedb: SQLiteDatabase? = null
    var strname=""

    var qlfctnaryadptr:ArrayAdapter<String>?=null
    var typearyadptr:ArrayAdapter<String>?=null
    var brncharyadptr:ArrayAdapter<String>?=null

    var sname= ""
    var smobile=""
    var semail=""
    var sgender=""
    var saddrs=""
    var sdob=""
    var sqlftctn=""
    var stype=""
    var sbranch=""
    var  qlfctnal=ArrayList<String>()
    var  typeal=ArrayList<String>()
    var  branchal=ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_viewdetails)
        sqlitedb=openOrCreateDatabase("localdb", Context.MODE_PRIVATE,null)
        strname=  intent.getStringExtra("usersname")

        var view = currentFocus
        if (view == null) {
            view = View(this)
        }
        val imm =getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
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

        radioButton1_profl.setOnClickListener(this)
        radioButton2_profl.setOnClickListener(this)
        btn_registr_profl.setOnClickListener(this)
        et_dob_profl.setOnClickListener(this)

        qlfctnaryadptr= ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,qlfctnal)
        sp_qlfctn_profl.adapter=qlfctnaryadptr

        typearyadptr= ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,typeal)
        sp_type_profl.adapter=typearyadptr

        brncharyadptr= ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,branchal)
        sp_branch_profl.adapter=brncharyadptr

        sp_qlfctn_profl.onItemSelectedListener=object: AdapterView.OnItemSelectedListener{


            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

                sqlftctn=sp_qlfctn_profl.selectedItem.toString()
                if(sqlftctn.equals("others")){

                    sp_branch_profl.visibility= View.GONE
                }else{

                    sp_branch_profl.visibility= View.VISIBLE
                }

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        }
        sp_type_profl.onItemSelectedListener=object: AdapterView.OnItemSelectedListener{


            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

                stype=sp_type_profl.selectedItem.toString()

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

            }

        }
        sp_branch_profl.onItemSelectedListener=object: AdapterView.OnItemSelectedListener{


            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

                sbranch=sp_branch_profl.selectedItem.toString()

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

            }

        }

        calldetailsapi(strname)


    }

    private fun calldetailsapi(strname: String) {

        val c= sqlitedb!!.rawQuery("select * from Users where NAME ='"+strname+"'",null)

        c.moveToFirst()
        if(c.count>0)
        {

                var i = c.getColumnIndex("NAME")
                sname = c.getString(i)
                et_nm_profl.setText(sname).toString()

                var j = c.getColumnIndex("MOBILE")
                smobile = c.getString(j)
                et_mobile_profl.setText(smobile).toString()

                var k = c.getColumnIndex("EMAIL")
                semail = c.getString(k)
                et_email_profl.setText(semail).toString()

                var l = c.getColumnIndex("ADDRESS")
                saddrs = c.getString(l)
                et_address_profl.setText(saddrs).toString()

                var m = c.getColumnIndex("GENDER")
                sgender = c.getString(m)

                if (sgender.equals("Male")) {

                    radioButton1_profl.isChecked = true
                    radioButton2_profl.isChecked = false

                } else {

                    radioButton2_profl.isChecked = true
                    radioButton1_profl.isChecked = false
                }

                var n = c.getColumnIndex("DATEOFBIRTH")
                sdob = c.getString(n)
                et_dob_profl.setText(sdob).toString()



                var o = c.getColumnIndex("TYPE")
                stype = c.getString(o)
               sp_type_profl.setSelection(typearyadptr!!.getPosition(stype))



                var p = c.getColumnIndex("QUALIFICATION")
                sqlftctn = c.getString(p)
                sp_qlfctn_profl.setSelection(qlfctnaryadptr!!.getPosition(sqlftctn))


                var q = c.getColumnIndex("BRANCH")
                sbranch = c.getString(q)
                sp_branch_profl.setSelection(brncharyadptr!!.getPosition(sbranch))


                System.out.println("@@@@@@@@@@@@"+stype+sqlftctn+sbranch)


        }else
        {
            Toast.makeText(applicationContext,"no data found", Toast.LENGTH_SHORT).show()

        }
        c.close()



    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {


        menuInflater.inflate(R.menu.mymenu,menu)
        return super.onCreateOptionsMenu(menu)

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        if(item!!.title=="call"){

            var i=Intent(Intent.ACTION_CALL,Uri.parse("tel:"+smobile));
            startActivity(i);
            Toast.makeText(getApplicationContext(), "call connecting",Toast.LENGTH_SHORT).show();

        }else  if(item.title=="email"){

            val i = Intent(Intent.ACTION_SEND)
            i.putExtra(Intent.EXTRA_EMAIL, arrayOf<String>(semail))
            i.putExtra(Intent.EXTRA_SUBJECT, "")
            i.putExtra(Intent.EXTRA_TEXT, "")
            i.type = "text/rfc822"
            startActivity(Intent.createChooser(i, "choose email id"))
            Toast.makeText(applicationContext, "emailsending", Toast.LENGTH_SHORT).show()


        }else{
            var alrtdlg=AlertDialog.Builder(this)
            alrtdlg.setTitle("Enter your message")
            val input = EditText(this)
            val lp = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
            input.layoutParams = lp
            alrtdlg.setView(input)
            alrtdlg.setCancelable(false)
            alrtdlg.setPositiveButton("Send",DialogInterface.OnClickListener { dialog, which ->

                var mesg=input.text
                if(mesg.isEmpty()|| mesg.isBlank())
                {
                    var smsmngr=SmsManager.getDefault()
                    smsmngr.sendTextMessage(smobile,null,"", null,null)
                    Toast.makeText(applicationContext,"message sent", Toast.LENGTH_SHORT).show()

                    dialog.dismiss()
                }

            })

            alrtdlg.setNegativeButton("Cancel",DialogInterface.OnClickListener { dialog, which ->

                dialog.dismiss()

            })
            alrtdlg.create()
            alrtdlg.show()

        }

        return super.onOptionsItemSelected(item)

    }



    override fun onClick(v: View) {

        when(v) {

            radioButton1_profl-> {sgender="Male"}
            radioButton2_profl->{sgender="Female"}
            et_dob_profl-> {


                val cal = Calendar.getInstance()
                val curntday = cal.get(Calendar.DAY_OF_MONTH)
                val curntmonth = cal.get(Calendar.MONTH)
                val curntyear = cal.get(Calendar.YEAR)
                val datepickr: DatePickerDialog

                datepickr= DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->

                    var sb=""
                    var apndday=""
                    var apndmonth=""

                    if(month<10){

                        apndmonth= "0$month"

                    }else{
                        apndmonth=(month+1).toString()
                    }

                    if(dayOfMonth<10){

                        apndday="0$dayOfMonth"
                    }

                    sb="$apndday-$apndmonth-$year"

                    et_dob_profl.setText(sb).toString()


                },curntyear,curntmonth,curntday)

                datepickr.show()
            }
            btn_back_profl->{

                finish()
            }
            btn_registr_profl-> {

                sname=et_nm_profl.text.toString()
                smobile=et_mobile_profl.text.toString()
                semail=et_email_profl.text.toString()
                saddrs=et_address_profl.text.toString()
                sdob=et_dob_profl.text.toString()


                val mobpatrn = "[6-9]{1}[0-9]{9}"
                val emailpatrn = "^[(a-zA-Z-0-9-\\-\\_\\.\\^\\&\\*\\?\\+\\$\\#\\!)]+@[(a-z)]+\\.[(a-z)]{1,10}$"
                // val passwrdvaldtn = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%_^&+=])(?=\\S+$).{8,}$"

                var patrmob: Pattern = Pattern.compile(mobpatrn)
                var patremail: Pattern = Pattern.compile(emailpatrn)


                if(sname.isEmpty())
                {
                    Toast.makeText(applicationContext,"enter your name",Toast.LENGTH_SHORT).show()

                }else if(smobile.isEmpty())
                {
                    Toast.makeText(applicationContext,"enter your mobile",Toast.LENGTH_SHORT).show()

                }else if(!patrmob.matcher(smobile).matches())
                {
                    Toast.makeText(applicationContext,"enter valid mobile number",Toast.LENGTH_SHORT).show()

                }else if(semail.isEmpty())
                {
                    Toast.makeText(applicationContext,"enter your email",Toast.LENGTH_SHORT).show()

                }else if(!patremail.matcher(semail).matches())
                {
                    Toast.makeText(applicationContext,"enter valid email address",Toast.LENGTH_SHORT).show()

                }else if(saddrs.isEmpty())
                {
                    Toast.makeText(applicationContext,"enter your address",Toast.LENGTH_SHORT).show()

                }else if(sgender.isEmpty())
                {
                    Toast.makeText(applicationContext,"please select gender",Toast.LENGTH_SHORT).show()

                }else if(sdob.isEmpty())
                {
                    Toast.makeText(applicationContext,"please select your Dateofbirth",Toast.LENGTH_SHORT).show()
                }
                else if(sqlftctn.equals("--SELECT--"))
                {
                    Toast.makeText(applicationContext,"please select Qualification",Toast.LENGTH_SHORT).show()
                }
                else if(stype.equals("--SELECT--"))
                {
                    Toast.makeText(applicationContext,"please select Type",Toast.LENGTH_SHORT).show()
                }
                else if(sbranch.equals("--SELECT--"))
                {
                    Toast.makeText(applicationContext,"please select branch",Toast.LENGTH_SHORT).show()

                }else{

                    sqlitedb?.execSQL("update Users set MOBILE='$smobile', EMAIL='$semail',ADDRESS='$saddrs',GENDER='$sgender',DATEOFBIRTH='$sdob',TYPE='$stype',QUALIFICATION='$sqlftctn',BRANCH='$sbranch' where NAME='$strname' ")
                    Toast.makeText(applicationContext,"details updated",Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this,HomeActivity::class.java))
                    finish()

                }


            }



        }

    }
}
