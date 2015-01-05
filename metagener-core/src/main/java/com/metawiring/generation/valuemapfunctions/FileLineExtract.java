package com.metawiring.generation.valuemapfunctions;

import com.metawiring.annotations.FieldFunctionSignature;
import com.metawiring.generation.FieldFunction;
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

@FieldFunctionSignature(input=Long.class,output=String.class)
public class FileLineExtract implements FieldFunction<Long,String> {
    private final static Logger logger = LoggerFactory.getLogger(FileLineExtract.class);

    private String filename;
    private List<String> lines = new ArrayList<>();
    private IntegerDistribution itemDistribution;

    @Override
    public String apply(Long aLong) {
        return null;
    }

    private void loadLines(String fileName) {
        InputStream stream = FileLineExtract.class.getClassLoader().getResourceAsStream(filename);
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

    }
}
