package com.razzies.golden.raspberry.awards;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Component
public class CsvFileReader {

    private static final Logger LOGGER = LoggerFactory.getLogger(CsvFileReader.class);

    public <T> List<T> read(Class<T> type, String fileName) {
        try {
            LOGGER.info("Starting read {} file", fileName);
            CsvMapper mapper = new CsvMapper();
            CsvSchema schema = mapper
                    .typedSchemaFor(type)
                    .withHeader()
                    .withColumnSeparator(';');
            MappingIterator<T> iterator = mapper
                    .readerWithTypedSchemaFor(type)
                    .with(schema)
                    .readValues(getDecodedFile(fileName));
            LOGGER.info("Finish read {} file", fileName);
            return iterator.readAll();
        } catch (IOException e) {
            LOGGER.error("Error while loading list from file {}", fileName, e);
            return Collections.emptyList();
        }
    }

    private BufferedReader getDecodedFile(String fileName) {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName);
        return new BufferedReader(new InputStreamReader(
                Objects.requireNonNull(inputStream, "Error reading file"),
                StandardCharsets.UTF_8));
    }
}