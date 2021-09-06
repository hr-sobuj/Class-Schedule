package com.classschedule.classtime;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Process;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class addnewuser extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{


    private DrawerLayout mNavDrawer;
    Button btnaYes,btnaNo;

    private Button signup_button;
    private EditText mailadress1,pass1,repass1;
    private FirebaseAuth mAuth;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_addnewuser);

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


        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Loading..");


        signup_button=(Button) findViewById(R.id.signUpss);
        mailadress1=(EditText) findViewById(R.id.signUpmail);
        pass1=(EditText) findViewById(R.id.signUpPass1);
        /*repass1=(EditText) findViewById(R.id.repeatPass1);*/


        mAuth = FirebaseAuth.getInstance();


        signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent=new Intent(mail_registration.this,registration_success_page.class);
                startActivity(intent);*/

                userRegistration();


            }

            public void userRegistration(){
                final String email=mailadress1.getText().toString().trim();
                final String password=pass1.getText().toString().trim();
                /* final String passs2=repass1.getText().toString().trim();*/
                progressDialog.show();

                if(email.isEmpty()){
                    mailadress1.setError("Input a correct mail address");
                    mailadress1.requestFocus();
                    return;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    mailadress1.setError("Input a Valid email address");
                    mailadress1.requestFocus();
                    return;
                }
                if(password.length()<6){

                    pass1.setError("Password Length 6");
                    pass1.requestFocus();
                    return;
                }


                mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            progressDialog.dismiss();


                            Intent intent=new Intent(addnewuser.this,adminactivity.class);
                            startActivity(intent);
                            Toast.makeText(addnewuser.this,"User Added Successfully.",Toast.LENGTH_LONG).show();




                        } else {
                            progressDialog.dismiss();

                            if(task.getException() instanceof FirebaseAuthUserCollisionException){

                                Toast.makeText(addnewuser.this,"User Already registered",Toast.LENGTH_LONG).show();

                            }
                            else {
                                Toast.makeText(addnewuser.this,"Exception"+task.getException().getMessage(),Toast.LENGTH_LONG).show();
                            }





                        }
                    }
                });

            }
        });


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
            Intent intent=new Intent(addnewuser.this,adminactivity.class);
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed(){

                Intent intent=new Intent(addnewuser.this,adminactivity.class);
                startActivity(intent);
                finish();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nvclassschedulehome:



                Intent intent=new Intent(addnewuser.this,new_app_menu.class);
                startActivity(intent);

                mNavDrawer.closeDrawer(GravityCompat.START);
                break;

            case R.id.nvtodayclassschedules:


                Intent intent1=new Intent(addnewuser.this,classactivity.class);
                startActivity(intent1);

                finish();

                mNavDrawer.closeDrawer(GravityCompat.START);
                break;

            case R.id.nvnoticeboardid:



                Intent intent2=new Intent(addnewuser.this,notice_display_activity.class);
                startActivity(intent2);
                mNavDrawer.closeDrawer(GravityCompat.START);
                finish();

                break;

            case R.id.nvcurriculamid:
                Intent intent3=new Intent(addnewuser.this,curriculamactivity.class);
                startActivity(intent3);
                mNavDrawer.closeDrawer(GravityCompat.START);

                finish();
                break;

            case R.id.nvbusscheduleid:
                Intent intent4=new Intent(addnewuser.this,busschedulepdf.class);
                startActivity(intent4);
                mNavDrawer.closeDrawer(GravityCompat.START);
                finish();
                break;

            case R.id.nvfacebookgrpid:
                Intent intent5=new Intent(addnewuser.this,facebook_group.class);
                startActivity(intent5);
                mNavDrawer.closeDrawer(GravityCompat.START);
                finish();

                break;
            case R.id.nvsettimeid:


                Intent intent6=new Intent(addnewuser.this,settimeactivity.class);
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
                Intent intent9=new Intent(addnewuser.this,developer.class);
                startActivity(intent9);
                mNavDrawer.closeDrawer(GravityCompat.START);
                finish();
                break;




            case R.id.nvaboutthisappid:

                try{

                    Intent intent10=new Intent(addnewuser.this,AboutThisApplication.class);
                    startActivity(intent10);

                }catch (Exception e){
                    Toast.makeText(addnewuser.this,"Exception"+e,Toast.LENGTH_LONG).show();
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
