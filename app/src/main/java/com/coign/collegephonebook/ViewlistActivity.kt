package com.coign.collegephonebook

import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_viewlist.*

class ViewlistActivity : AppCompatActivity() {

    var sqlitedb: SQLiteDatabase? = null
    var  typeal=ArrayList<String>()
    var  branchal=ArrayList<String>()
    var  listofitemsal=ArrayList<String>()
    var stype=""
    var sbranch=""
    var strname=""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_viewlist)
        sqlitedb=openOrCreateDatabase("localdb", Context.MODE_PRIVATE,null)
        if(intent.getStringExtra("navgtn").equals("fromsearch"))
        {
            sp_branch.visibility=View.GONE
            sp_type.visibility=View.GONE
            strname=intent.getStringExtra("name")
            callsearchapi(strname)
        }
        else{

            sp_branch.visibility=View.VISIBLE
            sp_type.visibility=View.VISIBLE
        }


        typeal.clear()
        typeal.add("Select Type")
        typeal.add("Teaching")
        typeal.add("NonTeaching")
        typeal.add("Student")

        branchal.clear()
        branchal.add("Select Branch")
        branchal.add("CSE")
        branchal.add("ECE")
        branchal.add("EEE")
        branchal.add("MECH")
        branchal.add("others")


        val typearyadptr= ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,typeal)
        sp_type.adapter=typearyadptr

        val brncharyadptr= ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,branchal)
        sp_branch.adapter=brncharyadptr

        sp_type.onItemSelectedListener=object: AdapterView.OnItemSelectedListener{


            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

                stype=sp_type.selectedItem.toString()
                if(stype.equals("others")){

                    sp_branch.visibility= View.GONE
                    sbranch=""
                    callapi(stype,sbranch)

                }else{

                    sp_branch.visibility= View.VISIBLE
                    callapi(stype,sbranch)
                }


            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        }
        sp_branch.onItemSelectedListener=object: AdapterView.OnItemSelectedListener{


            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

                sbranch=sp_branch.selectedItem.toString()
                callapi(stype,sbranch)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

            }
        }


        lv.setOnItemClickListener { parent, view, position, id ->

            var selectdnm=lv.getItemAtPosition(position).toString()
            var  it= Intent(this,ViewdetailsActivity::class.java)
            it.putExtra("usersname",selectdnm)
            startActivity(it)
            finish()
           // Toast.makeText(applicationContext,selectdnm,Toast.LENGTH_SHORT).show()

        }

    }

    private fun callsearchapi(strname: String) {

        listofitemsal.clear()
        val c= sqlitedb!!.rawQuery("select * from Users where NAME ='%$strname%'",null)
        c.moveToFirst()

        if(c.count>0)
        {
            do {

                var indexx=c.getColumnIndex("NAME")
                var name=c.getString(indexx)
                listofitemsal.add(name)

            }while (c.moveToNext())


        }else
        {
            listofitemsal.add("No data found")
        }
        c.close()
        val listaryadptr= ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,listofitemsal)
        lv.adapter=listaryadptr
    }

    fun callapi(stype: String, sbranch: String){

        listofitemsal.clear()
        var qury:String

        if(sbranch.isEmpty()){

          qury= "select * from Users where TYPE ='"+stype+"' "
        }
        else{

           qury="select * from Users where TYPE ='"+stype+"' and BRANCH='"+sbranch+"' "

        }

        var c= sqlitedb!!.rawQuery(qury,null)
        c.moveToFirst()

        if(c.count>0)
        {
            do {

                var indexx=c.getColumnIndex("NAME")
                var name=c.getString(indexx)
                listofitemsal.add(name)


            }while (c.moveToNext())

        }else
        {
            listofitemsal.add("No data found")
        }
        c.close()
        val listaryadptr= ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,listofitemsal)
        lv.adapter=listaryadptr

    }
}
