package com.jasoncareter.onetick.mModel;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.jasoncareter.onetick.mPresenter.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringBufferInputStream;

public class OTRemData {

    private String remFileName = "reminderlists.json" ;
    private Context context ;
    private static final String JSFTime = "time";
    private static final String JSFPlace ="place";
    private static final String JSFContent="content";
    private static final String JSFWholeArr = "reminderLists";
    private static final String JSFRemRecArr = "reminderRecently";
    private static final String JSFRemPerArr = "reminderPermanently";
    private static final String JSFINIT = "{\"reminderLists\": [{\"reminderRecently\": []}, {\"reminderPermanently\": []}]}";
    private JSONObject WholeJsonObj ;
    private JSONArray RemListsArr ;
    private JSONObject RemRecObj ;
    private JSONObject RemPerObj ;
    private JSONArray RemRecArr ;
    private JSONArray RemPerArr ;
    private static final int FlagRec =26 ;
    private static final int FlagPer = 25 ;
    // get method
    public String getJFName(){return remFileName ; }
    public int getFlagRec(){return FlagRec ;}
    public int getFlagPer(){return FlagPer;}
//constructor
    public OTRemData(Context context){
        // for we only call the method createJsonFile() , so the context instance passed in
        // can only be the reminderList.class
        this.context = context ;
    }
    //initial json file
    public void initJson() throws FileNotFoundException {
        FileOutputStream fileOutputStream = context.openFileOutput(remFileName,Context.MODE_PRIVATE );
        String format = JSFINIT ;
        try {
            fileOutputStream.write(format.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //create new JSON file
    public void createJsonFile(){
        File file = new File(context.getFilesDir() ,remFileName);
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //delete JSON file
    public void deleteJson() {
       File file=new File(context.getFilesDir(),remFileName);
       if(file.exists()){ file.delete(); }
    }
    //write object into json file
    public JSONObject writeObj(int flag ,JSONObject newjsonobj, JSONObject wholeobj){
        try{
            WholeJsonObj =  wholeobj ;
            RemListsArr = WholeJsonObj.getJSONArray( JSFWholeArr );
            switch (flag){
                case FlagPer :
                        RemPerObj = RemListsArr.getJSONObject(1);
                        RemPerArr = RemPerObj.getJSONArray(JSFRemPerArr );
                        RemPerArr.put(newjsonobj);
                    break;
                case FlagRec :
                        RemRecObj = RemListsArr.getJSONObject(0);
                        RemRecArr = RemRecObj.getJSONArray(JSFRemRecArr );
                        RemRecArr.put(newjsonobj);
                    break; }

            }catch (JSONException e){
            e.printStackTrace();
            }
        return WholeJsonObj ;
    }
    //new Json Object
    public JSONObject getNewJsonObj(String time, String place ,String content){
        JSONObject  object = new JSONObject();
        try {
            object.put(JSFTime ,time);
            object.put(JSFPlace , place);
            object.put(JSFContent , content);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }

    public String readJsonFile (){
        byte[] bytes = new byte[2048];
        StringBuilder stringBuilder = new StringBuilder();
        int bytecount = 0 ;
        try {
            FileInputStream fileInputStream = context.openFileInput(remFileName);
            while (-1 != (bytecount = fileInputStream.read(bytes ,0,2048))){
                stringBuilder.append( new String(bytes,0,bytecount));
            }
            fileInputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder+"" ;
    }

    public void writeJsonFile(String string){
        try {
            FileOutputStream fileOutputStream = context.openFileOutput(remFileName ,Context.MODE_PRIVATE );
            fileOutputStream.write(string.getBytes());
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
/*
 * add new object:
 * 1. call @readJsonFile() , return the whole string
 * 2. call @getNewJsonObj() , get a new object contains information from user
 * 3. call @writeObj ()  , add the new object into  the whole json file
 * 4. call @writeJsonFile , finish information update action
 */



