package com.example.background.workers;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.ExistingWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import static com.example.background.Constants.SYNC_WORK_NAME;

/**
 * Cleans up temporary files generated during blurring process
 */
public class ScheduleSyncWorker extends Worker {

    /**
     * Creates an instance of the {@link Worker}.
     *
     * @param appContext   the application {@link Context}
     * @param workerParams the set of {@link WorkerParameters}
     */
    public ScheduleSyncWorker(@NonNull Context appContext, @NonNull WorkerParameters workerParams) {
        super(appContext, workerParams);
    }

    private static final String TAG = ScheduleSyncWorker.class.getSimpleName();

    @NonNull
    @Override
    public Result doWork() {
        Log.i(TAG, "ScheduleSyncWorker START");
        scheduleSync();
        Log.i(TAG, "ScheduleSyncWorker END");
        return Result.success();
    }

    private void scheduleSync() {
        Context applicationContext = getApplicationContext();
        WorkManager.getInstance(applicationContext)
                .enqueueUniqueWork(SYNC_WORK_NAME,
                        ExistingWorkPolicy.KEEP,
                        OneTimeWorkRequest.from(SyncWorker.class));
    }
}
