package com.jasoncareter.onetick.mModel;

public class OTAccDataObject {
    private String platform ;
    private String account ;
    private String password ;

    public OTAccDataObject(String platform ,String account ,String password){
        this.platform = platform ;
        this.account = account ;
        this.password = password ;
    }

    public String getPlatform(){
        return platform ;
    }
    public String getAccount(){
        return account ;
    }
    public String getPassword(){
        return password ;
    }
}
