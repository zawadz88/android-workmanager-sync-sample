package com.example.background.workers;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.background.Constants;

public class SaveSingleOfferWorker extends Worker {

    /**
     * Creates an instance of the {@link Worker}.
     *
     * @param appContext   the application {@link Context}
     * @param workerParams the set of {@link WorkerParameters}
     */
    public SaveSingleOfferWorker(@NonNull Context appContext, @NonNull WorkerParameters workerParams) {
        super(appContext, workerParams);
    }

    private static final String TAG = SaveSingleOfferWorker.class.getSimpleName();

    @NonNull
    @Override
    public Result doWork() {
        String offerId = getInputData().getString(Constants.OFFER_ID);
        Log.i(TAG, "SaveSingleOfferWorker START - " + offerId);

        WorkerUtils.sleep();

        Log.i(TAG, "SaveSingleOfferWorker POST SLEEP - " + offerId);

        return Result.success();
    }
}
