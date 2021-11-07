package com.buraktan.githubapi.util;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.FileWriter;
import java.io.IOException;

public class CSVWriter {

    public void writer(String fileName, Iterable<Object> list, String[] header) throws IOException {


        CSVPrinter printer = new CSVPrinter(new FileWriter(fileName + ".csv"), CSVFormat.EXCEL.withFirstRecordAsHeader());
        printer.printRecord(header);
        for (Object o : list) {
            printer.printRecord(o);

        }
        printer.flush();
        printer.close();

    }

}
