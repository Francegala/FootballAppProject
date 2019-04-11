package com.francegala.footballapp;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.LongDef;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
/**
 * Dear Programmer,
 *
 * When I wrote this code, only God and I knew how it worked.
 * Now, only God knows it !
 *
 * Therefore, if you are trying to optimize this routine and
 * it fails (most surely), please increase this counter
 * as a warning for the next person:
 *
 * total_hours_wasted_here: 7
 *
 * Yours sincerely,
 * Francesco Galassi
 **/
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";

    private ArrayList<String> mDataMatch = new ArrayList<>();
    private Context  mContext;
    private int  bool;

    public RecyclerViewAdapter(ArrayList<String> mDataMatch, Context mContext) {
        this.mDataMatch = mDataMatch;
        this.mContext = mContext;
        //this.bool = bool;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_listitem, viewGroup, false);
        ViewHolder holder  = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int i) {
        Log.d(  TAG, "onBindViewHoler: called. ");

        holder.dataMatch.setText(mDataMatch.get(i));

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDataMatch.get(i).substring(0, 4).equals("ID :")) {
                    Log.d(TAG, "onClick: clicked on:" + mDataMatch.get(i));
                    Toast.makeText(mContext, mDataMatch.get(i), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(mContext, PlayerActivity.class);
                    intent.putExtra("MatchID", mDataMatch.get(i).substring(4));
                    mContext.startActivity(intent);
                }

            }
        });
    }
    /**
     *  Hello there!
     *
     *     Nice to see you. I didn't expect you here and
     *     I'm sorry, there is no cake.
     *
     *     Feel free to look and learn,
     *     but please don't steal the whole thing.
     *
     *     Send me a message if you have questions.
     *
     *     Sincerely,
     *     Francesco :)
     *
     **/
    @Override
    public int getItemCount() {
        return mDataMatch.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView dataMatch;
        RelativeLayout relativeLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dataMatch = itemView.findViewById(R.id.dataMatch);
            relativeLayout = itemView.findViewById(R.id.relativeLayout);
        }


    }
}
