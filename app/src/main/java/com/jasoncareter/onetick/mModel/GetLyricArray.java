package com.jasoncareter.onetick.mModel;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetLyricArray {

    /**
     *  calling the setArrayData() before you tried to call the getTimeArray() or getLyricsArray()
     */
    public ArrayList<Integer> TimeArray = new ArrayList<>() ;
    public ArrayList<String> LyricsArray = new ArrayList<>();
    public ArrayList<String> Lyrics  = new ArrayList<>();

    private Activity activity ;
    private static final String myregex = "\\[(\\d{2}):(\\d{2})\\.(\\d{2})\\]";
    private Pattern pattern ;
    private Matcher matcher ;

    public GetLyricArray(Activity activity){
        this.activity = activity ;
    }

    public void  setArrayData() {
        AssetManager assetManager = activity.getAssets();
        try {
            InputStream inputStream = assetManager.open("music/itwouldntberight.lrc"  );
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String OneLine  = bufferedReader.readLine();
            while (OneLine.length()>0){
                Lyrics.add(OneLine);
                OneLine = bufferedReader.readLine();
            }
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void setTimeArray() {
        pattern = Pattern.compile(myregex );
        int LyricsLength = Lyrics.size();
        for (int i = 0 ; i < LyricsLength ; i++) {
            matcher = pattern.matcher(Lyrics.get(i));
            if (matcher.find()) {
                int minutes = Integer.valueOf(matcher.group(1));
                int seconds = Integer.valueOf(matcher.group(2));
                int miliseconds = Integer.valueOf(matcher.group(3));
                int totalmilli = miliseconds + seconds * 1000 + minutes * 60 * 1000;
                TimeArray.add(totalmilli);
            }
        }
//        return TimeArray;
    }

    public void setLyricsArray() {
        int LyricsLength = Lyrics.size();
        for (int i = 0; i<LyricsLength ; i++){
            String[] tempstr = (Lyrics.get(i)).split("]");
            LyricsArray.add( tempstr[1] );
        }
//        return LyricsArray;
    }

    public void setAll(){
        setArrayData();
        setLyricsArray();
        setTimeArray();
    }
}