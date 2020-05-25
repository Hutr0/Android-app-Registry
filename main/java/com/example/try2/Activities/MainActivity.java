package com.example.try2.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.example.try2.Adapters.FileAdapter;
import com.example.try2.Files.Files;
import com.example.try2.R;
import com.example.try2.Files.voidFiles;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    voidFiles vf = new voidFiles(this);
    RecyclerView recyclerView;
    private static String str = null;
    private static boolean status;
    private static ArrayList<Files> files;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getSupportActionBar().hide();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView)findViewById(R.id.recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        if(status != true)
        {
            status = false;
        }

        if(status == false)
        {
            if(str != null)
            {
                vf.putCurStr(str);
                vf.readFileXML(2);
                files = vf.getFileXML();
            }
            else {
                vf.readFileXML(0);
                files = vf.getFileXML();
            }
        }

        final Button button = (Button)findViewById(R.id.searchButton);
        button.setOnClickListener(this);
        Button button1 = (Button)findViewById(R.id.clearButton);
        button1.setOnClickListener(this);
        Button button2 = (Button)findViewById(R.id.creatorButton);
        button2.setOnClickListener(this);
        Button button3 = (Button)findViewById(R.id.projectButton);
        button3.setOnClickListener(this);
        Button button4 = (Button)findViewById(R.id.exitButton);
        button4.setOnClickListener(this);
        EditText editText = (EditText) findViewById(R.id.editText);
        editText.setText(str);
        editText.setOnEditorActionListener(new EditText.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
            {
                if(actionId == EditorInfo.IME_ACTION_DONE) {

                    button.callOnClick();
                    return true;
                }
                return false;
            }
        });
        editText.setSelection(editText.getText().length());

        FileAdapter fileAdapter = new FileAdapter(files, this);
        recyclerView.setAdapter(fileAdapter);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.searchButton:
                final EditText editText = (EditText) findViewById(R.id.editText);
                str = "" + editText.getText();
                status = false;
                SecondActivity.putSearch(str);
                Intent intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                overridePendingTransition(0, 0);
                break;
            case R.id.clearButton:
                str = null;
                status = false;
                Intent intent2 = new Intent(this, MainActivity.class);
                intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent2);
                overridePendingTransition(0, 0);
                break;
            case R.id.creatorButton:
                Intent intent3 = new Intent(this, CreatorActivity.class);
                CreatorActivity.give(files);
                startActivity(intent3);
                overridePendingTransition(R.anim.in3, R.anim.out3);
                break;
            case R.id.projectButton:
                Intent intent4 = new Intent(this, ProjectActivity.class);
                ProjectActivity.give(files);
                startActivity(intent4);
                overridePendingTransition(R.anim.in3, R.anim.out3);
                break;
            case R.id.exitButton:
                Intent intent5 = new Intent(this, MapActivity.class);
                intent5.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent5);
                overridePendingTransition(R.anim.in2, R.anim.out2);
                break;
            default:
                break;
        }
    }

    public static void give(boolean b, ArrayList al)
    {
        status = b;
        files = al;
    }
}
