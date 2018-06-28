package main.consumers;

import main.util.Job;
import main.util.JobEvent;
import main.Main;

public class JobConsumer implements EventConsumer {
    
    @Override
    public JobEvent consume(JobEvent event) {
        Job job = event.getJob();
        job.printDetails();
        Main.totalPrice += job.getTotalPrice();
//        Thread.sleep(1000);
        return event;
    }
}
