package com.project.writers;

import com.project.config.Config;

public class WriterFactory {

    private static final String XML = "xml";
    private static final String CSV = "csv";

    public static IWriter createWriter(){
        Config appConfig = Config.getConfig();
        String outputFormat = appConfig.getProperties().getProperty("output");

        if (XML.equals(outputFormat)) {
            return new XmlWriter();
        }
        if (CSV.equals(outputFormat)) {
            return new CsvWriter();
        }

        throw new IllegalStateException("No valid output format configured in properties file");
    }
}
