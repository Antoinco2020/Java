package it.restapp.project.listener;

import it.restapp.project.event.ReviewInsertedEvent;
import it.restapp.project.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class EmailListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(EmailListener.class);

    @Autowired
    private EmailService emailService;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Async
    public void onInsertEvent(ReviewInsertedEvent event) throws Exception {
        try {
            LOGGER.info("Start event when insert review");
            emailService.sendReviewEmail(event.getReview());
        }
        catch (Exception e){
            LOGGER.error("Error onInsertEvent. Details: ", e);
            throw e;
        }
    }
}
