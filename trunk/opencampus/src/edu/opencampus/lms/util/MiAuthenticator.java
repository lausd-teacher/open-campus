package edu.opencampus.lms.util;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class MiAuthenticator extends Authenticator{
    
    public PasswordAuthentication getPasswordAuthentication(){
        return new PasswordAuthentication("campusv4","c4mpusv4");
    }
    
}