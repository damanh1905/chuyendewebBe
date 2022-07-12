package com.example.chuyendeweb.model.response;

import java.util.Comparator;

public class AccordingMarks implements Comparator<String> {
    public int compare(String s1, String s2) {
        return -(s2.compareTo(s1));
    }
}
