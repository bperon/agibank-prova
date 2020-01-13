package br.com.agibank.listener;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;

import br.com.agibank.config.SalesProjectConfiguration;
import br.com.agibank.service.ReportExecutorService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class FolderListener {
    private static final Logger LOGGER = LogManager.getLogger(FolderListener.class.getName());

    private SalesProjectConfiguration config;
    private ReportExecutorService reportExecutorService;

    public FolderListener(final SalesProjectConfiguration config, final ReportExecutorService reportExecutorService) {
        this.config = config;
        this.reportExecutorService = reportExecutorService;
    }

    @EventListener(ApplicationReadyEvent.class)
    @SuppressWarnings({"InfiniteLoopStatement", "unchecked"})
    public void listen() {
        final Path inputFolder = config.getInputFolder();
        WatchKey key;
        try {
            final WatchService watchService = FileSystems.getDefault().newWatchService();
            key = inputFolder.register(watchService, ENTRY_CREATE); //listen only for new added files
        } catch (IOException e) {
            LOGGER.error(e);
            return;
        }

        LOGGER.debug("Listening for new files in folder '{}'...", inputFolder);
        final ExecutorService executor = Executors.newFixedThreadPool(Integer.parseInt(config.getNumberOfThreads()));
        executor.execute(() -> reportExecutorService.execute()); // create report if there are any files in HOMEPATH/data/in.

        WatchEvent<Path> eventPath;
        Path child;
        while(true) {
            for (final WatchEvent<?> event : key.pollEvents()) {
                eventPath = (WatchEvent<Path>) event;
                child = inputFolder.resolve(eventPath.context());
                LOGGER.debug("File '{}' added.", child.getFileName());

                executor.execute(() -> reportExecutorService.execute());

                boolean isValid = key.reset();  // needed, otherwise key won't receive any new events.
                if (!isValid) {
                    break;
                }
            }
        }
    }
}
