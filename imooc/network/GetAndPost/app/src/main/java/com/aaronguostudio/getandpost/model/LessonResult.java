package com.aaronguostudio.getandpost.model;

import java.util.List;

public class LessonResult {
    private int mStatus;
    private List<Lesson> mLessons;

    public int getStatus() {
        return mStatus;
    }

    public void setStatus(int status) {
        mStatus = status;
    }

    public List<Lesson> getLessons() {
        return mLessons;
    }

    public void setLessons(List<Lesson> lessons) {
        mLessons = lessons;
    }

    @Override
    public String toString() {
        return "LessonResult{" +
                "mStatus=" + mStatus +
                ", mLessons=" + mLessons +
                '}';
    }
}