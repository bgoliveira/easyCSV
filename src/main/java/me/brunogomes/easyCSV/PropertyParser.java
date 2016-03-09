package me.brunogomes.easyCSV;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Function;

/**
 *
 */
public class PropertyParser
{
    public static void main(String[] args) throws IOException, IllegalAccessException, NoSuchFieldException, InstantiationException
    {
        final Function<String, MyBean> csvToMyBean = s -> new MyBean(s.split(",")[0], s.split(",")[1]);

        Path caminho = Paths.get(System.getProperty("user.home"),"test.csv");

        CsvReader csvReader = new CsvReader(caminho);

        //List<MyBean> myBeen = csvReader.toBeanList(MyBean.class);

        List<MyBean> myBeen = csvReader.mapLineToBean(csvToMyBean);

        myBeen.forEach(System.out::println);
    }
}
