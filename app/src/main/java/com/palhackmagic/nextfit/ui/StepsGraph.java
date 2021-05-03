package com.palhackmagic.nextfit.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.palhackmagic.nextfit.R;
import com.palhackmagic.nextfit.data.model.BigValueFormatter;
import com.palhackmagic.nextfit.data.model.DateValueFormatter;
import com.palhackmagic.nextfit.data.model.Steps;

import java.util.ArrayList;

public class StepsGraph extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps_graph);

        LineChart lineChart = (LineChart) findViewById(R.id.chart);
        TextView textView = (TextView) findViewById(R.id.titleTV);


        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("BUNDLE");

        ArrayList<Steps> stepsArrayList = (ArrayList<Steps>) bundle.getSerializable("StepsArrayList");
        ArrayList<Entry> entries = new ArrayList<Entry>();
        ArrayList<String> xLables = new ArrayList<>();
        int i = 1;
        for (Steps steps : stepsArrayList) {
            // turn your data into Entry objects
            entries.add(new Entry(i++, steps.getSteps()));
            String date = steps.getDateS();
            String dateLabel = String.valueOf(steps.getIntDate(date));
            xLables.add(dateLabel);
        }

        LineDataSet lineDataSet = new LineDataSet(entries, "");
        int color = ContextCompat.getColor(getApplicationContext(), R.color.chartColor);
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
//        xAxis.setDrawGridLines(false);
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

        lineChart.animateXY(1000,1000);
        lineChart.invalidate();

        textView.setText("");

        final TextView yAxisTitle = findViewById(R.id.yAxisTitle);
        // doesnt work, returns wrap content
        int height = yAxisTitle.getLayoutParams().height;
        int width = yAxisTitle.getLayoutParams().width;
        //assigning opposite values because layout is vertical
        Log.i("P", height + " ------ " + width);
        yAxisTitle.setLayoutParams(new LinearLayout.LayoutParams(height, width));
        Log.i("P", height + " -- " + width);
    }
}