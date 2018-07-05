package main.producers;

import main.consumers.EventConsumer;
import main.model.PageType;
import main.util.Job;
import main.util.JobEvent;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import static java.util.concurrent.TimeUnit.SECONDS;


public class SimpleJobProducer implements EventStream, AutoCloseable{
    
    private final LinkedBlockingQueue<JobEvent> stream = new LinkedBlockingQueue<>(2);
    private final ExecutorService reader = Executors.newSingleThreadExecutor();
    private final ExecutorService operator = Executors.newSingleThreadExecutor();
    private boolean finishReadingJobs;

    @Override
    public void produce(EventConsumer consumer) {
        // generate print jobs to queue
        Runnable jobsGenerator = () -> {
            try {
                BufferedReader br = new BufferedReader(new FileReader("/Users/aimeeg/Desktop/printjobs.csv"));
                readJobsFromFile(br);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        };
        reader.execute(jobsGenerator);
        
        // pass jobs from queue to the consumer
        Runnable passEventToConsumer = () -> {
            try {
                List<Job> jobList = new ArrayList<>();
                while (!finishReadingJobs || !stream.isEmpty()) { 
                    Thread.sleep(5000);
                    try {
                        JobEvent event = consumer.consume(stream.poll(1, SECONDS));
                        System.out.println("Queue length: " + stream.size());
                        jobList.add(event.getJob());
                    } catch (NullPointerException e) {
                        // TODO
                        continue;
                    }
                }
                Double totalPrice = jobList.stream().mapToDouble(i -> i.getTotalPrice()).sum();
                System.out.println("Total price: $"+String.format("%.2f", totalPrice/100));
            } catch (InterruptedException e) {
                System.err.println("Thread was interrupted, Failed to complete operation: " + e);
            }
        };
        operator.execute(passEventToConsumer);
    }
    
    public void readJobsFromFile(BufferedReader br) throws InterruptedException {
        String line;
        String cvsSplitBy = ",";
        
        try {
            while ((line = br.readLine()) != null) {
                String[] jobItems = line.split(cvsSplitBy);
                Job job = new Job(Integer.parseInt(jobItems[0].trim()),
                        Integer.parseInt(jobItems[1].trim()),
                        (jobItems[2].trim().equals("true")? PageType.DOUBLE:PageType.SINGLE));
                JobEvent event = new JobEvent(UUID.randomUUID(), job);
                Thread.sleep(1000);
                stream.put(event);
            }
        } catch (IOException e) {
            // TODO
            e.printStackTrace();
        } finally {
            finishReadingJobs = true;
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
        System.err.println("Auto close threads.");
        reader.shutdown();
        operator.shutdown();
    }
}
