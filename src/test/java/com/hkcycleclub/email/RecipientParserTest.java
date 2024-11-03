package com.hkcycleclub.email;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class RecipientParserTest {

    @Test
    void testParseRecipientCsv() throws Exception {
        List<Recipient> recipientList = RecipientParser.parseCsv("/list.csv");

        assertThat(recipientList).hasSize(3);

        assertThat(recipientList.get(0).getFullName()).isEqualTo("Soifur Rahman");
        assertThat(recipientList.get(0).getEmailAddress()).isEqualTo("soif_1st@yahoo.co.uk");

        assertThat(recipientList.get(1).getFullName()).isEqualTo("Muhib Rakeeb");
        assertThat(recipientList.get(1).getEmailAddress()).isEqualTo("muhib_rakeeb@hotmail.com");

        assertThat(recipientList.get(2).getFullName()).isEqualTo("Mehboob Patel");
        assertThat(recipientList.get(2).getEmailAddress()).isEqualTo("pmebs@yahoo.com");
    }
}