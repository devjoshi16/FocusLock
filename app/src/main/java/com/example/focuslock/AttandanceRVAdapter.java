package com.example.focuslock;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AttandanceRVAdapter extends RecyclerView.Adapter<AttandanceRVAdapter.ViewHolder> {
    // creating variables for our ArrayList and context
    private ArrayList<Attandance> coursesArrayList;
    private Context context;

    // creating constructor for our adapter class
    public AttandanceRVAdapter(ArrayList<Attandance> coursesArrayList, Context context) {
        this.coursesArrayList = coursesArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // passing our layout file for displaying our card item
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.attandence_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // setting data to our text views from our modal class.
        Attandance Attandance = coursesArrayList.get(position);
        holder.courseNameTV.setText(Attandance.getCourseName());
        holder.courseDurationTV.setText(Attandance.getCourseDuration());
        holder.courseDescTV.setText(Attandance.getCourseDescription());
    }

    @Override
    public int getItemCount() {
        // returning the size of our array list.
        return coursesArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        // creating variables for our text views.
        private final TextView courseNameTV;
        private final TextView courseDurationTV;
        private final TextView courseDescTV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing our text views.
            courseNameTV = itemView.findViewById(R.id.idTVCourseName);
            courseDurationTV = itemView.findViewById(R.id.idTVCourseDuration);
            courseDescTV = itemView.findViewById(R.id.idTVCourseDescription);
        }
    }
}

