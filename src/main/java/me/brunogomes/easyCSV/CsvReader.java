package me.brunogomes.easyCSV;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <h1>CSV Reader</h1>
 * The CsvReader class is the main class of the library
 * It's read a CSV file from a path and provides
 * results in a raw List of Strings or in a List of a given Bean.
 * <p>
 * <b>Note:</b> Giving proper comments in your program makes it more
 * user friendly and it is assumed as a high quality code.
 *
 * @author  Bruno Oliveira
 * @version 1.0
 * @since   2016-03-06
 */
public class CsvReader
{
    private static final String STRING_EMPTY = "";

    private String delimiterCharacter;
    private Stream<String> lines;

    /**
     *
     * @param filePath
     * @param charset
     * @param delimiterCharacter
     * @throws IOException
     */
    public CsvReader(final Path filePath,
                     final Charset charset,
                     final String delimiterCharacter) throws IOException
    {
        this.delimiterCharacter = delimiterCharacter;
        this.lines = Files.lines(filePath, charset);
    }

    /**
     *
     * @param filePath
     * @throws IOException
     */
    public CsvReader(Path filePath) throws IOException
    {
        this(filePath,StandardCharsets.UTF_8, FileDelimiter.COMA.getDelimiterCharacter());
    }

    /**
     *
     * @param beanMapper
     * @param <T>
     * @return
     */
    public <T> List<T> mapLineToBean(final Function<String, T> beanMapper)
    {
        return lines.skip(1).map(beanMapper::apply).collect(Collectors.toList());
    }

    /**
     *
     * @return
     */
    public List<String> getLinesList()
    {
        return lines.collect(Collectors.toList());
    }

    /**
     *
     * @return
     */
    public Stream<String> getLinesStream()
    {
        return lines;
    }

    /**
     *
     * @return
     */
    public List<String> getHeader()
    {
        return  Pattern.compile(delimiterCharacter)
                        .splitAsStream(lines.findFirst().orElse(STRING_EMPTY))
                        .collect(Collectors.toList());
    }
}
