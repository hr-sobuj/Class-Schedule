package com.classschedule.classtime;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Process;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ArchiveClass extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    EditText datepicker;
    Button btnsearch, btntotalclass;
    private DrawerLayout mNavDrawer;
    Button btnaYes, btnaNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_archive_class);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mNavDrawer = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.navigation_view);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(

                this, mNavDrawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        );
        mNavDrawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);


        datepicker = findViewById(R.id.datepicker);
        btnsearch = findViewById(R.id.classsearch);
        btntotalclass = findViewById(R.id.totalclass);

        ///date picker

        final Calendar myCalendar = Calendar.getInstance();

        EditText datepicker_edittext = (EditText) findViewById(R.id.datepicker);
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
                String search_key = datepicker.getText().toString();

                if (search_key.isEmpty()) {
                    Toast.makeText(ArchiveClass.this, "Please select a date", Toast.LENGTH_LONG).show();
                } else {
//                String search_key=datepicker.getText().toString();
                    String[] keyTotalClass = search_key.split("/");
                    String keyTotal = keyTotalClass[0] + keyTotalClass[1] + keyTotalClass[2];

                    Intent intent = new Intent(ArchiveClass.this, ClassSearchingResult.class);
                    intent.putExtra("date", search_key);
                    intent.putExtra("key", keyTotal);
                    startActivity(intent);

                }


//                Toast.makeText(ArchiveClass.this,keyTotal,Toast.LENGTH_LONG).show();
            }
        });


//else{
//    Toast.makeText(ArchiveClass.this,"Please Select a date",Toast.LENGTH_LONG).show();
//}


        btntotalclass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ArchiveClass.this,TotalClass.class);
                startActivity(intent);
            }
        });

    }

        @Override
        public boolean onCreateOptionsMenu (Menu menu){

            MenuInflater menuInflater = getMenuInflater();
            menuInflater.inflate(R.menu.back_layout, menu);

            return super.onCreateOptionsMenu(menu);
        }

        @Override
        public boolean onOptionsItemSelected (@NonNull MenuItem item){

            if (item.getItemId() == R.id.backId) {
                Intent intent = new Intent(ArchiveClass.this, new_app_menu.class);
                startActivity(intent);

                finish();
            }

            return super.onOptionsItemSelected(item);
        }

        @Override
        public void onBackPressed () {

            Intent intent = new Intent(ArchiveClass.this, new_app_menu.class);
            startActivity(intent);
            finish();

        }

        @Override
        public boolean onNavigationItemSelected (@NonNull MenuItem item){
            switch (item.getItemId()) {
                case R.id.nvclassschedulehome:


                    Intent intent = new Intent(ArchiveClass.this, new_app_menu.class);
                    startActivity(intent);

                    mNavDrawer.closeDrawer(GravityCompat.START);
                    break;

                case R.id.nvtodayclassschedules:


                    Intent intent1 = new Intent(ArchiveClass.this, classactivity.class);
                    startActivity(intent1);

                    finish();

                    mNavDrawer.closeDrawer(GravityCompat.START);
                    break;

                case R.id.nvnoticeboardid:


                    Intent intent2 = new Intent(ArchiveClass.this, notice_display_activity.class);
                    startActivity(intent2);
                    mNavDrawer.closeDrawer(GravityCompat.START);
                    finish();

                    break;

                case R.id.nvcurriculamid:
                    Intent intent3 = new Intent(ArchiveClass.this, curriculamactivity.class);
                    startActivity(intent3);
                    mNavDrawer.closeDrawer(GravityCompat.START);

                    finish();
                    break;

                case R.id.nvbusscheduleid:
                    Intent intent4 = new Intent(ArchiveClass.this, busschedulepdf.class);
                    startActivity(intent4);
                    mNavDrawer.closeDrawer(GravityCompat.START);
                    finish();
                    break;

                case R.id.nvfacebookgrpid:
                    Intent intent5 = new Intent(ArchiveClass.this, facebook_group.class);
                    startActivity(intent5);
                    mNavDrawer.closeDrawer(GravityCompat.START);
                    finish();

                    break;
                case R.id.nvsettimeid:


                    Intent intent6 = new Intent(ArchiveClass.this, settimeactivity.class);
                    startActivity(intent6);

                    finish();


                    break;

                case R.id.nvrating:
                    Intent intent7 = new Intent();
                    intent7.setAction(Intent.ACTION_VIEW);
                    intent7.addCategory(Intent.CATEGORY_BROWSABLE);
                    intent7.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.classschedule.classtime"));
                    startActivity(intent7);
                    mNavDrawer.closeDrawer(GravityCompat.START);
                    finish();
                    break;

                case R.id.nvshareid:
                    Intent intent8 = new Intent(Intent.ACTION_SEND);
                    intent8.setType("text/plain");
                    String body = "Class Schedule App for Computer Science and Engineering Department,HSTU." +
                            " \nPlay Store Link : https://play.google.com/store/apps/details?id=com.classschedule.classtime";
                    String sub = "Class Schedule";
                    intent8.putExtra(Intent.EXTRA_SUBJECT, sub);
                    intent8.putExtra(Intent.EXTRA_TEXT, body);
                    startActivity(Intent.createChooser(intent8, "Share Using"));
                    mNavDrawer.closeDrawer(GravityCompat.START);
                    finish();
                    break;
                case R.id.nvdeveloperid:
                    Intent intent9 = new Intent(ArchiveClass.this, developer.class);
                    startActivity(intent9);
                    mNavDrawer.closeDrawer(GravityCompat.START);
                    finish();
                    break;


                case R.id.nvaboutthisappid:

                    try {

                        Intent intent10 = new Intent(ArchiveClass.this, ArchiveClass.class);
                        startActivity(intent10);

                    } catch (Exception e) {
                        Toast.makeText(ArchiveClass.this, "Exception" + e, Toast.LENGTH_LONG).show();
                    }

                    mNavDrawer.closeDrawer(GravityCompat.START);
                    finish();
                    break;
                case R.id.nvexitic:
                    showdialog();
                    mNavDrawer.closeDrawer(GravityCompat.START);
                    break;

            }
            return true;
        }
        private void showdialog () {

            LayoutInflater layoutInflater = LayoutInflater.from(this);
            View view = layoutInflater.inflate(R.layout.alertdialog_layout, null);

            btnaYes = (Button) view.findViewById(R.id.aYes);
            btnaNo = (Button) view.findViewById(R.id.aNo);

            btnaYes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    moveTaskToBack(true);
                    Process.killProcess(Process.myPid());
                    System.exit(1);
                    /*Toast.makeText(MainActivity.this,"Yes",Toast.LENGTH_LONG).show();*/
                    finish();

                }
            });


            final AlertDialog alertDialog = new AlertDialog.Builder(this).setView(view)
                    .create();

            alertDialog.show();

            btnaNo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    alertDialog.dismiss();
                }
            });

        }

    
}
