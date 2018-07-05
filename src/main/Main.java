package main;

import main.consumers.JobConsumer;
import main.model.PriceList;
import main.producers.EventStream;
import main.producers.SimpleJobProducer;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;

public class Main {
    public static void main(String [] args) {
        loadPriceList();
        try {
            EventStream eventStream =
                    new SimpleJobProducer();
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


}

