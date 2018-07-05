package main.consumers;

import main.util.Job;
import main.util.JobEvent;

public class JobConsumer implements EventConsumer {
    
    @Override
    public JobEvent consume(JobEvent event) {
        Job job = event.getJob();
        job.printDetails();
        return event;
    }
}
