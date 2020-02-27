package com.example.background

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.work.WorkInfo
import kotlinx.android.synthetic.main.activity_sync.cancelScheduleSyncButton
import kotlinx.android.synthetic.main.activity_sync.cancelSyncButton
import kotlinx.android.synthetic.main.activity_sync.progressBar
import kotlinx.android.synthetic.main.activity_sync.saveButton
import kotlinx.android.synthetic.main.activity_sync.syncButton

class SyncActivity : AppCompatActivity() {

    private val syncViewModel: SyncViewModel by lazy { ViewModelProviders.of(this).get(SyncViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sync)
        cancelSyncButton.setOnClickListener { syncViewModel.cancelSyncWork() }
        cancelScheduleSyncButton.setOnClickListener { syncViewModel.cancelScheduleSyncWork() }
        syncButton.setOnClickListener { syncViewModel.syncOffers() }
        saveButton.setOnClickListener { syncViewModel.saveOffer() }
        syncViewModel.syncWorkInfo.observe(this, Observer { listOfWorkInfo: List<WorkInfo>? ->
            // If there are no matching work info, do nothing
            if (listOfWorkInfo == null || listOfWorkInfo.isEmpty()) {
                return@Observer
            }

            val notFinished = listOfWorkInfo.any { !it.state.isFinished }
            if (notFinished) {
                showWorkInProgress()
            } else {
                showWorkFinished()
            }
        })
    }

    /**
     * Shows and hides views for when the Activity is processing an image
     */
    private fun showWorkInProgress() {
        progressBar.visibility = View.VISIBLE
        cancelSyncButton.visibility = View.VISIBLE
    }

    /**
     * Shows and hides views for when the Activity is done processing an image
     */
    private fun showWorkFinished() {
        progressBar.visibility = View.GONE
        cancelSyncButton.visibility = View.GONE
    }
}
