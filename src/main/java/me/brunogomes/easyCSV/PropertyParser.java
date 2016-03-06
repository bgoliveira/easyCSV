package me.brunogomes.easyCSV;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 *
 */
public class PropertyParser
{
    public static void main(String[] args) throws IOException, IllegalAccessException, NoSuchFieldException, InstantiationException {
        Path caminho = Paths.get(System.getProperty("user.home"),"test.csv");

        CsvReader csvReader = new CsvReader(caminho);

        //List<MyBean> myBeen = csvReader.toBeanList(MyBean.class);

        List<MyBean> myBeen = csvReader.toBeanFunctionalStyle(PropertyParser::mapLineToBean);

        myBeen.forEach(System.out::println);
    }

    public static MyBean mapLineToBean(String line)
    {
        return new MyBean(line.split(",")[0], line.split(",")[1]);
    }
}
