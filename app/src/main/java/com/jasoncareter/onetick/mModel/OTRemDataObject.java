package com.jasoncareter.onetick.mModel;

public class OTRemDataObject {
    private String time ;
    private String place ;
    private String content ;
    private String date ;

    public OTRemDataObject ( String time ,String place , String content ,String date){
        this.time = time ;
        this.place = place ;
        this.content = content ;
        this.date = date ;
    }
    public String getTime(){ return time ;}
    public String getPlace(){ return place;}
    public String getContent(){ return content;}
    public String getDate(){ return  date; }
}
