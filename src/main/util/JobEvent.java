package main.util;

import java.time.Instant;
import java.util.UUID;

public class JobEvent {
    
    private final UUID uuid;
    private final Job job;
    private final Instant created;
    
    public JobEvent(JobEvent event) {
        this.uuid = event.uuid;
        this.job = event.job;
        this.created = event.created;
    }
    
    public JobEvent(UUID uuid, Job job) {
        this.uuid = uuid;
        this.job = job;
        this.created = Instant.now();
    }
    
    public UUID getUuid() {
        return uuid;
    }
    
    public Job getJob() {return job;}
    
    public Instant getCreated() {
        return created;
    }

}

