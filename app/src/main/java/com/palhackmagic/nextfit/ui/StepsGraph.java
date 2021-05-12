// USING MPANDROIDCHART LIBRARY THAT IS APACHE 2.0 LICENSED
// NO CHANGE MADE TO ITS SOURCE CODE

package com.palhackmagic.nextfit.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.palhackmagic.nextfit.R;
import com.palhackmagic.nextfit.data.model.BigValueFormatter;
import com.palhackmagic.nextfit.data.model.DateValueFormatter;
import com.palhackmagic.nextfit.data.model.Steps;

import java.util.ArrayList;

public class StepsGraph extends Fragment {

    public ArrayList<Steps> stepsArrayList = new ArrayList<>();
    FirebaseAuth mAuth;
    DatabaseReference mref;
    String userId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_steps_graph, container, false);

        LineChart lineChart = (LineChart) view.findViewById(R.id.chart);
        lineChart.setVisibility(View.INVISIBLE);
        TextView textView = (TextView) view.findViewById(R.id.titleTV);


        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getCurrentUser().getUid();
        mref = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);

        final DatabaseReference stepRef = mref.child("fitbitsteps");
        final ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String date = dataSnapshot.getKey();
                    Integer steps = Integer.parseInt(dataSnapshot.getValue().toString());
                    Log.i("TAG", date + "--> " + steps);
                    stepsArrayList.add(new Steps(date, steps));
                }
                drawGraph(stepsArrayList, view);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        stepRef.addListenerForSingleValueEvent(valueEventListener);

        return view;
    }

    public void drawGraph(ArrayList<Steps> stepsArrayList, View view) {

        LineChart lineChart = (LineChart) view.findViewById(R.id.chart);
        TextView textView = (TextView) view.findViewById(R.id.titleTV);
        Log.i("TAG", "HERE" + stepsArrayList.toString());

        while (stepsArrayList.size() > 31) {
            stepsArrayList.remove(0);
        }


        ArrayList<Entry> entries = new ArrayList<Entry>();
        ArrayList<String> xLables = new ArrayList<>();
        int i = 0;
        for (Steps steps : stepsArrayList) {
            // turn your data into Entry objects
            entries.add(new Entry(i++, steps.getSteps()));
            String date = steps.getDateS();
            String dateLabel = String.valueOf(steps.getIntDate(date));
            xLables.add(dateLabel);
        }

        LineDataSet lineDataSet = new LineDataSet(entries, "");
        int color = ContextCompat.getColor(getActivity(), R.color.chartColor);
        lineDataSet.setColor(color);
//        lineDataSet.setValueTextColor();
        lineDataSet.setFillColor(color);
        lineDataSet.setDrawFilled(true);
        lineDataSet.setDrawValues(false);

        // making graph line smooth
        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);

        LineData lineData = new LineData(lineDataSet);
        lineChart.setData(lineData);
        Description description = lineChart.getDescription();
//        description.setText("Monthly steps");
//        lineChart.setDescription(description);
//        description.setTextSize(20);
        description.setEnabled(false);
        lineChart.setAutoScaleMinMaxEnabled(true);
        //hide label/legend
        lineChart.getLegend().setEnabled(false);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new DateValueFormatter(xLables));
        Log.i("TAG", String.valueOf(xLables.size()));
        Log.i("TAG", String.valueOf(xLables));
        xAxis.setLabelCount(xLables.size()/2, false);
        xAxis.setDrawGridLines(false);
        xAxis.setTextSize(15);
        xAxis.setAxisLineWidth(1.5f);

        YAxis left = lineChart.getAxisLeft();
        left.setValueFormatter(new BigValueFormatter());
        left.setAxisMinimum(0);
        left.setDrawGridLines(false);
        left.setTextSize(15);
        left.setAxisLineWidth(1.5f);

        YAxis right = lineChart.getAxisRight();
        right.setDrawGridLines(false);
        right.setDrawLabels(false);

        //padding at bottom for text that was getting cut off
        lineChart.setExtraBottomOffset(5);

        lineChart.setVisibility(View.VISIBLE);
        lineChart.animateXY(1000,1000);
        lineChart.invalidate();

        String start = stepsArrayList.get(0).dateS;
        String end = stepsArrayList.get(stepsArrayList.size() - 1).dateS;
        textView.setText("Steps from " + start + " to " + end);

    }
}
