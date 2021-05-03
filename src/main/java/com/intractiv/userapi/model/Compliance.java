package com.intractiv.userapi.model;

import java.util.regex.Pattern;

public class Compliance {
   private static final Pattern digitRegex = Pattern.compile("^(?=.*\\d).+$");
   private static final Pattern specialRegex = Pattern.compile("^(?=.*[!#$%&@]).+$");
   private static final Pattern upperCaseRegex = Pattern.compile("^(?=.*[A-Z]).+$");

   public static final String MESSAGE_PASSWORD_OK = "Password is OK!";
   public static final String MESSAGE_USER_FOUND = "User exists!";
   public static final String MESSAGE_USER_CREATED = "User created!";
   public static final String ERROR_INVALID = "Invalid password!";
   public static final String ERROR_USER_NULL = "User can not be NULL!";
   public static final String ERROR_USER_EXISTS = "User already exist!";
   public static final String ERROR_USER_NOTFOUND ="User not found!";
   public static final String ERROR_NULL = "Password can not be NULL!";
   public static final String ERROR_LENGTH ="Password must have at least 8 characters!";
   public static final String ERROR_DIGIT ="Password must have at least 1 digit!";
   public static final String ERROR_SPECIAL_CHAR ="Password must have at least 1 special character!";
   public static final String ERROR_UPPERCASE ="Password must have at least 1 uppercase letter!";

   private Boolean isValid;
   private String reason;

   public Compliance() {
   }

   public Compliance(Boolean isValid, String reason) {
      this.isValid = isValid;
      this.reason = reason;
   }

   // constructor that get string to analyse validity
   public Compliance(String s) {
      isValid(s);
   }

   public Boolean isValid() {
      return isValid;
   }

   // initialise compliance with string analyse result
   public Boolean isValid(String s) {

      if( s == null) {
         this.isValid = false;
         this.reason = ERROR_NULL;
      } else if ( s.length() < 8) {
         this.isValid = false;
         this.reason = ERROR_LENGTH;
      } else if ( !digitRegex.matcher(s).matches()) {
         this.isValid = false;
         this.reason = ERROR_DIGIT;
      } else if ( !specialRegex.matcher(s).find()) {
         this.isValid = false;
         this.reason = ERROR_SPECIAL_CHAR;
      } else if ( !upperCaseRegex.matcher(s).matches()) {
         this.isValid = false;
         this.reason = ERROR_UPPERCASE;
      } else {
         this.isValid = true;
         this.reason =MESSAGE_PASSWORD_OK;
      }

      return isValid;
   }

   public void setValid(Boolean valid) {
      isValid = valid;
   }

   public String getReason() {
      return reason;
   }

   public void setReason(String reason) {
      this.reason = reason;
   }
}
