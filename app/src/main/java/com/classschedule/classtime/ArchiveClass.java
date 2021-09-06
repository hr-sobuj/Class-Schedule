package com.classschedule.classtime;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ArchiveClass extends AppCompatActivity {

    EditText datepicker;
    Button btnsearch,btntotalclass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_archive_class);


        datepicker=findViewById(R.id.datepicker);
        btnsearch=findViewById(R.id.classsearch);
        btntotalclass=findViewById(R.id.totalclass);

        ///date picker

        final Calendar myCalendar = Calendar.getInstance();

        EditText datepicker_edittext= (EditText) findViewById(R.id.datepicker);
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "dd/MM/yy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                datepicker.setText(sdf.format(myCalendar.getTime()));
            }

        };

        datepicker_edittext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(ArchiveClass.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    btnsearch.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String search_key=datepicker.getText().toString();

            if(search_key.isEmpty()){
                Toast.makeText(ArchiveClass.this,"Please select a date",Toast.LENGTH_LONG).show();
            }
            else{
//                String search_key=datepicker.getText().toString();
                String[] keyTotalClass=search_key.split("/");
                String keyTotal=keyTotalClass[0]+keyTotalClass[1]+keyTotalClass[2];

                Intent intent=new Intent(ArchiveClass.this, ClassSearchingResult.class);
                intent.putExtra("date",search_key);
                intent.putExtra("key",keyTotal);
                startActivity(intent);

            }


//                Toast.makeText(ArchiveClass.this,keyTotal,Toast.LENGTH_LONG).show();
        }
    });



//else{
//    Toast.makeText(ArchiveClass.this,"Please Select a date",Toast.LENGTH_LONG).show();
//}

    }
}