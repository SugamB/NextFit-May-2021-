package com.palhackmagic.nextfit.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.jetbrains.annotations.NotNull;


public class Steps implements Parcelable {
    public int date;
    public int steps;
    public String dateS;


    public Steps(String date, int steps) {
        this.date = getIntDate(date);
        this.steps = steps;
        this.dateS = date;
    }

    protected Steps(Parcel in) {
        date = in.readInt();
        steps = in.readInt();
        dateS = in.readString();
    }

    public static final Creator<Steps> CREATOR = new Creator<Steps>() {
        @Override
        public Steps createFromParcel(Parcel in) {
            return new Steps(in);
        }

        @Override
        public Steps[] newArray(int size) {
            return new Steps[size];
        }
    };

    public int getDate() {
        return date;
    }

    public int getSteps() {
        return steps;
    }

    public void setDate(String date) {
        this.date = getIntDate(date);
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    public void setDateS(String dateS) {
        this.dateS = dateS;
    }

    public String getDateS() {
        return dateS;
    }

    public int getIntDate(@NotNull String date) {
        String[] temp = date.split("-");
        return Integer.parseInt(temp[2]);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(date);
        dest.writeInt(steps);
        dest.writeString(dateS);
    }
}
