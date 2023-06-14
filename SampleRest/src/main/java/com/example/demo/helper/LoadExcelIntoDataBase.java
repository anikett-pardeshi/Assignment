package com.example.demo.helper;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Properties;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.apache.ibatis.jdbc.ScriptRunner;

public class LoadExcelIntoDataBase {
    public static void LoadData() throws FileNotFoundException {
        try {
        Properties prop = new Properties();
        prop.load(new FileReader("src/main/resources/application.properties"));
        String url = prop.getProperty("spring.datasource.url");
        String user = prop.getProperty("spring.datasource.username");
        String password = prop.getProperty("spring.datasource.password");
        String DriverClass = prop.getProperty("spring.datasource.class");
        System.out.println("url :"+url);
        System.out.println("user :"+user);
        System.out.println("password :"+password);
        Class.forName(DriverClass);
        Connection con= DriverManager.getConnection(url,user,password);
        ScriptRunner sr = new ScriptRunner(con);
        //Creating a reader object
        Reader reader = new BufferedReader(new FileReader("src/main/resources/QueriesToCreateTable/createTable.sql"));
        //Running the script
        System.out.println("-------------------------------------------------------");
        System.out.println("---- Executing Script -----------");
        sr.runScript(reader);
        System.out.println("--------------------------------------------------------");
        loadCSVIntoTable(con,"src/main/resources/CSVtoLoad/movies.csv");
        loadCSVIntoTable(con,"src/main/resources/CSVtoLoad/ratings.csv");
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void loadCSVIntoTable(Connection con, String s) throws FileNotFoundException {
        try (CSVReader reader = new CSVReader(new FileReader(s))) {
            String[] nextLine;
            String [] firstLineCol = reader.readNext();
            int col = firstLineCol.length;
            StringBuilder strb = new StringBuilder();
            strb.append("(");
            for(int i = 0 ; i < col ; i++){
                strb.append("?,");
            }
            strb.deleteCharAt(strb.length()-1);
            strb.append(")");
            String firstLine = Arrays.toString(firstLineCol);
            System.out.println("firstLine :" +firstLine);
            firstLine = firstLine.replace("[","(");
            firstLine = firstLine.replace("]",")");
            System.out.println("firstLine :" +firstLine);
            String insertQuery = "INSERT INTO "+s.substring(s.lastIndexOf("/")+1,s.indexOf("."))+" "+firstLine+" VALUES ";
            try{
                // Skip header row if needed
                // reader.readNext();
                while ((nextLine = reader.readNext()) != null) {
                    System.out.println("insertQuery : " +insertQuery);
                    StringBuilder values = new StringBuilder();
                    values.append("(\"");
                    for (String sing:nextLine) {
                        values.append(sing).append("\",\"");
                    }
                    values.deleteCharAt(values.length()-1);
                    values.deleteCharAt(values.length()-1);
                    values.append(")");
                    System.out.println("Query : "+insertQuery+values);
                    PreparedStatement statement = con.prepareStatement(insertQuery + values);
                    statement.execute();
                }
            } catch (SQLException | CsvValidationException | IOException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException | CsvValidationException e) {
            throw new RuntimeException(e);
        }
    }
}
