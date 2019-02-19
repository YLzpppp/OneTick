package com.jasoncareter.onetick.mModel;

public class OTRemDataObject {
    private String time ;
    private String place ;
    private String content ;

    public OTRemDataObject ( String time ,String place , String content ){
        this.time = time ;
        this.place = place ;
        this.content = content ;
    }
    public String getTime(){ return time ;}
    public String getPlace(){ return place;}
    public String getContent(){ return content;}
}
