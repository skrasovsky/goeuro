package com.goeuro.service.csv;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;

import static com.goeuro.service.logger.CustomConsoleLogger.error;
import static com.goeuro.service.logger.CustomConsoleLogger.info;
import static com.goeuro.service.logger.CustomConsoleLogger.message;
import static com.goeuro.service.logger.CustomConsoleLogger.warn;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Record data to the csv file and save.
 */
@Service
public class CSVService {

    private final char DELIMITER = ',';

    private final int defaultBatchSize = 100;

    private int batchSize = 0;

    /**
     * Store data to the csv file.
     *
     * @param entities The entities which will be stored in csv file
     * @param headers The csv file headers
     * @param fileName The csv file name
     */
    public void createCSVFile(Collection<Collection<Object>> entities, String[] headers, String fileName) {

        CSVPrinter printer = null;
        try {
            CSVFormat format = prepareFormat();
            printer = new CSVPrinter(new FileWriter(fileName), format);
            printer.printRecord(headers);

            int i = 0;
            for (Collection<Object> entity : entities) {
                printer.printRecord(entity);
                i++;
                if (i % getBatchSize() == 0) {
                    printer.flush();
                }
            }

            info(String.format("Created new CSV file: ./%s", fileName));
        } catch (IOException e) {
            String message = message("Obtain error while creating CSV file %s", fileName);
            error(message);
            try {
                Files.deleteIfExists(Paths.get(fileName));
            } catch (IOException ex) {
                warn(String.format("Obtain error while deleting CSV file %s", fileName));
            }
            throw new CSVFileCreationException(message, e);

        } finally {
            IOUtils.closeQuietly(printer);
        }
    }

    private CSVFormat prepareFormat() {
        return CSVFormat
                .RFC4180
                .withHeader()
                .withDelimiter(DELIMITER);
    }

    /**
     * Return a batch size of inserting row. If <code>batchSize</code> is 0,
     * than default value of batch size will be return.
     *
     * @return The batch size <code>batchSize</code>
     */
    public int getBatchSize() {
        if (batchSize != 0) {
            return batchSize;
        }
        return defaultBatchSize;
    }

    /**
     * Set the batch size.
     *
     * @param batchSize The <code>batchSize</code>
     */
    public void setBatchSize(int batchSize) {
        assertThat(batchSize).isNegative();
        assertThat(batchSize).isGreaterThan(Integer.MAX_VALUE);

        this.batchSize = batchSize;
    }

}
