package me.brunogomes.easyCSV;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 */
public class CsvReader
{
    private static final String STRING_EMPTY = "";

    private List<String> fileRows;
    private String delimiterCharacter;
    private Stream<String> lines;

    public CsvReader(final Path filePath,
                     final Charset charset,
                     final String delimiterCharacter) throws IOException
    {
        this.fileRows = Files.lines(filePath, charset)
        .filter(line-> !line.isEmpty()).collect(Collectors.toList());
        this.delimiterCharacter = delimiterCharacter;
        this.lines = Files.lines(filePath, charset);
    }

    public CsvReader(Path filePath) throws IOException
    {
        this(filePath,StandardCharsets.UTF_8, FileDelimiter.COMA.getDelimiterCharacter());
    }

    /**
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> List<T> toBeanList(Class<T> clazz) throws IllegalAccessException, InstantiationException, NoSuchFieldException
    {
        List<String> header = getHeader();

        List<T> toReturn = new ArrayList<>();

        for(String row :fileRows.subList(1,fileRows.size()))
        {
            T beanObject = clazz.newInstance();

            for(String headerField: header)
            {
                Field field = clazz.getDeclaredField(headerField);
                field.setAccessible(true);
                field.set(beanObject, row.split(delimiterCharacter)[header.indexOf(headerField)]);
            }

            toReturn.add(beanObject);
        }

        return toReturn;
    }

    public <T> List<T> toBeanFunctionalStyle(final Function<String, T> beanMapper)
    {
        return lines.skip(1).map(beanMapper::apply).collect(Collectors.toList());
    }

    /**
     *
     * @return
     */
    public List<String> getHeader()
    {
        return  Pattern.compile(delimiterCharacter)
                        .splitAsStream(fileRows.stream().findFirst().orElse(STRING_EMPTY))
                        .collect(Collectors.toList());
    }
}
