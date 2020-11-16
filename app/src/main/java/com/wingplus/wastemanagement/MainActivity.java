package com.wingplus.wastemanagement;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.View.OnClickListener;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.example.tscdll.TSCActivity;

public class MainActivity extends AppCompatActivity {


    TSCActivity TscDll = new TSCActivity();

    private Button test;

    String barcode ="8526331524";
    String name ="Wingplus Solution";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        btntestPrint();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void  btntestPrint() {
        test = (Button) findViewById(R.id.button1);

        test.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                TscDll.openport("00:19:0E:A3:C6:DB");

                //String status = TscDll.printerstatus(300);

                TscDll.sendcommand("SIZE 75 mm, 50 mm\r\n");
                //TscDll.sendcommand("GAP 2 mm, 0 mm\r\n");//Gap media
                //TscDll.sendcommand("BLINE 2 mm, 0 mm\r\n");//blackmark media
                TscDll.clearbuffer();
                TscDll.sendcommand("SPEED 4\r\n");
                TscDll.sendcommand("DENSITY 12\r\n");
                TscDll.sendcommand("CODEPAGE UTF-8\r\n");
                TscDll.sendcommand("SET TEAR ON\r\n");
                TscDll.sendcommand("SET COUNTER @1 1\r\n");
                TscDll.sendcommand("@1 = \"0001\"\r\n");
                TscDll.sendcommand("TEXT 100,300,\"ROMAN.TTF\",0,12,12,@1\r\n");
                TscDll.sendcommand("TEXT 100,400,\"ROMAN.TTF\",0,12,12,\"TEST FONT\"\r\n");
                TscDll.barcode(100, 100, "128", 100, 1, 0, 3, 3, barcode);
                TscDll.printerfont(100, 250, "3", 0, 1, 1, name);
                TscDll.printlabel(1, 1);
                TscDll.closeport(1000);
            }
        });
    }
}