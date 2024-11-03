package com.hkcycleclub.email;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RecipientTest {

    @Test
    void testToString() {
        Recipient recipient = new Recipient();
        recipient.setFullName("mashrur mia");
        recipient.setEmailAddress("mashrur.mia@gmail.com");

        String actualToString = recipient.toString();

        assertThat(actualToString).isEqualTo("mashrur mia <mashrur.mia@gmail.com>");
    }

}