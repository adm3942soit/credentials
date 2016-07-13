package com.credentials;

import com.json.helper.JsonHelper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;


/**
 * Created by oksdud on 01.07.2016.
 */
public class Credentials {
    public static final String ADMIN = "admin";
    public static final String MERCHANT = "merchant";
    public static final String AML = "aml";
    private static String current;
    private static String resources;
    private static String credentialsJsonStr;

    static {
        try {
            current = new File(".").getCanonicalPath();
            current = current.replaceAll("\\\\", "/");
            resources = current + "/src/test/resources";
            System.out.println("current = " + current);
            System.out.println("resources = " + resources);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private static String readCredentialsFromFile() {
        File file = new File(resources + File.separator + "credentials.json");
        FileReader reader;
        String fromFile="";
        try {
            reader = new FileReader(file.getAbsolutePath());
            char[] array = new char[(int)file.length()];
            // считаем файл полностью
            reader.read(array);
            fromFile=new String(array);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        System.out.println("fromFile = " + fromFile);
        return fromFile;
    }
    private static JSONArray array;
    public static String getUserName(String key) throws JSONException {
        String userName = "";
        String fromFile = readCredentialsFromFile();

        if (JsonHelper.isJson(fromFile)) {
            String result=JsonHelper.getPartJsonStringFromJsonStringByKey(fromFile,key);
            System.out.println("result = " + result);
            array=JsonHelper.jsonStrToJsonArray(result);
            int i=0;
            for (;i<array.length();i++){
                try {
                    JSONObject object=(JSONObject) array.get(i);
                    if(!object.toString().contains("name"))continue;
                    return (String) object.get("name");
                }catch (Exception ex){
                    ex.printStackTrace();
                }

            }

        }
        return userName;
    }
    public static String getUserPassword(String key) throws JSONException {
        String userPassword = "";
        String fromFile = readCredentialsFromFile();

        if (JsonHelper.isJson(fromFile)) {
            String result=JsonHelper.getPartJsonStringFromJsonStringByKey(fromFile,key);
            System.out.println("result = " + result);
            array=JsonHelper.jsonStrToJsonArray(result);
            int i=0;
            for (;i<array.length();i++){
                try {
                    JSONObject object=(JSONObject) array.get(i);
                    if(!object.toString().contains("password"))continue;
                    return (String) object.get("password");
                }catch (Exception ex){
                    ex.printStackTrace();
                }

            }

        }
        return "";
    }

    public static void main(String[] args) {
        try {
            System.out.println("userName :"+Credentials.getUserName(Credentials.ADMIN));
            System.out.println("userPassword :"+Credentials.getUserPassword(Credentials.ADMIN));
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }
}
