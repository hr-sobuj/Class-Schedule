package com.classschedule.classtime;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Process;
import android.provider.AlarmClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ClassSearchingResult extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

    private Button btnclasstimeback,btnarchive;
    private DrawerLayout mNavDrawer;
    Button btnaYes,btnaNo;

    private Button btnshowalarm1,btnshowalarm2,btnshowalarm3,btnshowalarm4,btnshowalarm5;

    private TextView textViewshowtime1,textViewshowtime2,textViewshowtime3,textViewshowtime4,textViewshowtime5;


    private TextView tshowcoursecode1,tshowcoursecode2,tshowcoursecode3,tshowcoursecode4,tshowcoursecode5;


    private TextView tttdate,tttclassroom;

    DatabaseReference databaseReference,databaseReference1,databaseReference2;
    private ProgressDialog progressDialog;

    private String coursecodes1,coursecodes2,coursecodes3,coursecodes4,coursecodes5;
    private String timeshour1,timeshour2,timeshour3,timeshour4,timeshour5;
    private String timemins1,timemins2,timemins3,timemins4,timemins5;
    private String timeampm1,timeampm2,timeampm3,timeampm4,timeampm5;

    private String todayclassroom,todayclassdate;

    String searchkey,searchdate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_class_searching_result);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            searchkey = extras.getString("key");
            searchdate = extras.getString("date");
            Log.e("key",searchdate);
            //The key argument here must match that used in the other activity
        }

        /*btnarchive=findViewById(R.id.archiveclass);
        btnarchive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ClassSearching.this,ArchiveClass.class);
                startActivity(intent);
            }
        });
*/



        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mNavDrawer=findViewById(R.id.drawer_layout);

        NavigationView navigationView=findViewById(R.id.navigation_view);

        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(

                this,mNavDrawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close
        );
        mNavDrawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);


        databaseReference= FirebaseDatabase.getInstance().getReference().child("Total Class").child(searchkey);
//        databaseReference1= FirebaseDatabase.getInstance().getReference().child("Class Time").child("Class Date");
//        databaseReference2= FirebaseDatabase.getInstance().getReference().child("Class Room Number").child("Class Room Number");



        progressDialog=new ProgressDialog(this);
        progressDialog.setProgress(10);
        progressDialog.setMax(10);
        progressDialog.setMessage("Loading..");
        progressDialog.setIndeterminate(true);

        if(isOnline()){

            progressDialog.show();
        }
        else {

            Toast.makeText(ClassSearchingResult.this,"Please Check Your Internet Connection and try again.Now You see it offline.",Toast.LENGTH_LONG).show();
        }








        btnclasstimeback=(Button) findViewById(R.id.classtimebackid);


       /* btnshowalarm1=(Button) findViewById(R.id.showalarmid1);
        btnshowalarm2=(Button) findViewById(R.id.showalarmid2);
        btnshowalarm3=(Button) findViewById(R.id.showalarmid3);
        btnshowalarm4=(Button) findViewById(R.id.showalarmid4);
        btnshowalarm5=(Button) findViewById(R.id.showalarmid5);

        btnshowalarm1.setOnClickListener(this);
        btnshowalarm2.setOnClickListener(this);
        btnshowalarm3.setOnClickListener(this);
        btnshowalarm4.setOnClickListener(this);
        btnshowalarm5.setOnClickListener(this);*/


        textViewshowtime1=(TextView) findViewById(R.id.showtimeid1);
        textViewshowtime2=(TextView) findViewById(R.id.showtimeid2);
        textViewshowtime3=(TextView) findViewById(R.id.showtimeid3);
        textViewshowtime4=(TextView) findViewById(R.id.showtimeid4);
        textViewshowtime5=(TextView) findViewById(R.id.showtimeid5);



        tshowcoursecode1=(TextView) findViewById(R.id.showcoursecode1);
        tshowcoursecode2=(TextView) findViewById(R.id.showcoursecode2);
        tshowcoursecode3=(TextView) findViewById(R.id.showcoursecode3);
        tshowcoursecode4=(TextView) findViewById(R.id.showcoursecode4);
        tshowcoursecode5=(TextView) findViewById(R.id.showcoursecode5);



        tttclassroom=(TextView) findViewById(R.id.classsroom);
        tttdate=(TextView) findViewById(R.id.classsdate);
        tttdate.setText(searchdate);


        btnclasstimeback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(ClassSearchingResult.this,ArchiveClass.class);
                startActivity(intent);
                /*finish();
                finishAffinity();*/

            }
        });








        databaseReference.addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {

                progressDialog.dismiss();

                if (!isOnline()) {
                    progressDialog.dismiss();
                }

                if (!dataSnapshot.exists()) {

                    Intent intent = new Intent(ClassSearchingResult.this, ArchiveClass.class);
                    startActivity(intent);
                    Toast.makeText(ClassSearchingResult.this, "Data not found!", Toast.LENGTH_LONG).show();
                } else {



                coursecodes1 = dataSnapshot.child("coursecodes1").getValue().toString();
                coursecodes2 = dataSnapshot.child("coursecodes2").getValue().toString();
                coursecodes3 = dataSnapshot.child("coursecodes3").getValue().toString();
                coursecodes4 = dataSnapshot.child("coursecodes4").getValue().toString();
                coursecodes5 = dataSnapshot.child("coursecodes5").getValue().toString();


                timeshour1 = dataSnapshot.child("hour1").getValue().toString();
                timeshour2 = dataSnapshot.child("hour2").getValue().toString();
                timeshour3 = dataSnapshot.child("hour3").getValue().toString();
                timeshour4 = dataSnapshot.child("hour4").getValue().toString();
                timeshour5 = dataSnapshot.child("hour5").getValue().toString();

                timemins1 = dataSnapshot.child("min1").getValue().toString();
                timemins2 = dataSnapshot.child("min2").getValue().toString();
                timemins3 = dataSnapshot.child("min3").getValue().toString();
                timemins4 = dataSnapshot.child("min4").getValue().toString();
                timemins5 = dataSnapshot.child("min5").getValue().toString();

                timeampm1 = dataSnapshot.child("ampm1").getValue().toString();
                timeampm2 = dataSnapshot.child("ampm2").getValue().toString();
                timeampm3 = dataSnapshot.child("ampm3").getValue().toString();
                timeampm4 = dataSnapshot.child("ampm4").getValue().toString();
                timeampm5 = dataSnapshot.child("ampm5").getValue().toString();


                if (timeshour1.length() > 2 ||
                        timeshour2.length() > 2 ||
                        timeshour3.length() > 2 ||
                        timeshour4.length() > 2 ||
                        timeshour5.length() > 2 ||
                        timemins1.length() > 2 ||
                        timemins2.length() > 2 ||
                        timemins3.length() > 2 ||
                        timemins4.length() > 2 ||
                        timemins5.length() > 2 ||
                        timeampm1.length() > 2 ||
                        timeampm2.length() > 2 ||
                        timeampm3.length() > 2 ||
                        timeampm4.length() > 2 ||
                        timeampm5.length() > 2) {


                    timeshour1 = "00";
                    timeshour2 = "00";
                    timeshour3 = "00";
                    timeshour4 = "00";
                    timeshour5 = "00";


                    timemins1 = "00";
                    timemins2 = "00";
                    timemins3 = "00";
                    timemins4 = "00";
                    timemins5 = "00";


                }


                ///coursecodeEmpty
                if (coursecodes5.isEmpty()) {

                    coursecodes5 = "Empty";
                }
                if (coursecodes4.isEmpty()) {

                    coursecodes4 = "Empty";
                }
                if (coursecodes3.isEmpty()) {

                    coursecodes3 = "Empty";
                }
                if (coursecodes2.isEmpty()) {

                    coursecodes2 = "Empty";
                }
                if (coursecodes1.isEmpty()) {

                    coursecodes1 = "Empty";
                }

                ///timeshourEmpty

                if (timeshour1.isEmpty()) {

                    timeshour1 = "00";
                }
                if (timeshour2.isEmpty()) {

                    timeshour2 = "00";
                }
                if (timeshour3.isEmpty()) {

                    timeshour3 = "00";
                }
                if (timeshour4.isEmpty()) {

                    timeshour4 = "00";
                }
                if (timeshour5.isEmpty()) {

                    timeshour5 = "00";
                }

                //minEmpty
                if (timemins1.isEmpty()) {

                    timemins1 = "00";
                }
                if (timemins2.isEmpty()) {

                    timemins2 = "00";
                }
                if (timemins3.isEmpty()) {


                    timemins3 = "00";
                }
                if (timemins4.isEmpty()) {

                    timemins4 = "00";
                }
                if (timemins5.length() < 1) {

                    timemins5 = "00";
                }

                //ampmEmpty

                if (timeampm1.isEmpty()) {

                    timeampm1 = "      ";
                }
                if (timeampm2.isEmpty()) {

                    timeampm2 = "      ";
                }
                if (timeampm3.isEmpty()) {

                    timeampm3 = "      ";
                }
                if (timeampm4.isEmpty()) {

                    timeampm4 = "      ";
                }
                if (timeampm5.isEmpty()) {

                    timeampm5 = "      ";
                }

                tshowcoursecode1.setText(coursecodes1);
                tshowcoursecode2.setText(coursecodes2);
                tshowcoursecode3.setText(coursecodes3);
                tshowcoursecode4.setText(coursecodes4);
                tshowcoursecode5.setText(coursecodes5);


                textViewshowtime1.setText(timeshour1 + ":" + timemins1 + "  " + timeampm1);
                textViewshowtime2.setText(timeshour2 + ":" + timemins2 + "  " + timeampm2);
                textViewshowtime3.setText(timeshour3 + ":" + timemins3 + "  " + timeampm3);
                textViewshowtime4.setText(timeshour4 + ":" + timemins4 + "  " + timeampm4);
                textViewshowtime5.setText(timeshour5 + ":" + timemins5 + "  " + timeampm5);


                tshowcoursecode1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        AlertDialog.Builder builder = new AlertDialog.Builder(ClassSearchingResult.this);
                        builder.setTitle("Teachers Detail");
                        builder.setIcon(R.drawable.detailes);
                        builder.setMessage("CSE 254>>Java>> Marjan Sir\n" +
                                "-----------------------------------------\n" +
                                "CSE 255 & CSE 256>>Algorithm>>Masud Sir\n" +
                                "-----------------------------------------\n" +
                                "CSE 257 & CSE 258>>Computation>>Hashi Mam\n" +
                                "-----------------------------------------\n" +
                                "CSE 259>>Computer Architecture>>Arshad Sir\n" +
                                "-----------------------------------------\n" +
                                "ECE 259 & ECE 260>>Digital Electronics>>Abubakar Sir\n" +
                                "-----------------------------------------\n" +
                                "ACT 205>>Accounting>>Mamun Sir\n" +
                                "-----------------------------------------\n" +
                                "CSE 252>>Application Development\n");

                        builder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //apphome.super.onBackPressed();
                                dialog.cancel();
                            }
                        });
                        builder.show();


                    }


                });


                tshowcoursecode2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        AlertDialog.Builder builder = new AlertDialog.Builder(ClassSearchingResult.this);
                        builder.setTitle("Teachers Detail");
                        builder.setIcon(R.drawable.detailes);
                        builder.setMessage("CSE 254>>Java>> Marjan Sir\n" +
                                "-----------------------------------------\n" +
                                "CSE 255 & CSE 256>>Algorithm>>Masud Sir\n" +
                                "-----------------------------------------\n" +
                                "CSE 257 & CSE 258>>Computation>>Hashi Mam\n" +
                                "-----------------------------------------\n" +
                                "CSE 259>>Computer Architecture>>Arshad Sir\n" +
                                "-----------------------------------------\n" +
                                "ECE 259 & ECE 260>>Digital Electronics>>Abubakar Sir\n" +
                                "-----------------------------------------\n" +
                                "ACT 205>>Accounting>>Mamun Sir\n" +
                                "-----------------------------------------\n" +
                                "CSE 252>>Application Development\n");

                        builder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //apphome.super.onBackPressed();
                                dialog.cancel();
                            }
                        });
                        builder.show();


                    }


                });


                tshowcoursecode3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        AlertDialog.Builder builder = new AlertDialog.Builder(ClassSearchingResult.this);
                        builder.setTitle("Teachers Detail");
                        builder.setIcon(R.drawable.detailes);
                        builder.setMessage("CSE 254>>Java>> Marjan Sir\n" +
                                "-----------------------------------------\n" +
                                "CSE 255 & CSE 256>>Algorithm>>Masud Sir\n" +
                                "-----------------------------------------\n" +
                                "CSE 257 & CSE 258>>Computation>>Hashi Mam\n" +
                                "-----------------------------------------\n" +
                                "CSE 259>>Computer Architecture>>Arshad Sir\n" +
                                "-----------------------------------------\n" +
                                "ECE 259 & ECE 260>>Digital Electronics>>Abubakar Sir\n" +
                                "-----------------------------------------\n" +
                                "ACT 205>>Accounting>>Mamun Sir\n" +
                                "-----------------------------------------\n" +
                                "CSE 252>>Application Development\n");

                        builder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //apphome.super.onBackPressed();
                                dialog.cancel();
                            }
                        });
                        builder.show();


                    }


                });


                tshowcoursecode4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        AlertDialog.Builder builder = new AlertDialog.Builder(ClassSearchingResult.this);
                        builder.setTitle("Teachers Detail");
                        builder.setIcon(R.drawable.detailes);
                        builder.setMessage("CSE 254>>Java>> Marjan Sir\n" +
                                "-----------------------------------------\n" +
                                "CSE 255 & CSE 256>>Algorithm>>Masud Sir\n" +
                                "-----------------------------------------\n" +
                                "CSE 257 & CSE 258>>Computation>>Hashi Mam\n" +
                                "-----------------------------------------\n" +
                                "CSE 259>>Computer Architecture>>Arshad Sir\n" +
                                "-----------------------------------------\n" +
                                "ECE 259 & ECE 260>>Digital Electronics>>Abubakar Sir\n" +
                                "-----------------------------------------\n" +
                                "ACT 205>>Accounting>>Mamun Sir\n" +
                                "-----------------------------------------\n" +
                                "CSE 252>>Application Development\n");

                        builder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //apphome.super.onBackPressed();
                                dialog.cancel();
                            }
                        });
                        builder.show();


                    }


                });


                tshowcoursecode5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        AlertDialog.Builder builder = new AlertDialog.Builder(ClassSearchingResult.this);
                        builder.setTitle("Teachers Detail");
                        builder.setIcon(R.drawable.detailes);
                        builder.setMessage("CSE 254>>Java>> Marjan Sir\n" +
                                "-----------------------------------------\n" +
                                "CSE 255 & CSE 256>>Algorithm>>Masud Sir\n" +
                                "-----------------------------------------\n" +
                                "CSE 257 & CSE 258>>Computation>>Hashi Mam\n" +
                                "-----------------------------------------\n" +
                                "CSE 259>>Computer Architecture>>Arshad Sir\n" +
                                "-----------------------------------------\n" +
                                "ECE 259 & ECE 260>>Digital Electronics>>Abubakar Sir\n" +
                                "-----------------------------------------\n" +
                                "ACT 205>>Accounting>>Mamun Sir\n" +
                                "-----------------------------------------\n" +
                                "CSE 252>>Application Development\n");


                        builder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //apphome.super.onBackPressed();
                                dialog.cancel();
                            }
                        });
                        builder.show();


                    }


                });


            }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




/*        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                todayclassdate=dataSnapshot.child("classScheduleDate").getValue().toString();

                tttdate.setText(todayclassdate);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/

/*
        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                todayclassroom=dataSnapshot.child("classroomnumber").getValue().toString();

                tttclassroom.setText(todayclassroom);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
*/






    }



    @Override
    public void onClick(View v) {

        if (v.getId()==R.id.showalarmid1){

            int hour=0,min=0;

            if(isOnline()){

                if(timeshour1.length()>0&&timemins1.length()>0){

                    hour=Integer.parseInt(timeshour1);
                    min=Integer.parseInt(timemins1);
                }
                else {

                    Toast.makeText(ClassSearchingResult.this,"Data not found!",Toast.LENGTH_LONG).show();
                }


                alarmsystem(hour,min);
            }
            else {

                Toast.makeText(ClassSearchingResult.this,"Please turn on your internet connection!",Toast.LENGTH_LONG).show();
            }



            /*Intent intent=new Intent(AlarmClock.ACTION_SET_ALARM);
            intent.putExtra(AlarmClock.EXTRA_HOUR,hour);
            intent.putExtra(AlarmClock.EXTRA_MINUTES,min);
            intent.putExtra(AlarmClock.EXTRA_IS_PM,ampms);


            if(hour>=1&&hour<=12&&min>=1&&min<=60){

                startActivity(intent);
            }
            else {
                Toast.makeText(ClassSearching.this,"Class Schedule Is Empty",Toast.LENGTH_LONG).show();
            }

*/
        }

        if (v.getId()==R.id.showalarmid2){

            int hour=0,min=0;

            if(isOnline()){

                if(timeshour2.length()>0&&timemins2.length()>0){

                    hour=Integer.parseInt(timeshour2);
                    min=Integer.parseInt(timemins2);
                }
                else {

                    Toast.makeText(ClassSearchingResult.this,"Data not found!",Toast.LENGTH_LONG).show();
                }


                alarmsystem(hour,min);
            }
            else {

                Toast.makeText(ClassSearchingResult.this,"Please turn on your internet connection!",Toast.LENGTH_LONG).show();
            }

        }
        if (v.getId()==R.id.showalarmid3){

            int hour=0,min=0;

            if(isOnline()){

                if(timeshour3.length()>0&&timemins3.length()>0){

                    hour=Integer.parseInt(timeshour3);
                    min=Integer.parseInt(timemins3);
                }
                else {

                    Toast.makeText(ClassSearchingResult.this,"Data not found!",Toast.LENGTH_LONG).show();
                }


                alarmsystem(hour,min);
            }
            else {

                Toast.makeText(ClassSearchingResult.this,"Please turn on your internet connection!",Toast.LENGTH_LONG).show();
            }

        }
        if (v.getId()==R.id.showalarmid4){

            int hour=0,min=0;

            if(isOnline()){

                if(timeshour4.length()>0&&timemins4.length()>0){

                    hour=Integer.parseInt(timeshour4);
                    min=Integer.parseInt(timemins4);
                }
                else {

                    Toast.makeText(ClassSearchingResult.this,"Data not found!",Toast.LENGTH_LONG).show();
                }


                alarmsystem(hour,min);
            }
            else {

                Toast.makeText(ClassSearchingResult.this,"Please turn on your internet connection!",Toast.LENGTH_LONG).show();
            }

        }
        if (v.getId()==R.id.showalarmid5){

            int hour=0,min=0;

            if(isOnline()){

                if(timeshour5.length()>0&&timemins5.length()>0){

                    hour=Integer.parseInt(timeshour5);
                    min=Integer.parseInt(timemins5);
                }
                else {

                    Toast.makeText(ClassSearchingResult.this,"Data not found!",Toast.LENGTH_LONG).show();
                }


                alarmsystem(hour,min);
            }
            else {

                Toast.makeText(ClassSearchingResult.this,"Please turn on your internet connection!",Toast.LENGTH_LONG).show();
            }



        }

    }

    public void alarmsystem(int h,int m){

        Intent intent=new Intent(AlarmClock.ACTION_SET_ALARM);
        intent.putExtra(AlarmClock.EXTRA_HOUR,h);
        intent.putExtra(AlarmClock.EXTRA_MINUTES,m);


        if(h>=1&&h<=12){

            if(m>=0&&m<=60){

                startActivity(intent);
            }
        }
        else {
            Toast.makeText(ClassSearchingResult.this,"Class Schedule is Empty!",Toast.LENGTH_LONG).show();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.back_layout,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==R.id.backId){
            Intent intent=new Intent(ClassSearchingResult.this,ArchiveClass.class);
            startActivity(intent);

            /*finish();*/
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed(){

        Intent intent=new Intent(ClassSearchingResult.this,ArchiveClass.class);
        startActivity(intent);
        /*finish();*/

    }



    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nvclassschedulehome:



                Intent intent=new Intent(ClassSearchingResult.this,new_app_menu.class);
                startActivity(intent);

                mNavDrawer.closeDrawer(GravityCompat.START);
                break;

            case R.id.nvtodayclassschedules:


                Intent intent1=new Intent(ClassSearchingResult.this, ClassSearchingResult.class);
                startActivity(intent1);

                finish();

                mNavDrawer.closeDrawer(GravityCompat.START);
                break;

            case R.id.nvnoticeboardid:



                Intent intent2=new Intent(ClassSearchingResult.this,notice_display_activity.class);
                startActivity(intent2);
                mNavDrawer.closeDrawer(GravityCompat.START);
                finish();

                break;

            case R.id.nvcurriculamid:
                Intent intent3=new Intent(ClassSearchingResult.this,curriculamactivity.class);
                startActivity(intent3);
                mNavDrawer.closeDrawer(GravityCompat.START);

                finish();
                break;

            case R.id.nvbusscheduleid:
                Intent intent4=new Intent(ClassSearchingResult.this,busschedulepdf.class);
                startActivity(intent4);
                mNavDrawer.closeDrawer(GravityCompat.START);
                finish();
                break;

            case R.id.nvfacebookgrpid:
                Intent intent5=new Intent(ClassSearchingResult.this,facebook_group.class);
                startActivity(intent5);
                mNavDrawer.closeDrawer(GravityCompat.START);
                finish();

                break;
            case R.id.nvsettimeid:


                Intent intent6=new Intent(ClassSearchingResult.this,settimeactivity.class);
                startActivity(intent6);

                finish();


                break;

            case R.id.nvrating:
                Intent intent7=new Intent();
                intent7.setAction(Intent.ACTION_VIEW);
                intent7.addCategory(Intent.CATEGORY_BROWSABLE);
                intent7.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.classschedule.classtime"));
                startActivity(intent7);
                mNavDrawer.closeDrawer(GravityCompat.START);
                finish();
                break;

            case R.id.nvshareid:
                Intent intent8=new Intent(Intent.ACTION_SEND);
                intent8.setType("text/plain");
                String body="Class Schedule App for Computer Science and Engineering Department,HSTU." +
                        " \nPlay Store Link : https://play.google.com/store/apps/details?id=com.classschedule.classtime";
                String sub="Class Schedule";
                intent8.putExtra(Intent.EXTRA_SUBJECT,sub);
                intent8.putExtra(Intent.EXTRA_TEXT,body);
                startActivity(Intent.createChooser(intent8,"Share Using"));
                mNavDrawer.closeDrawer(GravityCompat.START);
                finish();
                break;
            case R.id.nvdeveloperid:
                Intent intent9=new Intent(ClassSearchingResult.this,developer.class);
                startActivity(intent9);
                mNavDrawer.closeDrawer(GravityCompat.START);
                finish();
                break;




            case R.id.nvaboutthisappid:

                try{

                    Intent intent10=new Intent(ClassSearchingResult.this,AboutThisApplication.class);
                    startActivity(intent10);

                }catch (Exception e){
                    Toast.makeText(ClassSearchingResult.this,"Exception"+e,Toast.LENGTH_LONG).show();
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
    private void showdialog() {

        LayoutInflater layoutInflater=LayoutInflater.from(this);
        View view=layoutInflater.inflate(R.layout.alertdialog_layout,null);

        btnaYes=(Button) view.findViewById(R.id.aYes);
        btnaNo=(Button) view.findViewById(R.id.aNo);

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



        final AlertDialog alertDialog=new AlertDialog.Builder(this).setView(view)
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
