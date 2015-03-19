package com.metawiring.generation.fieldgenfuncs;

import com.metawiring.annotations.Output;
import com.metawiring.types.functiontypes.TypedFieldFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Output({String.class})
public class FileLineCycler implements TypedFieldFunction<String> {
    private final static Logger logger = LoggerFactory.getLogger(FileLineCycler.class);

    private final String filename;
    private List<String> loadedLines = new ArrayList<>();

    public FileLineCycler(String filename) {
        this(filename, "uniform");
    }

    public FileLineCycler(String filename, String distname) {
        this.filename = filename;
        loadedLines = loadLines(filename);
    }

    private List<String> loadLines(String fileName) {

        List<String> lines = new ArrayList<>();

        InputStream stream = FileLineCycler.class.getClassLoader().getResourceAsStream(filename);
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
        int line = loadedLines.size() % (int) value;
        return loadedLines.get(line);
    }
}
