package com.overengineered.client.util;

import com.overengineered.client.exception.HelloWorldClientException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.function.Supplier;

/**
 * Utility class for retry operations.
 */
@Component
@Slf4j
public class RetryUtil {

    /**
     * Retry an operation until it succeeds or the maximum number of attempts is reached.
     *
     * @param supplier The operation to retry
     * @param sleepTimeMs The time to sleep between retries in milliseconds
     * @param maxAttempts The maximum number of attempts
     * @param timeoutMessage The message to display if the operation times out
     * @param <T> The type of the result
     * @return The result of the operation
     * @throws HelloWorldClientException if the operation times out
     */
    public <T> T retryUntilResult(Supplier<Optional<T>> supplier,
                                  long sleepTimeMs,
                                  int maxAttempts,
                                  String timeoutMessage) {
        int attempts = 0;
        while (attempts < maxAttempts) {
            try {
                Optional<T> result = supplier.get();
                if (result.isPresent()) {
                    return result.get();
                }
                log.debug("No result yet, retrying ({}/{})", attempts + 1, maxAttempts);
            } catch (Exception e) {
                log.warn("Error while retrying: {}", e.getMessage());
            }
            
            attempts++;
            
            if (attempts < maxAttempts) {
                try {
                    Thread.sleep(sleepTimeMs);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    throw new HelloWorldClientException("Retry interrupted", e);
                }
            }
        }
        
        throw new HelloWorldClientException(timeoutMessage);
    }
}
