package com.classschedule.classtime;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TotalClass extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AdaptarRecyclerView2 adaptarRecyclerView;
    private List<ClassTime_handler> classList;
    DatabaseReference databaseReference;
    RecyclerView.LayoutManager mLinearLayoutManager;
    private ProgressDialog progressDialog;
    MediaPlayer mediaPlayer;
    private DrawerLayout mNavDrawer;
    LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_class);



        progressDialog=new ProgressDialog(this);
        progressDialog.setProgress(10);
        progressDialog.setMax(10);
        progressDialog.setMessage("Loading..");
        progressDialog.setIndeterminate(true);
        if(isOnline()){

            progressDialog.show();
        }
        else {

            Toast.makeText(TotalClass.this,"Please Check Your Internet Connection and try again.Now You see it offline.",Toast.LENGTH_LONG).show();

        }



        recyclerView=(RecyclerView) findViewById(R.id.recyclerviewid);
        recyclerView.setHasFixedSize(true);

        linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);



        classList =new ArrayList();

        databaseReference= FirebaseDatabase.getInstance().getReference("Total Class");


        databaseReference.orderByKey().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.e("data",dataSnapshot+"");
                if(dataSnapshot.exists()){
                    progressDialog.dismiss();

                }
                else{
                    progressDialog.dismiss();
                    Toast.makeText(TotalClass.this,"emtpy :(",Toast.LENGTH_LONG).show();

                    Intent intent=new Intent(TotalClass.this,ArchiveClass.class);
                    startActivity(intent);
                }

                progressDialog.dismiss();

                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){

                    Log.e("datas",dataSnapshot1+"");
                    String classTimeHandler=dataSnapshot1.getKey();
                    ClassTime_handler classTime_handler = dataSnapshot1.getValue(ClassTime_handler.class);
                    classList.add(classTime_handler);
                }

                adaptarRecyclerView=new AdaptarRecyclerView2(TotalClass.this,classList);
                recyclerView.setAdapter(adaptarRecyclerView);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

/*
        databaseReference.orderByKey().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.e("data",dataSnapshot+"");

                if(dataSnapshot.exists()){
                    progressDialog.dismiss();

                }
                else{
                    progressDialog.dismiss();
                    Toast.makeText(TotalClass.this,"emtpy :(",Toast.LENGTH_LONG).show();

                    Intent intent=new Intent(TotalClass.this,ArchiveClass.class);
                    startActivity(intent);
                }

                progressDialog.dismiss();


                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){



                    ClassTime_handler classTime_handler = dataSnapshot1.getValue(ClassTime_handler.class);

                    classList.add(classTime_handler);


                }


                adaptarRecyclerView=new AdaptarRecyclerView();

                recyclerView.setAdapter(adaptarRecyclerView);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressDialog.dismiss();

                Toast.makeText(TotalClass.this,"Error"+databaseError.getMessage(),Toast.LENGTH_LONG).show();

            }
        });
*/


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.back_layout, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.backId) {


//            mediaPlayer.start();
            Intent intent = new Intent(this, ArchiveClass.class);
            startActivity(intent);
//            finish();


        }


        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {

//        mediaPlayer.start();
        Intent intent = new Intent(this, ArchiveClass.class);
        startActivity(intent);
//        finish();

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

}
