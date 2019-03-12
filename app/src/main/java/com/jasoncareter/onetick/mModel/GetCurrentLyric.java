package com.jasoncareter.onetick.mModel;

import java.util.ArrayList;

public class GetCurrentLyric {
    private GetLyricArray getTimeAndLyrics;
    private ArrayList<Integer> timearray;
    private ArrayList<String> lyricarray;

    public GetCurrentLyric( GetLyricArray getTimeAndLyrics){
        this.getTimeAndLyrics = getTimeAndLyrics;
        getTimeAndLyrics.setArrayData();
        getTimeAndLyrics.setLyricsArray();
        getTimeAndLyrics.setTimeArray();

        timearray = getTimeAndLyrics.TimeArray;
        lyricarray = getTimeAndLyrics.LyricsArray;
    }

    public String CurrentLyric(int timestamp){
        /*
         *  如果当前时间点介于前一条和后一条之间，返回第一条歌词
         */
        int pre = 0;
        int lat = 1;
        String ret = "";
        while(true) {
            if (timestamp >= timearray.get(pre) && timestamp < timearray.get(lat)) {
                ret = lyricarray.get(pre);
                break;
            }else{
                if( lat < timearray.size() ){
                    pre = lat;
                    lat++;
                }else break;
            }
        }
        return ret;
    }

}
