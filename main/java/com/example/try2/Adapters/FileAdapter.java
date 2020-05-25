package com.example.try2.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.try2.Files.Files;
import com.example.try2.R;
import com.example.try2.Activities.SecondActivity;

import java.util.ArrayList;

public class FileAdapter extends RecyclerView.Adapter<FileAdapter.FileViewHolder> {

    Activity activity;
    ArrayList<Files> files;

    public FileAdapter (ArrayList<Files> files, Activity activity) {

        this.activity = activity;
        this.files = files;
    }

    public class FileViewHolder extends RecyclerView.ViewHolder{

        TextView str;
        ImageButton im;

        public FileViewHolder (View view) {

            super(view);
            str = (TextView) view.findViewById(R.id.text);
            im = (ImageButton) view.findViewById(R.id.imageButton);
        }

    }

    @NonNull
    @Override
    public FileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new FileViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final FileViewHolder holder, final int position) {

        holder.str.setText(files.get(position).getFiles());
        holder.im.setImageResource(R.drawable.arrow);

        holder.im.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                SecondActivity.putFiles(position);
                SecondActivity.sgive(files);
                Intent intent = new Intent(view.getContext(), SecondActivity.class);
                activity.startActivity(intent);
                activity.overridePendingTransition(R.anim.in, R.anim.out);
            }
        });
    }


    @Override
    public int getItemCount() {
        return files.size();
    }

}