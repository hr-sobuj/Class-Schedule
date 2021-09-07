package com.classschedule.classtime;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.chrisbanes.photoview.PhotoView;

import java.util.List;

public class AdaptarRecyclerView2 extends RecyclerView.Adapter<AdaptarRecyclerView2.MyHolderView>{


    private Context context;
    private List<ClassTime_handler> uploadList;

    public AdaptarRecyclerView2(Context context, List<ClassTime_handler> uploadList) {
        this.context = context;
        this.uploadList = uploadList;
    }

    @NonNull
    @Override
    public MyHolderView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater=LayoutInflater.from(context);
        View v=inflater.inflate(R.layout.item_layout,parent,false);

        return new MyHolderView(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolderView holder, int position) {


        ClassTime_handler classTimeHandler = uploadList.get(position);

        holder.textView.setText(classTimeHandler.getDate());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // display a toast with person name on item click
                Intent intent = new Intent(context, ClassSearchingResult.class);
                intent.putExtra("date", classTimeHandler.getDate());
                intent.putExtra("key", classTimeHandler.getKey());
                context.startActivity(intent);
//                Toast.makeText(context, classTimeHandler.getDate(), Toast.LENGTH_SHORT).show();
            }
        });

  /*      Picasso.get().load(upload.getUrl())

                .placeholder(R.drawable.loading)
                .into(holder.imageView);*/

    }

    @Override
    public int getItemCount() {
        return uploadList.size();
    }


    public class MyHolderView extends RecyclerView.ViewHolder {

//        PhotoView imageView;
TextView textView;

        public MyHolderView(@NonNull View itemView) {
            super(itemView);


            textView=itemView.findViewById(R.id.textid);

        }
    }
}
