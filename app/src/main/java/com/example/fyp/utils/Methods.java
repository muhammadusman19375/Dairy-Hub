package com.example.fyp.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.fyp.R;
import com.example.fyp.models.SymptomsModel;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.concurrent.TimeUnit;

public class Methods {
    public static Bitmap base64ToBitmap(String encodedString) {
        byte[] decodedString = new byte[0];
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            decodedString = Base64.getDecoder().decode(encodedString);
        }
        Bitmap bitmap= BitmapFactory.decodeByteArray(decodedString , 0,
                decodedString.length);
        return bitmap;
    }

    public static String encodeBitmapTobase64(Bitmap image) {
        Log.d("TAG", "encodeBitmapTobase64: "+image);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        String encodedImageString = "";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            encodedImageString = Base64.getEncoder().encodeToString(byteArray);
        }
        return encodedImageString ;
    }
    public static long getDateDiff(SimpleDateFormat format, String oldDate, String newDate) {
        try {
            return TimeUnit.DAYS.convert(format.parse(newDate).getTime() - format.parse(oldDate).getTime(), TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static String insemDate(String currentDate){
        String dueDate;
        String[] dates = currentDate.split("/");
        int date = Integer.parseInt(dates[0]);

        int month = Integer.parseInt(dates[1]);
        int year = Integer.parseInt(currentDate.substring(currentDate.length()-4));
        Log.d("TAG", "insemDate: "+year);
        Log.d("TAG", "insemDate: "+date);
        Log.d("TAG", "insemDate: "+currentDate);
//        Log.d("TAG", "insemDate: "+month);
//        Log.d("TAG", "insemDate: "+year);
        date = date+4;
            if (month==1||month==3||month==5||month==7||month==8||month==10||month==12){
                if (date>31){
                    month = month+1;
                    date = 31-date;
                }
        }else{
                if (date>30){
                    month = month+1;
                    date = 30-date;
                }
            }
        month = month+9;
        if (month>12){
            year = year+1;
            month = month-12;
        }
        dueDate = date+"/"+month+"/"+year;
        return dueDate;
    }

    public static String[] readDiseases(Context context){
        String[]  diseases;
        int position = 0;
        diseases = new String[11];
        InputStream inputStream = context.getResources().openRawResource(R.raw.disease_information);
        BufferedReader bufferedReader= new BufferedReader(new InputStreamReader(inputStream));
        String eachline = null;
        try {
            eachline = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (eachline != null) {
            // `the words in the file are separated by space`, so to get each words
            diseases[position] = (eachline);
            position++;
            try {
                eachline = bufferedReader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Log.d("TAG", "readDiseases: "+diseases.length);

        return diseases;
    }
    public static ArrayList<SymptomsModel> readSymptoms(Context context, InputStream inputStream){
        String[]  diseases;
        ArrayList<SymptomsModel> diseasesList;
        diseasesList = new ArrayList<>();
        int position = 0;
        diseases = new String[11];
        BufferedReader bufferedReader= new BufferedReader(new InputStreamReader(inputStream));
        String eachline = null;
        try {
            eachline = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (eachline != null) {
            // `the words in the file are separated by space`, so to get each words
            diseases[position] = (eachline);
            SymptomsModel symptomsModel = new SymptomsModel();
            symptomsModel.setSymptomName(eachline);
            symptomsModel.setChecked(false);
            diseasesList.add(symptomsModel);
            Log.d("TAG", "readDiseases: "+eachline);
            position++;
            try {
                eachline = bufferedReader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return diseasesList;
    }
}
