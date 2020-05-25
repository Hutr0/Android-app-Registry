package com.example.try2.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.try2.Adapters.SecondFileAdapter;
import com.example.try2.Files.Files;
import com.example.try2.R;
import com.example.try2.Files.voidFiles;

import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity implements View.OnClickListener {

    private static int pos;

    voidFiles vf = new voidFiles(this);
    RecyclerView recyclerView;
    private static ArrayList<Files> files;
    private static ArrayList<Files> files2;
    private static ArrayList<Files> FILES;
    private static boolean flag = false;
    private static boolean mapF = false;
    private static boolean search = false;
    private static boolean MMFlag = false;
    private static boolean MFlag = false;
    private static float zoom ;
    private static double latitude;
    private static double longitude;
    private static float zoom2 ;
    private static double latitude2;
    private static double longitude2;
    private static boolean mapFlag = false;
    private static boolean mapFlag2 = false;
    private static String string;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getSupportActionBar().hide();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        super.onCreate(savedInstanceState);

        if(mapF == true)
        {
            setContentView(R.layout.second_activity_main_map);
        }
        else
        {
            setContentView(R.layout.second_activity_main);
        }

        recyclerView = (RecyclerView)findViewById(R.id.second_recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        if(MMFlag == true)
        {
            FILES = files2;
            MFlag = false;
        }
        else if(search == true)
        {
            vf.putCurStr(string);
            vf.putCur(pos);
            vf.readFileXML(5);
            FILES = vf.getFileXML();
            MFlag = true;
        }
        else
        {
            vf.putCur(pos);
            vf.readFileXML(1);
            FILES = vf.getFileXML();
        }
        if(MFlag == true)
        {
            flag = true;
        }

        Button button = (Button)findViewById(R.id.buttonBack);
        button.setOnClickListener(this);
        Button button2 = (Button)findViewById(R.id.buttonBackMap);
        button2.setOnClickListener(this);

        SecondFileAdapter secondFileAdapter = new SecondFileAdapter(FILES);
        recyclerView.setAdapter(secondFileAdapter);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.buttonBack:

                Intent intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                if(flag == true)
                {
                    MainActivity.give(true, files);
                }
                else
                {
                    MainActivity.give(false, null);
                }
                MMFlag = false;
                MFlag = false;
                mapFlag2 = false;
                mapFlag = false;
                mapF = false;
                flag = false;
                string = "";
                startActivity(intent);
                overridePendingTransition(R.anim.in2, R.anim.out2);
                break;
            case R.id.buttonBackMap:

                if(mapFlag2 == true)
                {
                    MapActivity.putMap(zoom2, latitude2, longitude2);
                    mapFlag2 = false;
                }
                else if(mapFlag == true)
                {
                    MapActivity.putMap(zoom, latitude, longitude);
                    mapFlag = false;
                }

                mapF = false;

                Intent intent2 = new Intent(this, MapActivity.class);
                intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                startActivity(intent2);
                overridePendingTransition(R.anim.in, R.anim.out);
                break;
            default:
                break;
        }
    }

    public static void putFiles(int position) {

        pos = position;
    }

    public static void putSearch(String cur) {

        string = cur;
        search = true;
    }

    public static void sgive(ArrayList al)
    {
        files = al;
        MFlag = true;
    }

    public static void sgive2(ArrayList al)
    {
        files2 = al;
        MMFlag = true;
    }

    public static void putMap(float z, double la, double lo)
    {
        zoom = z;
        latitude = la;
        longitude = lo;
        mapFlag = true;
    }

    public static void putMap2(float z, double la, double lo)
    {
        zoom2 = z;
        latitude2 = la;
        longitude2 = lo;
        mapFlag2 = true;
        mapF = true;
    }
}
