package com.palhackmagic.nextfit.data.model;


import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.ValueFormatter;

public class BigValueFormatter extends ValueFormatter {

    @Override
    public String getAxisLabel(float value, AxisBase axis) {
        return getConvertedValue(value);
    }

    public String getConvertedValue(float value) {
        if (value < 1000) return String.valueOf(value);
        if (value < 1000000) {
            return String.valueOf(getLeadingInt(value, 1000)) + getPrefix(1000);
        }
        if (value < 1000000000) {
            return String.valueOf(getLeadingInt(value, 1000000)) + getPrefix(1000000);
        }
        else return String.valueOf(value);
    }

    public int getLeadingInt(float value, int decimals) {
        return Math.round(value/decimals);
    }

    public String getPrefix(float number) {
        if (number < 1000) return "";
        if (number < 1000000) return "K";
        if (number < 1000000000) return "M";
        else return "";
    }
}
