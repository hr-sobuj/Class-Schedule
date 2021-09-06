package com.classschedule.classtime;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.TimePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Process;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class addclasstimeactivity extends AppCompatActivity implements View.OnClickListener , NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout mNavDrawer;
    Button btnaYes,btnaNo;

    AlertReceiver classscheduleBroadcastReceiver = new AlertReceiver();

    private EditText editTextcourse1,editTextcourse2,editTextcourse3,editTextcourse4,editTextcourse5,editTextdateid;



    private EditText et1,em1,ea1,
            et2,em2,ea2,
            et3,em3,ea3,
            et4,em4,ea4,
            et5,em5,ea5;

    DatabaseReference databaseReference,databaseReferenceForTotalClass;

    private Button btncourse1,btncourse2,btncourse3,btncourse4,btncourse5,btnclasssubmitids;

    private TextView textViewcourse1,textViewcourse2,textViewcourse3,textViewcourse4,textViewcourse5;

    private TimePickerDialog timePickerDialog;

    private String c1,t1,m1,a1,
            c2,t2,m2,a2,
            c3,t3,m3,a3,
            c4,t4,m4,a4,
            c5,t5,m5,a5,dateids1;


    private NotificationHelper mNotificationhelper;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_addclasstimeactivity);
        
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

                datepicker_edittext.setText(sdf.format(myCalendar.getTime()));
            }

        };

        datepicker_edittext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(addclasstimeactivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
  /*      edittext.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(addclasstimeactivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });*/
//        private void updateLabel() {
//
//        }


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


        IntentFilter filter = new IntentFilter("com.codinginflow.classschedule_ACTION");
        registerReceiver(classscheduleBroadcastReceiver, filter);



        editTextcourse1=(EditText) findViewById(R.id.coursecode1ic);
        editTextcourse2=(EditText) findViewById(R.id.coursecode2ic);
        editTextcourse3=(EditText) findViewById(R.id.coursecode3ic);
        editTextcourse4=(EditText) findViewById(R.id.coursecode4ic);
        editTextcourse5=(EditText) findViewById(R.id.coursecode5ic);

        editTextdateid=(EditText) findViewById(R.id.classdateid1);



        et1=(EditText) findViewById(R.id.coursecode1hourid);
        et2=(EditText) findViewById(R.id.coursecode2hourid);
        et3=(EditText) findViewById(R.id.coursecode3hourid);
        et4=(EditText) findViewById(R.id.coursecode4hourid);
        et5=(EditText) findViewById(R.id.coursecode5hourid);

        em1=(EditText) findViewById(R.id.coursecode1minid);
        em2=(EditText) findViewById(R.id.coursecode2minid);
        em3=(EditText) findViewById(R.id.coursecode3minid);
        em4=(EditText) findViewById(R.id.coursecode4minid);
        em5=(EditText) findViewById(R.id.coursecode5minid);

        ea1=(EditText) findViewById(R.id.coursecode1ampmid);
        ea2=(EditText) findViewById(R.id.coursecode2ampmid);
        ea3=(EditText) findViewById(R.id.coursecode3ampmid);
        ea4=(EditText) findViewById(R.id.coursecode4ampmid);
        ea5=(EditText) findViewById(R.id.coursecode5ampmid);

        mNotificationhelper=new NotificationHelper(this);



        btnclasssubmitids=(Button) findViewById(R.id.classtimesubmitid);



        databaseReference= FirebaseDatabase.getInstance().getReference("Class Time");
        databaseReferenceForTotalClass= FirebaseDatabase.getInstance().getReference("Total Class");



        btnclasssubmitids.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                c1=editTextcourse1.getText().toString();
                c2=editTextcourse2.getText().toString();
                c3=editTextcourse3.getText().toString();
                c4=editTextcourse4.getText().toString();
                c5=editTextcourse5.getText().toString();

                t1=et1.getText().toString();
                t2=et2.getText().toString();
                t3=et3.getText().toString();
                t4=et4.getText().toString();
                t5=et5.getText().toString();

                m1=em1.getText().toString();
                m2=em2.getText().toString();
                m3=em3.getText().toString();
                m4=em4.getText().toString();
                m5=em5.getText().toString();

                a1=ea1.getText().toString();
                a2=ea2.getText().toString();
                a3=ea3.getText().toString();
                a4=ea4.getText().toString();
                a5=ea5.getText().toString();


                dateids1=datepicker_edittext.getText().toString();

                if(dateids1.length()>0){



                    String key1="Class Date";

                    String key="Today's Class Time";
                    ClassTime_handler classTime_handler_handler=new ClassTime_handler(c1,t1,m1,a1, c2,t2,m2,a2, c3,t3,m3,a3, c4,t4,m4,a4, c5,t5,m5,a5);
                    databaseReference.child(key).setValue(classTime_handler_handler);
                    String[] keyTotalClass=dateids1.split("/");
                    String keyTotal=keyTotalClass[0]+keyTotalClass[1]+keyTotalClass[2];
//                    Log.e("totalclass", keyTotal);
                    databaseReferenceForTotalClass.child(keyTotal).setValue(classTime_handler_handler);
                    if(isOnline()){
                        Intent intent=new Intent(addclasstimeactivity.this,adminactivity.class);
                        startActivity(intent);
                        Toast.makeText(addclasstimeactivity.this,"Class Schedule has been Added.",Toast.LENGTH_LONG).show();

                        finish();
                    }
                    else {

                        Toast.makeText(addclasstimeactivity.this,"No Internet Connection!",Toast.LENGTH_LONG).show();
                    }


                    date_Handler date_handler=new date_Handler(dateids1);
                    databaseReference.child(key1).setValue(date_handler);




                }

                else {

                    AlertDialog.Builder builder=new AlertDialog.Builder(addclasstimeactivity.this);
                    builder.setTitle("Warning");
                    builder.setIcon(R.drawable.warning);
                    builder.setMessage("Date is empty.");
                    builder.setPositiveButton("Dismiss", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });


                    builder.show();

                }






                //Notification
               /* sendChannel("Today's Class Schedule","CR has been Added the class Schedule.Check Class Schedule.");
*/


                /*FirebaseMessaging.getInstance().subscribeToTopic("weather")
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                *//*String msg = getString(R.string.msg_subscribed);*//*
                                String msg="Hello";
                                if (!task.isSuccessful()) {
                                    *//*msg = getString(R.string.msg_subscribe_failed);*//*
                                    msg="Failed";
                                }
                                *//*Log.d(TAG, msg);*//*
                                Toast.makeText(addclasstimeactivity.this, msg, Toast.LENGTH_SHORT).show();
                            }
                        });*/

            }
        });


























/*
        textViewcourse1=(TextView) findViewById(R.id.coursecode1textid);
        textViewcourse2=(TextView) findViewById(R.id.coursecode2textid);
        textViewcourse3=(TextView) findViewById(R.id.coursecode3textid);
        textViewcourse4=(TextView) findViewById(R.id.coursecode4textid);
        textViewcourse5=(TextView) findViewById(R.id.coursecode5textid);*/


       /* btncourse1=(Button) findViewById(R.id.coursecode1btnid);
        btncourse2=(Button) findViewById(R.id.coursecode2btnid);
        btncourse3=(Button) findViewById(R.id.coursecode3btnid);
        btncourse4=(Button) findViewById(R.id.coursecode4btnid);
        btncourse5=(Button) findViewById(R.id.coursecode5btnid);

        btncourse1.setOnClickListener(this);
        btncourse2.setOnClickListener(this);
        btncourse3.setOnClickListener(this);
        btncourse4.setOnClickListener(this);
        btncourse5.setOnClickListener(this);*/






    }

    @Override
    public void onClick(View v) {
/*
        if (v.getId()==R.id.coursecode1btnid) {


            TimePicker timePicker = new TimePicker(this);
            int currenthour = timePicker.getCurrentHour();
            int currentminite = timePicker.getCurrentMinute();

            timePickerDialog = new TimePickerDialog(addclasstimeactivity.this,

                    new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                            textViewcourse1.setText(hourOfDay+":"+minute);

                        }
                    }, currenthour, currentminite, false);

            timePickerDialog.show();



        }


        if (v.getId()==R.id.coursecode2btnid){

            TimePicker timePicker = new TimePicker(this);
            int currenthour = timePicker.getCurrentHour();
            int currentminite = timePicker.getCurrentMinute();

            timePickerDialog = new TimePickerDialog(addclasstimeactivity.this,

                    new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                            textViewcourse2.setText(hourOfDay+":"+minute);

                        }
                    }, currenthour, currentminite, false);

            timePickerDialog.show();

        }

        if (v.getId()==R.id.coursecode3btnid){

            TimePicker timePicker = new TimePicker(this);
            int currenthour = timePicker.getCurrentHour();
            int currentminite = timePicker.getCurrentMinute();

            timePickerDialog = new TimePickerDialog(addclasstimeactivity.this,

                    new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                            textViewcourse3.setText(hourOfDay+":"+minute);

                        }
                    }, currenthour, currentminite, false);

            timePickerDialog.show();

        }

        if (v.getId()==R.id.coursecode4btnid){

            TimePicker timePicker = new TimePicker(this);
            int currenthour = timePicker.getCurrentHour();
            int currentminite = timePicker.getCurrentMinute();

            timePickerDialog = new TimePickerDialog(addclasstimeactivity.this,

                    new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                            textViewcourse4.setText(hourOfDay+":"+minute);

                        }
                    }, currenthour, currentminite, false);

            timePickerDialog.show();

        }

        if (v.getId()==R.id.coursecode5btnid){

            TimePicker timePicker = new TimePicker(this);
            int currenthour = timePicker.getCurrentHour();
            int currentminite = timePicker.getCurrentMinute();

            timePickerDialog = new TimePickerDialog(addclasstimeactivity.this,

                    new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                            textViewcourse5.setText(hourOfDay+":"+minute);

                        }
                    }, currenthour, currentminite, false);

            timePickerDialog.show();

        }

    }*/
    }

    public void sendChannel(String titles,String messagess){

        NotificationCompat.Builder nb=mNotificationhelper.getChanel1Notification(titles,messagess);

      nb.setDefaults(Notification.DEFAULT_SOUND);

        mNotificationhelper.getManager().notify(1,nb.build());

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
            Intent intent=new Intent(addclasstimeactivity.this,adminactivity.class);
            startActivity(intent);

            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed(){

        Intent intent=new Intent(addclasstimeactivity.this,adminactivity.class);
        startActivity(intent);
        finish();

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
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(classscheduleBroadcastReceiver);
    }

    public void sendBroadcast(View v) {
        Intent intent = new Intent("com.codinginflow.classschedule_ACTION");
        intent.putExtra("Class Schedule", "Check Today's Class Schedule.");
        sendBroadcast(intent);
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String receivedText = intent.getStringExtra("com.codinginflow.EXTRA_TEXT");

        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter("com.codinginflow.classschedule_ACTION");
        registerReceiver(broadcastReceiver, filter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(broadcastReceiver);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nvclassschedulehome:



                Intent intent=new Intent(addclasstimeactivity.this,new_app_menu.class);
                startActivity(intent);

                mNavDrawer.closeDrawer(GravityCompat.START);
                break;

            case R.id.nvtodayclassschedules:


                Intent intent1=new Intent(addclasstimeactivity.this,classactivity.class);
                startActivity(intent1);

                finish();

                mNavDrawer.closeDrawer(GravityCompat.START);
                break;

            case R.id.nvnoticeboardid:



                Intent intent2=new Intent(addclasstimeactivity.this,notice_display_activity.class);
                startActivity(intent2);
                mNavDrawer.closeDrawer(GravityCompat.START);
                finish();

                break;

            case R.id.nvcurriculamid:
                Intent intent3=new Intent(addclasstimeactivity.this,curriculamactivity.class);
                startActivity(intent3);
                mNavDrawer.closeDrawer(GravityCompat.START);

                finish();
                break;

            case R.id.nvbusscheduleid:
                Intent intent4=new Intent(addclasstimeactivity.this,busschedulepdf.class);
                startActivity(intent4);
                mNavDrawer.closeDrawer(GravityCompat.START);
                finish();
                break;

            case R.id.nvfacebookgrpid:
                Intent intent5=new Intent(addclasstimeactivity.this,facebook_group.class);
                startActivity(intent5);
                mNavDrawer.closeDrawer(GravityCompat.START);
                finish();

                break;
            case R.id.nvsettimeid:


                Intent intent6=new Intent(addclasstimeactivity.this,settimeactivity.class);
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
                Intent intent9=new Intent(addclasstimeactivity.this,developer.class);
                startActivity(intent9);
                mNavDrawer.closeDrawer(GravityCompat.START);
                finish();
                break;




            case R.id.nvaboutthisappid:

                try{

                    Intent intent10=new Intent(addclasstimeactivity.this,AboutThisApplication.class);
                    startActivity(intent10);

                }catch (Exception e){
                    Toast.makeText(addclasstimeactivity.this,"Exception"+e,Toast.LENGTH_LONG).show();
                }

                mNavDrawer.closeDrawer(GravityCompat.START);
                finish();
                break;
            case R.id.nvexitic:
                showdialog();

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
