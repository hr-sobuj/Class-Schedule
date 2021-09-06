package com.classschedule.classtime;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class new_app_menu extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout mNavDrawer;

    private CardView crdclass,crdnotice,crdcurriculam,crdbus,crdfacebook,crdadmin;
    Button btnaYes,btnaNo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_drawer_layout);



        crdclass=(CardView) findViewById(R.id.crdclassscheduleid);
        crdnotice=(CardView) findViewById(R.id.crdnoticeboardid);
        crdcurriculam=(CardView) findViewById(R.id.crdcurriculumid);
        crdbus=(CardView) findViewById(R.id.crdbusscheduleid);
        crdfacebook=(CardView) findViewById(R.id.crdfacebookgrpid);
        crdadmin=(CardView) findViewById(R.id.crdadminpanelid);



        crdclass.setOnClickListener(this);
        crdnotice.setOnClickListener(this);
        crdcurriculam.setOnClickListener(this);
        crdbus.setOnClickListener(this);
        crdfacebook.setOnClickListener(this);
        crdadmin.setOnClickListener(this);


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

    }

    @Override
    public void onClick(View v) {

        if(v==crdclass){

            Intent intent=new Intent(new_app_menu.this,classactivity.class);
            startActivity(intent);
            finish();

        }

        if(v==crdnotice){

            Intent intent=new Intent(new_app_menu.this,notice_display_activity.class);
            startActivity(intent);
            finish();

        }

        if(v==crdcurriculam){

            Intent intent=new Intent(new_app_menu.this,curriculamactivity.class);
            startActivity(intent);
            finish();

        }
        if(v==crdbus){

            Intent intent=new Intent(new_app_menu.this, busschedulepdf.class);
            startActivity(intent);
            finish();

        }

        if(v==crdadmin){

            Intent intent=new Intent(new_app_menu.this,settimeactivity.class);
            startActivity(intent);
            finish();

        }

        if(v==crdfacebook){

            Intent intent=new Intent(new_app_menu.this,facebook_group.class);
            startActivity(intent);
            finish();

        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.nvclassschedulehome:



                Intent intent=new Intent(new_app_menu.this,new_app_menu.class);
                startActivity(intent);

                mNavDrawer.closeDrawer(GravityCompat.START);
                break;

            case R.id.nvtodayclassschedules:


                Intent intent1=new Intent(new_app_menu.this,classactivity.class);
                startActivity(intent1);

                finish();

                mNavDrawer.closeDrawer(GravityCompat.START);
                break;

            case R.id.nvnoticeboardid:



                Intent intent2=new Intent(new_app_menu.this,notice_display_activity.class);
                startActivity(intent2);
                mNavDrawer.closeDrawer(GravityCompat.START);
                finish();

                break;

            case R.id.nvcurriculamid:
                Intent intent3=new Intent(new_app_menu.this,curriculamactivity.class);
                startActivity(intent3);
                mNavDrawer.closeDrawer(GravityCompat.START);

                finish();
                break;

            case R.id.nvbusscheduleid:
                Intent intent4=new Intent(new_app_menu.this,busschedulepdf.class);
                startActivity(intent4);
                mNavDrawer.closeDrawer(GravityCompat.START);
                finish();
                break;

            case R.id.nvfacebookgrpid:
                Intent intent5=new Intent(new_app_menu.this,facebook_group.class);
                startActivity(intent5);
                mNavDrawer.closeDrawer(GravityCompat.START);
                finish();

                break;
            case R.id.nvsettimeid:


                Intent intent6=new Intent(new_app_menu.this,settimeactivity.class);
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
                Intent intent9=new Intent(new_app_menu.this,developer.class);
                startActivity(intent9);
                mNavDrawer.closeDrawer(GravityCompat.START);
                finish();
                break;




            case R.id.nvaboutthisappid:

                try{

                    Intent intent10=new Intent(new_app_menu.this,AboutThisApplication.class);
                    startActivity(intent10);

                }catch (Exception e){
                    Toast.makeText(new_app_menu.this,"Exception"+e,Toast.LENGTH_LONG).show();
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
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.exit_layout,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==R.id.exitId){

        showdialog();
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed(){

        if(mNavDrawer.isDrawerOpen(GravityCompat.START)){
            mNavDrawer.closeDrawer(GravityCompat.START);
        }
        else {

            showdialog();
        }


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
