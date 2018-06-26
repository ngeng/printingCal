package main;

import main.model.PageType;
import main.model.PriceList;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.ArrayList;

public class Main {
    public static void main(String [] args) {
        loadPriceList();
        try {
            String filePath = readUserInput("Please input job file path：");
//            /Users/Aimee_G/Desktop/printjobs.csv
            ArrayList<Job> jobs = readJobsFromFile(filePath);
            double totalPrice = 0.0;
            for (Job job: jobs) {
                job.printDetails();
                totalPrice += job.getTotalPrice();
            }
            System.out.println("Total price: $"+String.format("%.2f", totalPrice/100));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadPriceList() {
        PriceList list = null;
        Yaml yaml = new Yaml();
        try(InputStream in = ClassLoader.getSystemResourceAsStream("UnitPrice.yaml")) {
            list = yaml.loadAs(in, PriceList.class);
            PriceList.getInstance().setPriceList(list.getPriceList());
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    public static ArrayList<Job> readJobsFromFile(String filePath) {
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";

        ArrayList<Job> jobs = new ArrayList();
        try {
            br = new BufferedReader(new FileReader(filePath));
            while ((line = br.readLine()) != null) {
                String[] jobItems = line.split(cvsSplitBy);
                Job job = new Job(Integer.parseInt(jobItems[0].trim()),
                                Integer.parseInt(jobItems[1].trim()),
                                (jobItems[2].trim().equals("true")? PageType.DOUBLE:PageType.SINGLE));
                jobs.add(job);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return jobs;
    }

    private static String readUserInput(String prompt) throws IOException {
        String result;
        do {
            System.out.print(prompt);
            InputStreamReader is_reader = new InputStreamReader(System.in);
            result = new BufferedReader(is_reader).readLine();
        } while (isInvalid(result));

        return result;
    }

    private static boolean isInvalid(String str) {
        return str.equals("");
    }

}

