package com.example.try2.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.try2.Files.Files;
import com.example.try2.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class SecondFileAdapter extends RecyclerView.Adapter<SecondFileAdapter.SecondFileViewHolder> {

    ArrayList<Files> files;

    public SecondFileAdapter (ArrayList<Files> files) {
        this.files = files;
    }

    public class SecondFileViewHolder extends RecyclerView.ViewHolder{

        TextView strTop;
        TextView str;

        public SecondFileViewHolder (View view) {

            super(view);
            strTop = (TextView) view.findViewById(R.id.textTop);
            str = (TextView) view.findViewById(R.id.text);
        }

    }

    @NonNull
    @Override
    public SecondFileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.second_list_item, parent, false);
        return new SecondFileViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final SecondFileViewHolder holder, final int position) {

        holder.strTop.setText(files.get(position).getFilesTop());
        holder.str.setText(files.get(position).getFiles());
    }


    @Override
    public int getItemCount() {
        return files.size();
    }

}