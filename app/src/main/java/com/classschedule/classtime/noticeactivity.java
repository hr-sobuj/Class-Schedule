package com.classschedule.classtime;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class noticeactivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private EditText editTextTitle,editTextnoticeMessage,editTextnoticeDate;
    private RadioGroup radioGroupnoticeGroup;
    private RadioButton radioButtonGeneralnotice,radioButtonImportantNotice,radioButtonVal;
    private String dateVal,titleVal,messageVal,noticeVal;

    DatabaseReference databaseReference;

    private Button btnNoticesubmit;
    private NotificationHelper mNotificationhelper;

    private DrawerLayout mNavDrawer;
    Button btnaYes,btnaNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_addnotice);


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


        editTextTitle=(EditText) findViewById(R.id.noticetitleid);
        editTextnoticeMessage=(EditText) findViewById(R.id.noticemessageid);
        editTextnoticeDate=(EditText) findViewById(R.id.noticedateid);
        radioGroupnoticeGroup=(RadioGroup) findViewById(R.id.noticegroupid);
        radioButtonGeneralnotice=(RadioButton) findViewById(R.id.generalnoticeid);
        radioButtonImportantNotice=(RadioButton) findViewById(R.id.importantnoticeid);
        btnNoticesubmit=(Button) findViewById(R.id.noticesubmitid);



        mNotificationhelper=new NotificationHelper(this);


        databaseReference= FirebaseDatabase.getInstance().getReference("NoticeBoard");

        btnNoticesubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {





              try {
                  int selected=radioGroupnoticeGroup.getCheckedRadioButtonId();
                  radioButtonVal=(RadioButton) findViewById(selected);
                  noticeVal=radioButtonVal.getText().toString();


                  dateVal=new SimpleDateFormat("dd-MM-yyyy").format(new Date());
                  Log.e("date",dateVal);
//                  dateVal=editTextnoticeDate.getText().toString();
                  titleVal=editTextTitle.getText().toString();
                  messageVal=editTextnoticeMessage.getText().toString();


                  if(!dateVal.isEmpty()&&!titleVal.isEmpty()&&!messageVal.isEmpty()&&!noticeVal.isEmpty()){


                      if(noticeVal.length()==14){

                          Toast.makeText(noticeactivity.this,"General Notice Added Successfully.",Toast.LENGTH_LONG).show();

                          String key="General Notice Board";
                          notice_data_handle data_handler=new notice_data_handle(dateVal,titleVal,messageVal,noticeVal);
                          databaseReference.child(key).setValue(data_handler);

                          Intent intent=new Intent(noticeactivity.this,adminactivity.class);
                          startActivity(intent);
                          /*sendChannel("General Notice!!","CR has been Added a Notice.Check Notice.");*/
                      }

                      else {
                          Toast.makeText(noticeactivity.this,"Important Notice Added Successfully.",Toast.LENGTH_LONG).show();


                          String key="Important Notice";
                          notice_data_handle data_handler=new notice_data_handle(dateVal,titleVal,messageVal,noticeVal);
                          databaseReference.child(key).setValue(data_handler);
                          Intent intent=new Intent(noticeactivity.this,adminactivity.class);
                          startActivity(intent);
                          /*sendChannel("Important Notice!!","CR has been Added a Notice.Check Notice.");*/
                      }



                  }

                  else {
                      alertbox();
                  }







              }
              catch (Exception e){

                  Toast.makeText(noticeactivity.this,"Error"+e,Toast.LENGTH_LONG).show();
              }






            }
        });



    }

    private void alertbox()
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(noticeactivity.this);
        builder.setTitle("Alert!");
        builder.setIcon(R.drawable.warning);
        builder.setMessage("Please Fill Up the Form.");

        builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //apphome.super.onBackPressed();
                dialog.cancel();
            }
        });
        builder.show();
    }
    public void sendChannel(String titles1,String messagess1){

        NotificationCompat.Builder nb1=mNotificationhelper.getChane21Notification(titles1,messagess1);
        mNotificationhelper.getManager().notify(2,nb1.build());


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
            Intent intent=new Intent(noticeactivity.this,adminactivity.class);
            startActivity(intent);

            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed(){

        Intent intent=new Intent(noticeactivity.this,adminactivity.class);
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
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nvclassschedulehome:



                Intent intent=new Intent(noticeactivity.this,new_app_menu.class);
                startActivity(intent);

                mNavDrawer.closeDrawer(GravityCompat.START);
                break;

            case R.id.nvtodayclassschedules:


                Intent intent1=new Intent(noticeactivity.this,classactivity.class);
                startActivity(intent1);

                finish();

                mNavDrawer.closeDrawer(GravityCompat.START);
                break;

            case R.id.nvnoticeboardid:



                Intent intent2=new Intent(noticeactivity.this,notice_display_activity.class);
                startActivity(intent2);
                mNavDrawer.closeDrawer(GravityCompat.START);
                finish();

                break;

            case R.id.nvcurriculamid:
                Intent intent3=new Intent(noticeactivity.this,curriculamactivity.class);
                startActivity(intent3);
                mNavDrawer.closeDrawer(GravityCompat.START);

                finish();
                break;

            case R.id.nvbusscheduleid:
                Intent intent4=new Intent(noticeactivity.this,busschedulepdf.class);
                startActivity(intent4);
                mNavDrawer.closeDrawer(GravityCompat.START);
                finish();
                break;

            case R.id.nvfacebookgrpid:
                Intent intent5=new Intent(noticeactivity.this,facebook_group.class);
                startActivity(intent5);
                mNavDrawer.closeDrawer(GravityCompat.START);
                finish();

                break;
            case R.id.nvsettimeid:


                Intent intent6=new Intent(noticeactivity.this,settimeactivity.class);
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
                Intent intent9=new Intent(noticeactivity.this,developer.class);
                startActivity(intent9);
                mNavDrawer.closeDrawer(GravityCompat.START);
                finish();
                break;




            case R.id.nvaboutthisappid:

                try{

                    Intent intent10=new Intent(noticeactivity.this,AboutThisApplication.class);
                    startActivity(intent10);

                }catch (Exception e){
                    Toast.makeText(noticeactivity.this,"Exception"+e,Toast.LENGTH_LONG).show();
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
