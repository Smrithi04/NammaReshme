package com.example.nammareshme.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nammareshme.data.Repository
import com.example.nammareshme.data.models.Batch
import com.example.nammareshme.data.models.ClimateLog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    // ACTIVE BATCH
    val activeBatch =
        repository.getActiveBatch()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = null
            )

    // LATEST LOG
    val latestLog = activeBatch.flatMapLatest { batch ->
        if (batch != null) {
            repository.getLogsForBatch(batch.id).map { it.firstOrNull() }
        } else {
            flowOf(null)
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = null
    )

    // HISTORY
    val batchHistory =
        repository.getAllBatches()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )

    // INSERT BATCH
    fun createBatch(
        name: String,
        breed: String,
        farmerName: String,
        region: String,
        hatchDate: String
    ) {
        viewModelScope.launch {
            repository.insertBatch(
                Batch(
                    name = name,
                    breed = breed,
                    farmerName = farmerName,
                    region = region,
                    hatchDate = hatchDate
                )
            )
        }
    }

    // INSERT CLIMATE LOG
    fun addClimateLog(
        batchId: String,
        temperature: Double,
        humidity: Double,
        advice: String,
        status: String
    ) {
        viewModelScope.launch {
            repository.insertClimateLog(
                ClimateLog(
                    batchId = batchId,
                    timestamp = System.currentTimeMillis(),
                    temperature = temperature,
                    humidity = humidity,
                    advice = advice,
                    status = status
                )
            )
        }
    }

    // GET LOGS
    fun getLogsForBatch(batchId: String) = repository.getLogsForBatch(batchId)

    // ARCHIVE
    fun archiveBatch(batchId: String) {
        viewModelScope.launch {
            repository.archiveBatch(batchId)
        }
    }
}