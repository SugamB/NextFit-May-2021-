package com.palhackmagic.nextfit.data.model;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.ArrayList;

public class DateValueFormatter extends ValueFormatter {
    private ArrayList<String> labels;

    public DateValueFormatter(ArrayList<String> labels) {
        this.labels = labels;
    }

    @Override
    public String getAxisLabel(float value, AxisBase axis) {
        return labels.get((int) value);
    }
}
