/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

/**
 *
 * @author nguye
 */
public class UserError {
    private String emailError;
    private String passwordError;
    private String fullnameError;
    private String confirmError;
    private String error;

    public UserError() {
        this.emailError="";
        this.passwordError="";
        this.fullnameError="";
        this.confirmError="";
        this.error="";
    }
    
    public UserError(String emailError, String passwordError, String fullnameError, String confirmError, String error) {
        this.emailError = emailError;
        this.passwordError = passwordError;
        this.fullnameError = fullnameError;
        this.confirmError = confirmError;
        this.error = error;
    }

    public String getEmailError() {
        return emailError;
    }

    public void setEmailError(String emailError) {
        this.emailError = emailError;
    }
   
  

    public String getPasswordError() {
        return passwordError;
    }

    public void setPasswordError(String passwordError) {
        this.passwordError = passwordError;
    }

    public String getFullnameError() {
        return fullnameError;
    }

    public void setFullnameError(String fullnameError) {
        this.fullnameError = fullnameError;
    }

    public String getConfirmError() {
        return confirmError;
    }

    public void setConfirmError(String confirmError) {
        this.confirmError = confirmError;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
    
}
