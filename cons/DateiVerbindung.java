package cons;

import fx.StartAnalyzerFX;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class DateiVerbindung {

    private static final File JARFILE = new File(StartAnalyzerFX.class.getProtectionDomain().getCodeSource().getLocation().getPath());
    private DateiVerbindung(){}

    public static File liesFileVomJARpfad(String filename) throws IOException {
        System.out.println("Filepath: " + JARFILE);
        //Path inside jar-file -> Class_jar/Class_jar/..
        //one folder up is the corresponding Class_jar folder, so you can put all ressources in a subfolder (e.g. res/)
        //used: out\artifacts\Class_jar\res here for the pics and csv files inside res folder
        //return new File(JARFILE.getParent() + "\\res\\" + filename);;
        return new File(JARFILE + "\\res\\" + filename);
    }

    private static StringBuilder liesFileToStringbuilder(String filename) throws IOException {
        StringBuilder sb = new StringBuilder();
        File ressourceFile = DateiVerbindung.liesFileVomJARpfad(filename);

        try (BufferedReader br = new BufferedReader(new FileReader(ressourceFile, StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null){
                sb.append(line).append(System.lineSeparator());
            }
        } catch (IOException e) {
            e.getLocalizedMessage();
        }
        return sb;
    }

    public static StringBuilder liesLog(String logfilename) throws IOException {
        StringBuilder sb;
        sb = liesFileToStringbuilder(logfilename);
        return sb;
    }
}
