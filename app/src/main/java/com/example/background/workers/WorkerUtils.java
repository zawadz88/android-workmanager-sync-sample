package com.example.background.workers;

import android.util.Log;

import static com.example.background.Constants.DELAY_TIME_MILLIS;

final class WorkerUtils {
    private static final String TAG = WorkerUtils.class.getSimpleName();

    /**
     * Method for sleeping for a fixed about of time to emulate slower work
     */
    static void sleep() {
        try {
            Thread.sleep(DELAY_TIME_MILLIS, 0);
        } catch (InterruptedException e) {
            Log.d(TAG, e.getMessage());
        }
    }

    private WorkerUtils() {
    }
}
