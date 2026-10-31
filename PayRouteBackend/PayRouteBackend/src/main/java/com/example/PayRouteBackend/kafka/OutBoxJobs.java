package com.example.PayRouteBackend.kafka;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = {"app.jobs-enabled", "app.kafka-enabled"}, havingValue = "true")
public class OutBoxJobs {


    private static final Logger log = LoggerFactory.getLogger(OutBoxJobs.class);

    private final EventRepository eventRepository;
    private final OutBoxPublisher publisher;

    public OutBoxJobs(EventRepository eventRepository, OutBoxPublisher publisher) {
        this.eventRepository = eventRepository;
        this.publisher = publisher;
    }


    @Scheduled(fixedDelay = 3000)
    public void publish() {


        for (Event e : eventRepository.findTop50ByPublishedAtIsNullOrderByCreatedAtDesc()) {

            try {
                publisher.publish(e.id);
            } catch (Exception ex) {

                log.error("Event {} remains pending {}", e.id, ex.getClass().getSimpleName());
                break;
            }


        }

    }


}
