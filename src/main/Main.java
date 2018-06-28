package main;

import main.consumers.JobConsumer;
import main.model.PriceList;
import main.producers.EventStream;
import main.producers.JobProducer;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;

public class Main {
    public static double totalPrice = 0.0;
    public static void main(String [] args) {
        loadPriceList();
        try {
//            String filePath = readUserInput("Please input job file path：");
//            /Users/aimeeg/Desktop/printjobs.csv
//            String filePath = "/Users/aimeeg/Desktop/printjobs.csv";
            EventStream eventStream =
                    new JobProducer();
            JobConsumer jobConsumer =
                    new JobConsumer();
            eventStream.produce(jobConsumer);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadPriceList() {
        PriceList list;
        Yaml yaml = new Yaml();
        try(InputStream in = ClassLoader.getSystemResourceAsStream("UnitPrice.yaml")) {
            list = yaml.loadAs(in, PriceList.class);
            PriceList.getInstance().setPriceList(list.getPriceList());
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    private static boolean isInvalid(String str) {
        return str.equals("");
    }

}

