package com.pieroyendilotto.testyendilotto;

import android.media.Image;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {


    private TextView txtfirstname;
    private TextView txtlastname;
    private TextView txtusername;
    private TextView txtemail;
    private ImageView imglarge;
    public static ArrayList<Person> persons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        getXML();

        //get bundle:
        Integer id = getIntent().getIntExtra("id", 0);

        txtfirstname.setText(persons.get(id).getFirstname());
        txtlastname.setText(persons.get(id).getLastname());
        txtusername.setText(persons.get(id).getUsername());
        txtemail.setText(persons.get(id).getEmail());

        //SETEO EL ICONO EN BASE A LA URL TRAIDA:
        new DownloadImageTask(imglarge).execute(persons.get(id).getLarge());

    }

    public void getXML(){

        txtfirstname = (TextView) findViewById(R.id.txt_firstname);
        txtlastname = (TextView) findViewById(R.id.txt_lastname);
        txtusername = (TextView) findViewById(R.id.txt_username);
        txtemail = (TextView) findViewById(R.id.txt_email);
        imglarge = (ImageView) findViewById(R.id.imglarge);
    }

}
