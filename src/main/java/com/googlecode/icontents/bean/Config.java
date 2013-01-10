package com.googlecode.icontents.bean;

public class Config {
    public static String INDEX = "config_index";
    public static String NAME = "config_name";
    
    private String key;
    private String value;
    
    public Config(){}
    
    public Config(String key, String value){
        this.key = key;
        this.value = value;
    }
    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }
}
