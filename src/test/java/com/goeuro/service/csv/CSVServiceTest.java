package com.goeuro.service.csv;

import com.goeuro.config.ApplicationTestConfig;
import com.goeuro.service.csv.CSVService;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

@SpringApplicationConfiguration(classes = ApplicationTestConfig.class)
public class CSVServiceTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private CSVService csvService;

    private String[] headers = {"_id", "name", "type", "latitude", "longitude" };

    private String fileName = "src/test/data/locations.csv";

    @Test
    public void should_create_csv_file_with_data() {

        Collection<Collection<Object>> inputData = prepareCSVData();
        csvService.createCSVFile(inputData, headers, fileName);

        File file = new File(fileName);
        assertTrue(file.isFile());

        FileReader fileReader = null;
        CSVParser csvFileParser = null;
        CSVFormat format = CSVFormat
                .RFC4180
                .withHeader()
                .withDelimiter(',');

        try {
            fileReader = new FileReader(fileName);
            csvFileParser = new CSVParser(fileReader, format);
            List outputData = csvFileParser.getRecords();

            assertEquals(outputData.size(), inputData.size() + 1);
        } catch (IOException e) {
            fail("Something happened during testing creation of csv", e);
        } finally {
            IOUtils.closeQuietly(fileReader);
            IOUtils.closeQuietly(csvFileParser);
            file.delete();
        }
    }

    private Collection<Collection<Object>> prepareCSVData() {

        Collection<Collection<Object>> list = new ArrayList<>(2);
        list.add(Arrays.asList("123", "Berlin", "LOCATION", "13.41053", "52.52437"));
        list.add(Arrays.asList("123", "Munich", "AIRPORT", "23.41053", "12.52437"));

        return list;
    }
}
