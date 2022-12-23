package org.apache.commons.compress.archivers.zip;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.commons.compress.parallel.FileBasedScatterGatherBackingStore;
import org.apache.commons.compress.parallel.InputStreamSupplier;
import org.apache.commons.compress.parallel.ScatterGatherBackingStore;
import org.apache.commons.compress.parallel.ScatterGatherBackingStoreSupplier;

public class ParallelScatterZipCreator {
    /* access modifiers changed from: private */
    public final ScatterGatherBackingStoreSupplier backingStoreSupplier;
    private long compressionDoneAt;

    /* renamed from: es */
    private final ExecutorService f145es;
    private final List<Future<Object>> futures;
    private long scatterDoneAt;
    private final long startedAt;
    /* access modifiers changed from: private */
    public final List<ScatterZipOutputStream> streams;
    /* access modifiers changed from: private */
    public final ThreadLocal<ScatterZipOutputStream> tlScatterStreams;

    private static class DefaultBackingStoreSupplier implements ScatterGatherBackingStoreSupplier {
        final AtomicInteger storeNum;

        private DefaultBackingStoreSupplier() {
            this.storeNum = new AtomicInteger(0);
        }

        public ScatterGatherBackingStore get() throws IOException {
            return new FileBasedScatterGatherBackingStore(File.createTempFile("parallelscatter", "n" + this.storeNum.incrementAndGet()));
        }
    }

    public ParallelScatterZipCreator() {
        this(Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()));
    }

    public ParallelScatterZipCreator(ExecutorService executorService) {
        this(executorService, new DefaultBackingStoreSupplier());
    }

    public ParallelScatterZipCreator(ExecutorService executorService, ScatterGatherBackingStoreSupplier scatterGatherBackingStoreSupplier) {
        this.streams = Collections.synchronizedList(new ArrayList());
        this.futures = new ArrayList();
        this.startedAt = System.currentTimeMillis();
        this.compressionDoneAt = 0;
        this.tlScatterStreams = new ThreadLocal<ScatterZipOutputStream>() {
            /* access modifiers changed from: protected */
            public ScatterZipOutputStream initialValue() {
                try {
                    ParallelScatterZipCreator parallelScatterZipCreator = ParallelScatterZipCreator.this;
                    ScatterZipOutputStream access$100 = parallelScatterZipCreator.createDeferred(parallelScatterZipCreator.backingStoreSupplier);
                    ParallelScatterZipCreator.this.streams.add(access$100);
                    return access$100;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        this.backingStoreSupplier = scatterGatherBackingStoreSupplier;
        this.f145es = executorService;
    }

    /* access modifiers changed from: private */
    public ScatterZipOutputStream createDeferred(ScatterGatherBackingStoreSupplier scatterGatherBackingStoreSupplier) throws IOException {
        ScatterGatherBackingStore scatterGatherBackingStore = scatterGatherBackingStoreSupplier.get();
        return new ScatterZipOutputStream(scatterGatherBackingStore, StreamCompressor.create(-1, scatterGatherBackingStore));
    }

    public void addArchiveEntry(ZipArchiveEntry zipArchiveEntry, InputStreamSupplier inputStreamSupplier) {
        submit(createCallable(zipArchiveEntry, inputStreamSupplier));
    }

    public final Callable<Object> createCallable(ZipArchiveEntry zipArchiveEntry, InputStreamSupplier inputStreamSupplier) {
        if (zipArchiveEntry.getMethod() != -1) {
            final ZipArchiveEntryRequest createZipArchiveEntryRequest = ZipArchiveEntryRequest.createZipArchiveEntryRequest(zipArchiveEntry, inputStreamSupplier);
            return new Callable<Object>() {
                public Object call() throws Exception {
                    ((ScatterZipOutputStream) ParallelScatterZipCreator.this.tlScatterStreams.get()).addArchiveEntry(createZipArchiveEntryRequest);
                    return null;
                }
            };
        }
        throw new IllegalArgumentException("Method must be set on zipArchiveEntry: " + zipArchiveEntry);
    }

    public ScatterStatistics getStatisticsMessage() {
        long j = this.compressionDoneAt;
        return new ScatterStatistics(j - this.startedAt, this.scatterDoneAt - j);
    }

    public final void submit(Callable<Object> callable) {
        this.futures.add(this.f145es.submit(callable));
    }

    public void writeTo(ZipArchiveOutputStream zipArchiveOutputStream) throws IOException, InterruptedException, ExecutionException {
        for (Future<Object> future : this.futures) {
            future.get();
        }
        this.f145es.shutdown();
        this.f145es.awaitTermination(60000, TimeUnit.SECONDS);
        this.compressionDoneAt = System.currentTimeMillis();
        for (ScatterZipOutputStream next : this.streams) {
            next.writeTo(zipArchiveOutputStream);
            next.close();
        }
        this.scatterDoneAt = System.currentTimeMillis();
    }
}
