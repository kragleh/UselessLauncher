package beauty.useless.uselesslauncher.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class AppsUtil {

    public static String[] getApps(File filesDir) {
        File appsFile = new File(filesDir, "apps.txt");

        if (!appsFile.exists()) {
            try {
                appsFile.createNewFile();
            } catch (Exception e) {
                return null;
            }
        }

        try {
            BufferedReader reader = new BufferedReader(new FileReader(appsFile));
            String line;
            StringBuilder builder = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }

            reader.close();
            String appsContent = builder.toString();
            return appsContent.split(";");
        } catch (Exception e) {
            return null;
        }


        /**File appsFile = new File(filesDir, "apps.json");

        if (!appsFile.exists()) {
            try {
                appsFile.createNewFile();
            } catch (IOException e) {
                return null;
            }
        }

        try {
            String appsJson = readJsonFile(appsFile);
            System.out.println("appsJson: " + appsJson);
            Gson gson = new Gson();
            return gson.fromJson(new FileReader(appsFile), String[].class);
        } catch (IOException e) {
            return null;
        }**/
    }

    public static void addApp(String packageName, File filesDir) {

        File appsFile = new File(filesDir, "apps.txt");

        if (!appsFile.exists()) {
            try {
                appsFile.createNewFile();
            } catch (Exception ignored) { }
        }

        try {
            BufferedReader reader = new BufferedReader(new FileReader(appsFile));
            String line;
            StringBuilder builder = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }

            reader.close();
            String appsContent = builder.toString();
            appsContent = appsContent + ";" + packageName;
            FileWriter writer = new FileWriter(appsFile);
            writer.write(appsContent);
        } catch (Exception ignored) { }

        /**File appsFile = new File(filesDir, "apps.json");

        try {
            String appsJson = readJsonFile(appsFile);
            Gson gson = new Gson();
            List<String> apps = gson.fromJson(new FileReader(appsFile), List.class);

            if (apps == null) {
                apps = new ArrayList<>();
            }

            apps.add(packageName);
            FileWriter writer = new FileWriter(appsFile);
            writer.write(gson.toJson(apps));
        } catch (IOException ignored) { }**/
    }

    private static String readJsonFile(File file) throws IOException {
        FileInputStream fis = new FileInputStream(file);
        BufferedReader br = new BufferedReader(new InputStreamReader(fis));
        StringBuilder sb = new StringBuilder();
        String line;

        while ((line = br.readLine()) != null) {
            sb.append(line);
        }

        br.close();
        return sb.toString();
    }

}
