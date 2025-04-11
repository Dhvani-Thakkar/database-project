import java.io.*;
import java.util.Scanner;

public class CSVtoSQL {
    public static void main(String[] args) 
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the names of your CSV files, separated by spaces:");
        String[] fileNames = scanner.nextLine().split(" ");

        try (PrintWriter writer = new PrintWriter(new File("sqlfile.txt"))) 
        {
            for (String fileName : fileNames) 
            {
                String csvFile = fileName + ".csv";
                String line;
                String cvsSplitBy = ",";
                String[] columnNames = null;

                try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) 
                {
                    if ((line = br.readLine()) != null) 
                    {
                        columnNames = line.split(cvsSplitBy);
                    }

                    while ((line = br.readLine()) != null) 
                    {
                        String[] values = line.split(cvsSplitBy);
                        StringBuilder sql = new StringBuilder("INSERT INTO " + fileName + " (");

                        for (String columnName : columnNames) 
                        {
                            sql.append(columnName).append(",");
                        }

                        sql = new StringBuilder(sql.substring(0, sql.length() - 1));
                        sql.append(") VALUES (");

                        for (String value : values) 
                        {
                            sql.append("'").append(value).append("',");
                        }

                        sql = new StringBuilder(sql.substring(0, sql.length() - 1));
                        sql.append(");");

                        writer.println(sql.toString());
                    }
                } 
                catch (IOException e) 
                {
                    e.printStackTrace();
                }
            }
        } 
        catch (FileNotFoundException e) 
        {
            e.printStackTrace();
        }
    }
}
