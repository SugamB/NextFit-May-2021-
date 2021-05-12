package com.palhackmagic.nextfit.data.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Calories implements Parcelable {
    private int goalC;
    private int activityC;
    private int caloriesBMR;
    private int caloriesOut;

    public Calories(int goalC, int activityC, int caloriesBMR, int caloriesOut) {
        this.activityC = activityC;
        this.goalC = goalC;
        this.caloriesBMR = caloriesBMR;
        this.caloriesOut = caloriesOut;
    }

    protected Calories(Parcel in) {
        activityC = in.readInt();
        goalC = in.readInt();
        caloriesBMR = in.readInt();
        caloriesOut = in.readInt();
    }

    public static final Creator<Calories> CREATOR = new Creator<Calories>() {
        @Override
        public Calories createFromParcel(Parcel in) {
            return new Calories(in);
        }

        @Override
        public Calories[] newArray(int size) {
            return new Calories[size];
        }
    };

    public int getActivityC() {
        return activityC;
    }

    public int getGoalC() {
        return goalC;
    }

    public int getCaloriesBMR() {
        return caloriesBMR;
    }

    public int getCaloriesOut() {
        return caloriesOut;
    }

    public void setActivityC(int activityC) {
        this.activityC = activityC;
    }

    public void setGoalC(int goalC) {
        this.goalC = goalC;
    }

    public void setCaloriesBMR(int caloriesBMR) {
        this.caloriesBMR = caloriesBMR;
    }

    public void setCaloriesOut(int caloriesOut) {
        this.caloriesOut = caloriesOut;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(activityC);
        dest.writeInt(goalC);
        dest.writeInt(caloriesBMR);
        dest.writeInt(caloriesOut);
    }
}
