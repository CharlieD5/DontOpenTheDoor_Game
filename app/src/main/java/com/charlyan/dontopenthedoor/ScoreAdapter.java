package com.charlyan.dontopenthedoor;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import java.util.List;

/**
 * Created by lynnreilly on 12/3/17.
 */

public class ScoreAdapter extends ArrayAdapter<Scores> {
    public ScoreAdapter(@NonNull Context context, List<Scores> scores) {
        super(context, 0, scores);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(getContext())
                .inflate(R.layout.list_row, parent, false);
        TextView nameView = convertView.findViewById(R.id.name);
        TextView scoreView = convertView.findViewById(R.id.score);
        Scores record = getItem(position);
        nameView.setText(record.getName());
        scoreView.setText(record.getScore());
        return convertView;
    }
}