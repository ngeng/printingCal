import model.PageType;

import java.io.*;
import java.util.ArrayList;


public class Main {
    public static void main(String [] args) {
        ArrayList<Job> jobs = readJobsFromFile();
        double totalPrice = 0.0;
        for (Job job: jobs) {
            job.printDetails();
            totalPrice += job.getTotalPrice();
        }
        System.out.println("Total price: $"+String.format("%.2f", totalPrice/100));
    }

    public static ArrayList<Job> readJobsFromFile() {
        String filePath = "src/main/resources/printjobs.csv";
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
}

