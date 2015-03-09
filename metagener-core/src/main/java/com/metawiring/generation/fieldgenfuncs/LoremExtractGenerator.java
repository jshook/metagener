package com.metawiring.generation.fieldgenfuncs;

import com.metawiring.generation.core.HashedDiscreteSamplingAdapter;
import com.metawiring.types.functiontypes.TypedFieldFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.CharBuffer;

public class LoremExtractGenerator implements TypedFieldFunction<String> {

    private final static Logger logger = LoggerFactory.getLogger(LoremExtractGenerator.class);

    private static CharBuffer loremIpsumImage = null;

    private int minsize, maxsize;

    HashedDiscreteSamplingAdapter sizeSamplingAdapter;
    HashedDiscreteSamplingAdapter positionSamplingAdapter;

    public LoremExtractGenerator(int minsize, int maxsize) {
        this.minsize = minsize;
        this.maxsize = maxsize;
        loremIpsumImage = loadLoremIpsum();
        positionSamplingAdapter = new HashedDiscreteSamplingAdapter(1, loremIpsumImage.limit() - maxsize, "uniform");
        sizeSamplingAdapter= new HashedDiscreteSamplingAdapter(minsize, maxsize, "uniform");
    }

    public LoremExtractGenerator(String minsize, String maxsize) {
        this(Integer.valueOf(minsize), Integer.valueOf(maxsize));
    }

    @Override
    public String apply(long value) {

        String sub;

        int offset = (int) positionSamplingAdapter.sample(value);
        int length = (int) sizeSamplingAdapter.sample(value+offset);
        try {
            sub = loremIpsumImage.subSequence(offset, offset + length).toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return sub;
    }

    private CharBuffer loadLoremIpsum() {
        InputStream stream = LoremExtractGenerator.class.getClassLoader().getResourceAsStream("lorem_ipsum_full.txt");
        if (stream == null) {
            throw new RuntimeException("lorem_ipsum_full.txt was missing.");
        }

        CharBuffer image;
        try {
            InputStreamReader isr = new InputStreamReader(stream);
            image = CharBuffer.allocate(1024 * 1024);
            isr.read(image);
            isr.close();
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
        image.flip();

        return image.asReadOnlyBuffer();

    }

    public String toString() {
        return getClass().getSimpleName() + ":" + minsize + ":" + maxsize;
    }

}
