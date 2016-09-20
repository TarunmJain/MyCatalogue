package com.centura_technologies.mycatalogue.Support;

import android.text.TextUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by parandam on 07/07/15.
 */
public class Validation {

    public static boolean isValidName(String name) {
        if(name.length()<4&&name.length()>25)
            return false;
        String UserName_Pattern = "^[a-zA-Z0-9]+([._ ]?[a-zA-Z0-9]+)*$";
        Pattern pattern = Pattern.compile(UserName_Pattern);
        Matcher matcher = pattern.matcher(name);
        return matcher.matches();
    }

      public static boolean isMobileNumberValid(CharSequence phone){
        if(TextUtils.isEmpty(phone)){
            return false;
        }
          else
        {
            String Phone_Pattern = "^[+]?[0-9]*$";
            Pattern pattern = Pattern.compile(Phone_Pattern);
            Matcher matcher = pattern.matcher(phone);
            return (matcher.matches());
        }
    }

    public static boolean isBirthDayValid(String birthdate){
        if(birthdate==null||birthdate.matches(""))
            return true;
        Date today= new Date();
        DateFormat dateFormat = new SimpleDateFormat("d/M/yyyy");
        String todayStr=dateFormat.format(today);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd h:m");
        try {
            if(dateFormat.parse(birthdate).after(dateFormat.parse(todayStr)))
            {
                return false;
            }
            else return true;
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean isPasswordCompareValid(String password,String confirmPassword){
        if(password != null)
        if(password.matches(confirmPassword) && !password.matches("") ){
            return true;
        }else {
            return false;
        }
        else return false;
    }
    public static boolean isPasswordLengthValid(CharSequence password){
        if(password != null && password.length() >= 6 && password.length()<=10  ){
            return true;
        }else {
            return false;
        }
    }
    public static boolean isEmailValid(CharSequence email)
    {
        if (TextUtils.isEmpty(email))
        {
            return true;
        }else
        {
            Pattern pattern;
            Matcher matcher;
            final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
            pattern = Pattern.compile(EMAIL_PATTERN);
            matcher = pattern.matcher(email);
            return matcher.matches();
        }
    }
    public static boolean isEditTextViewValid(CharSequence text)
    {
        if(TextUtils.isEmpty(text)) {
            return false;
        }
        return true;
    }
    public static boolean isQuantityValid(CharSequence text) {
        if(TextUtils.isEmpty(text)) {
            return false;
        }
        else if(Integer.parseInt(text.toString()) == 0) {
            return false;
        }
        return true;
    }
}
