package com.example.mp_8;


import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;



public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private GradeData[] mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView nameView;
        public TextView gradeView;
        public TextView sNameView;
        public MyViewHolder(View v) {
            super(v);
            nameView = v.findViewById(R.id.cName);
            gradeView =v.findViewById(R.id.grade);
            sNameView =v.findViewById(R.id.sName);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(GradeData[] myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        View v =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_text_layout, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.nameView.setText(mDataset[position].coName);
        holder.gradeView.setText(mDataset[position].grade);
        String sName;
        if(mDataset[position].name.equals("")){
            sName="";
        }
        else{
            sName="Name: "+mDataset[position].name +" ";
        }
        holder.sNameView.setText(sName);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.length;
    }
}