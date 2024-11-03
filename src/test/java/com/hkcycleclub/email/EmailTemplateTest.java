package com.hkcycleclub.email;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;


class EmailTemplateTest {

    @Test
    void testGenerate() throws Exception {
        EmailTemplate emailTemplate = new EmailTemplate("index.vm");

        Map<String, String> variables = new HashMap<>();

        variables.put("name", "Sa'ad");

        String actual = emailTemplate.generate(variables);

        assertThat(actual).isEqualTo("Salam Sa'ad");

    }

}