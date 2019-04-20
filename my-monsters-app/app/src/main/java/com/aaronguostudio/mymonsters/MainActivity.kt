package com.aaronguostudio.mymonsters

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val IMAGES = arrayOf(
            R.drawable.monster01, R.drawable.monster02, R.drawable.monster03, R.drawable.monster04, R.drawable.monster05,
            R.drawable.monster06, R.drawable.monster07, R.drawable.monster08, R.drawable.monster09, R.drawable.monster10,
            R.drawable.monster11, R.drawable.monster12
        )

    val NAMES = arrayOf(
        "Monster A", "Monster B", "Monster C", "Monster D", "Monster E",
        "Monster F", "Monster G", "Monster H", "Monster I", "Monster J",
        "Monster K", "Monster L", "Monster M", "Monster N", "Monster O",
        "Monster P", "Monster Q", "Monster R", "Monster S", "Monster T"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }


        val adapter = ArrayAdapter(this, R.layout.listview_item, NAMES)

        val listView: ListView = findViewById(R.id.listView)
        listView.adapter = adapter
        listView.onItemClickListener = object : AdapterView.OnItemClickListener {
            override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val itemValue = listView.getItemAtPosition(position) as String
                Toast.makeText(applicationContext, "Position: $position, value: $itemValue", Toast.LENGTH_LONG).show()
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
