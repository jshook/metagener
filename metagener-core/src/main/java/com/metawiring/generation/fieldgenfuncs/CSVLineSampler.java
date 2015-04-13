package com.metawiring.generation.fieldgenfuncs;

import com.metawiring.annotations.Output;
import com.metawiring.generation.core.HashedDiscreteSamplingAdapter;
import com.metawiring.types.functiontypes.TypedFieldFunction;
import com.opencsv.CSVReader;
import org.apache.commons.math3.distribution.IntegerDistribution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

@Output({String.class})
public class CSVLineSampler implements TypedFieldFunction<String> {
    private final static Logger logger = LoggerFactory.getLogger(CSVLineSampler.class);

    private final String csvfilename;
    private final String fieldname;
    private final String distname;
    private HashedDiscreteSamplingAdapter samplingAdapter;

    private IntegerDistribution itemDistribution;
    private List<String> loadedLines = new ArrayList<>();

    public CSVLineSampler(String csvfilename, String fieldname) {
        this(csvfilename, fieldname, "uniform");
    }

    public CSVLineSampler(String csvFilename, String fieldName, String distname) {
        this.csvfilename = csvFilename;
        this.fieldname = fieldName;
        this.distname = distname;

        loadedLines = loadCSVFieldLines(csvFilename, fieldName);
        samplingAdapter = new HashedDiscreteSamplingAdapter(0, loadedLines.size() - 1, this.distname);
    }

    private List<String> loadCSVFieldLines(String filename, String fieldName) {

        Reader reader = null;
        for (String filePath : new String[]{filename, "data/" + filename}) {
            if (new File(filePath).exists()) {
                try {
                    reader = new FileReader(filePath);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        if (reader == null) {
            InputStream stream = CSVLineSampler.class.getClassLoader().getResourceAsStream(filename);
            reader = new InputStreamReader(stream);
        }

        List<String> lines = new ArrayList<>();
        CharBuffer linesImage;
        linesImage = CharBuffer.allocate(1024 * 1024);

        CSVReader csvReader = new CSVReader(reader);

        String[] headers = null;
        Iterator<String[]> csvIter = csvReader.iterator();

        if (csvIter.hasNext())
            headers = csvIter.next();
        else
            throw new RuntimeException("Short read on CSV file " + filename);

        int selectedHeaderIdx = -1;
        for (int headerIdx = 0; headerIdx < headers.length; headerIdx++) {
            if (headers[headerIdx].toLowerCase().equals(fieldName.toLowerCase())) {
                selectedHeaderIdx = headerIdx;
            }
        }
        if (selectedHeaderIdx == -1) {
            throw new RuntimeException("Could not find header index for fieldname [" + fieldName + "] in csv file [" + filename + "]");
        }

        while (csvIter.hasNext()) {
            lines.add(csvIter.next()[selectedHeaderIdx]);
        }

        return lines;

    }

    @Override
    public String apply(long value) {
        long line = samplingAdapter.sample(value);
        return loadedLines.get((int) line);
    }
}
