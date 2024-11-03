package com.hkcycleclub.email;

import org.apache.commons.lang.WordUtils;

public class Recipient {

    private String fullName;
    private String emailAddress;

    public String getFullName() {
        return fullName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setFullName(String fullName) {
        this.fullName = WordUtils.capitalizeFully(fullName);
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    @Override
    public String toString() {
        return fullName + " <" + emailAddress + ">";
    }
}
