package com.hkcycleclub.email;

import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.constraint.StrRegEx;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.prefs.CsvPreference;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class RecipientParser {
    public static List<Recipient> parseCsv(String filename) throws IOException {

        List<Recipient> retVal = new ArrayList<>();

        Reader inputReader = new InputStreamReader(RecipientParser.class.getResourceAsStream(filename));
        CsvBeanReader csvBeanReader =
                new CsvBeanReader(inputReader, CsvPreference.STANDARD_PREFERENCE);
        csvBeanReader.getHeader(true);

        final CellProcessor[] processors = getProcessors();

        final String[] header = {
                "fullName",
                "emailAddress"
        };

        Recipient applicant;
        while ((applicant = csvBeanReader.read(Recipient.class, header, processors)) != null) {
            retVal.add(applicant);
        }

        return retVal;
    }

    private static CellProcessor[] getProcessors() {

        final String emailRegex = "[a-z0-9\\._]+@[a-z0-9\\.]+"; // just an example, not very robust!
        StrRegEx.registerMessage(emailRegex, "must be a valid email address");

        return new CellProcessor[]{
                new NotNull(), // Full Name
                new NotNull()  // Email address
        };
    }
}
