package com.aaronguostudio.getandpost.model;

public class Lesson {
    private int mId;
    private int mLearnerNumber;
    private String mName;
    private String mPicSmall;
    private String mPicBig;
    private String mDescription;

    public Lesson(int id, int learnerNumber, String name, String picSmall, String picBig, String description) {
        mId = id;
        mLearnerNumber = learnerNumber;
        mName = name;
        mPicSmall = picSmall;
        mPicBig = picBig;
        mDescription = description;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public int getLearnerNumber() {
        return mLearnerNumber;
    }

    public void setLearnerNumber(int learnerNumber) {
        mLearnerNumber = learnerNumber;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getPicSmall() {
        return mPicSmall;
    }

    public void setPicSmall(String picSmall) {
        mPicSmall = picSmall;
    }

    public String getPicBig() {
        return mPicBig;
    }

    public void setPicBig(String picBig) {
        mPicBig = picBig;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    @Override
    public String toString() {
        return "Lesson{" +
                "mId=" + mId +
                ", mLearnerNumber=" + mLearnerNumber +
                ", mName='" + mName + '\'' +
                ", mPicSmall='" + mPicSmall + '\'' +
                ", mPicBig='" + mPicBig + '\'' +
                ", mDescription='" + mDescription + '\'' +
                '}';
    }
}
