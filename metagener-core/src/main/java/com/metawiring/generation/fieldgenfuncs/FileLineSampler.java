package com.metawiring.generation.fieldgenfuncs;

import com.metawiring.generation.core.HashedSamplingAdapter;
import com.metawiring.types.functiontypes.TypedFieldFunction;
import org.apache.commons.math3.distribution.IntegerDistribution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FileLineSampler implements TypedFieldFunction<String> {
    private final static Logger logger = LoggerFactory.getLogger(FileLineSampler.class);

    private final String filename;
    private final String distname;
    private HashedSamplingAdapter samplingAdapter;

    private IntegerDistribution itemDistribution;
    private List<String> loadedLines = new ArrayList<>();

    public FileLineSampler(String filename) {
        this(filename, "uniform");
    }

    public FileLineSampler(String filename, String distname) {
        this.filename = filename;
        this.distname = distname;
        loadedLines = loadLines(filename);
        samplingAdapter = new HashedSamplingAdapter(0, loadedLines.size() - 1, this.distname);
    }

    private List<String> loadLines(String fileName) {

        List<String> lines = new ArrayList<>();

        InputStream stream = FileLineSampler.class.getClassLoader().getResourceAsStream(filename);
        if (stream == null) {
            throw new RuntimeException(filename + " was missing.");
        }

        CharBuffer linesImage;
        try {
            InputStreamReader isr = new InputStreamReader(stream);
            linesImage = CharBuffer.allocate(1024 * 1024);
            isr.read(linesImage);
            isr.close();
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
        linesImage.flip();
        Collections.addAll(lines, linesImage.toString().split("\n"));
        return lines;

    }

    @Override
    public String apply(long value) {
        long line = samplingAdapter.sample(value);
        return loadedLines.get((int) line);
    }
}
