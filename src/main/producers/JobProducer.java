package main.producers;

import main.consumers.EventConsumer;
import main.model.PageType;
import main.util.Job;
import main.util.JobEvent;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import static java.util.concurrent.TimeUnit.SECONDS;


public class JobProducer implements EventStream, AutoCloseable{
    
    private final LinkedBlockingQueue<JobEvent> stream = new LinkedBlockingQueue<>(3);
    private final ExecutorService reader = Executors.newSingleThreadExecutor();
    private final ExecutorService operator = Executors.newSingleThreadExecutor();
    private double totalPrice;
    
    public double getTotalPrice() { return totalPrice; }
    public synchronized void setTotalPrice(double price) {
        totalPrice += price;
    }
    
    @Override
    public void produce(EventConsumer consumer) {
        // generate print jobs to queue
        Runnable jobsGenerator = () -> {
            try {
                readJobsFromFile();
            } catch (InterruptedException e) {
                System.err.println("Interrupted.");
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                System.err.println("Failed not found.");
                e.printStackTrace();
            }
        };
        reader.execute(jobsGenerator);
        
        // pass jobs from queue to the consumer
        Runnable passEventToConsumer = () -> {
            try {
                while (!reader.isShutdown() || !stream.isEmpty()) {
                    Thread.sleep(5000);
                    try {
                        JobEvent event = consumer.consume(stream.poll(1, SECONDS));
                        System.out.println("Queue length: " + stream.size());
                        setTotalPrice(event.getJob().getTotalPrice());
                    } catch (NullPointerException e) {
                        // TODO
                        continue;
                    }
                }
                System.out.println("Total price: $"+String.format("%.2f", getTotalPrice()/100));
            } catch (InterruptedException e) {
                System.err.println("Thread was interrupted, Failed to complete operation: " + e);
            }
        };
        operator.execute(passEventToConsumer);
    }
    
    public void readJobsFromFile() throws FileNotFoundException, InterruptedException {
        BufferedReader br = new BufferedReader(
                new FileReader("/Users/aimeeg/Desktop/printjobs.csv"));
        String line;
        String cvsSplitBy = ",";
        
        try {
            while ((line = br.readLine()) != null) {
                String[] jobItems = line.split(cvsSplitBy);
                Job job = new Job(Integer.parseInt(jobItems[0].trim()),
                        Integer.parseInt(jobItems[1].trim()),
                        (jobItems[2].trim().equals("true")? PageType.DOUBLE:PageType.SINGLE));
                JobEvent event = new JobEvent(UUID.randomUUID(), job);
                Thread.sleep(5000);
                stream.put(event);
            }
        } catch (IOException e) {
            System.err.println("Failed to read next line.");
            e.printStackTrace();
        } finally {
            reader.shutdown();
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    @Override
    public void close() {
        reader.shutdown();
        operator.shutdown();
    }
}
