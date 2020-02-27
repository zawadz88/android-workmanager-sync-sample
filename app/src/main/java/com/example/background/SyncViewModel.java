package com.example.background;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.ExistingWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import com.example.background.workers.SaveSingleOfferWorker;
import com.example.background.workers.ScheduleSyncWorker;
import com.example.background.workers.SyncWorker;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.example.background.Constants.OFFER_ID;
import static com.example.background.Constants.SCHEDULE_SYNC_WORK_NAME;
import static com.example.background.Constants.SYNC_WORK_NAME;

public class SyncViewModel extends AndroidViewModel {

    private WorkManager mWorkManager;
    private LiveData<List<WorkInfo>> syncWorkInfo;

    public SyncViewModel(@NonNull Application application) {
        super(application);
        mWorkManager = WorkManager.getInstance(application);

        syncWorkInfo = mWorkManager.getWorkInfosForUniqueWorkLiveData(SYNC_WORK_NAME);
        enqueueScheduleSyncPeriodicWork();
    }

    void syncOffers() {
        mWorkManager
                .enqueueUniqueWork(SYNC_WORK_NAME,
                        ExistingWorkPolicy.APPEND,
                        OneTimeWorkRequest.from(SyncWorker.class));
    }

    void saveOffer() {
        OneTimeWorkRequest.Builder requestBuilder = new OneTimeWorkRequest.Builder(SaveSingleOfferWorker.class);
        Data data = new Data.Builder()
                .putString(OFFER_ID, UUID.randomUUID().toString())
                .build();
        requestBuilder.setInputData(data);
        mWorkManager
                .enqueueUniqueWork(SYNC_WORK_NAME,
                        ExistingWorkPolicy.APPEND,
                        requestBuilder.build());
    }

    void cancelSyncWork() {
        mWorkManager.cancelUniqueWork(SYNC_WORK_NAME);
    }

    void cancelScheduleSyncWork() {
        mWorkManager.cancelUniqueWork(SCHEDULE_SYNC_WORK_NAME);
    }

    LiveData<List<WorkInfo>> getSyncWorkInfo() {
        return syncWorkInfo;
    }

    private void enqueueScheduleSyncPeriodicWork() {
        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();
        PeriodicWorkRequest scheduleSyncRequest = new PeriodicWorkRequest.Builder(ScheduleSyncWorker.class, 1, TimeUnit.HOURS)
                .setConstraints(constraints)
                .build();
        mWorkManager.enqueueUniquePeriodicWork(SCHEDULE_SYNC_WORK_NAME, ExistingPeriodicWorkPolicy.KEEP, scheduleSyncRequest);
    }
}
