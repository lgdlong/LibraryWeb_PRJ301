package listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import service.BorrowRecordService;
import service.FineService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Servlet Context Listener that sets up automatic overdue checking
 * using a scheduled executor service that runs every few hours.
 */
@WebListener
public class OverdueSchedulerListener implements ServletContextListener {
    
    private ScheduledExecutorService scheduler;
    private final BorrowRecordService borrowRecordService = new BorrowRecordService();
    private final FineService fineService = new FineService();
    
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("=== Starting Library Overdue Scheduler ===");
        
        // Create a scheduled executor with a single thread
        scheduler = Executors.newSingleThreadScheduledExecutor(r -> {
            Thread t = new Thread(r, "overdue-checker");
            t.setDaemon(true); // Daemon thread so it doesn't prevent JVM shutdown
            return t;
        });
        
        // Schedule overdue checking to run every 6 hours
        // Initial delay: 30 seconds (to let the application start up fully)
        // Period: 6 hours = 6 * 60 * 60 = 21600 seconds
        scheduler.scheduleAtFixedRate(
            this::performOverdueCheck,
            30, // initial delay in seconds
            21600, // period in seconds (6 hours)
            TimeUnit.SECONDS
        );
        
        System.out.println("Overdue scheduler initialized - will check every 6 hours");
        System.out.println("Next check scheduled in 30 seconds from now");
    }
    
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("=== Stopping Library Overdue Scheduler ===");
        if (scheduler != null) {
            scheduler.shutdown();
            try {
                // Wait for existing tasks to terminate
                if (!scheduler.awaitTermination(60, TimeUnit.SECONDS)) {
                    scheduler.shutdownNow();
                    // Wait a bit more for tasks to respond to being cancelled
                    if (!scheduler.awaitTermination(60, TimeUnit.SECONDS)) {
                        System.err.println("Overdue scheduler did not terminate cleanly");
                    }
                }
            } catch (InterruptedException ie) {
                // (Re-)Cancel if current thread also interrupted
                scheduler.shutdownNow();
                // Preserve interrupt status
                Thread.currentThread().interrupt();
            }
        }
        System.out.println("Overdue scheduler stopped");
    }
    
    /**
     * Performs the actual overdue checking with comprehensive tracking and error handling
     */
    private void performOverdueCheck() {
        LocalDateTime startTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        
        try {
            System.out.println("========================================");
            System.out.println("Starting scheduled overdue check at: " + startTime.format(formatter));
            
            // Use the enhanced tracking method for detailed logging
            BorrowRecordService.OverdueProcessingResult result = 
                borrowRecordService.checkAndUpdateOverdueWithTracking();
            
            // Log detailed results
            System.out.println("Overdue check completed successfully:");
            System.out.println("- Records checked: " + result.getTotalChecked());
            System.out.println("- Updated to overdue: " + result.getUpdatedToOverdue());
            System.out.println("- Fines created: " + result.getFinesCreated());
            System.out.println("- Errors encountered: " + result.getErrorCount());
            
            if (result.getErrorCount() > 0) {
                System.out.println("Errors during processing:");
                for (String error : result.getErrors()) {
                    System.out.println("  - " + error);
                }
            }
            
            // Also process any outstanding overdue fines (update amounts if needed)
            System.out.println("Processing overdue fines...");
            fineService.processOverdueFines();
            System.out.println("Fine processing completed");
            
            LocalDateTime endTime = LocalDateTime.now();
            long durationSeconds = java.time.Duration.between(startTime, endTime).getSeconds();
            System.out.println("Total processing time: " + durationSeconds + " seconds");
            System.out.println("Next scheduled check: " + 
                endTime.plusHours(6).format(formatter));
            System.out.println("========================================");
            
        } catch (Exception e) {
            System.err.println("Error during scheduled overdue check at " + 
                startTime.format(formatter) + ": " + e.getMessage());
            System.err.println("Stack trace: " + e.getClass().getSimpleName() + " - " + e.getMessage());
            
            // Still continue with the schedule - don't let one error stop future checks
            System.err.println("Continuing with scheduled checks despite error");
        }
    }
}
