package com.example.background.workers;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class SyncWorker extends Worker {

    /**
     * Creates an instance of the {@link Worker}.
     *
     * @param appContext   the application {@link Context}
     * @param workerParams the set of {@link WorkerParameters}
     */
    public SyncWorker(@NonNull Context appContext, @NonNull WorkerParameters workerParams) {
        super(appContext, workerParams);
    }

    private static final String TAG = SyncWorker.class.getSimpleName();

    @NonNull
    @Override
    public Result doWork() {
        Log.i(TAG, "SyncWorker START");

        WorkerUtils.sleep();

        Log.i(TAG, "SyncWorker POST SLEEP");

        return Result.success();
    }
}
