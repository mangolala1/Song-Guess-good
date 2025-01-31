package dataAccessObjects;

import entities.Users.User;

import java.io.*;
import java.lang.reflect.Array;
import java.util.*;

public class getTop200SongNames {
    private File csvFile;

    private final Map<String, Integer> headers = new LinkedHashMap<>();

    private final Map<String, User> accounts = new HashMap<>();

    public getTop200SongNames() {}
        public List<String> top200(String csvPath) throws FileNotFoundException {
                List res = new ArrayList<>();
                csvFile = new File(csvPath);
                try (BufferedReader reader = new BufferedReader(new FileReader(csvFile))) {
                    reader.readLine();
                    String row;
                    while ((row = reader.readLine()) != null) {
                        String[] col = row.split(",");
                        String songName = String.valueOf(col[3]).replaceFirst("^\"", "").replaceAll("\"$", "");
                        res.add(songName);
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                return res;
        }

        public static void main(String[] args) throws FileNotFoundException {
            String filePath = "./top200SongsWeekly.csv"; // Replace with the actual path to your CSV file
            getTop200SongNames a = new getTop200SongNames();

            System.out.println(a.top200(filePath));
        }
    }


