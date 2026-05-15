package com.example.nammareshme.data

import com.example.nammareshme.data.models.Batch
import com.example.nammareshme.data.models.ClimateLog
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(
    private val firestore: FirebaseFirestore
) {

    private val batchesCollection = firestore.collection("batches")
    private val logsCollection = firestore.collection("climate_logs")

    fun getActiveBatch(): Flow<Batch?> = callbackFlow {
        val listener = batchesCollection
            .whereEqualTo("status", "ACTIVE")
            .limit(1)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                val batch = snapshot?.documents?.firstOrNull()?.toObject(Batch::class.java)?.copy(
                    id = snapshot.documents.firstOrNull()?.id ?: ""
                )
                trySend(batch)
            }
        awaitClose { listener.remove() }
    }

    fun getAllBatches(): Flow<List<Batch>> = callbackFlow {
        val listener = batchesCollection
            .orderBy("hatchDate", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                val batches = snapshot?.documents?.mapNotNull { doc ->
                    doc.toObject(Batch::class.java)?.copy(id = doc.id)
                } ?: emptyList()
                trySend(batches)
            }
        awaitClose { listener.remove() }
    }

    fun getLogsForBatch(batchId: String): Flow<List<ClimateLog>> = callbackFlow {
        val listener = logsCollection
            .whereEqualTo("batchId", batchId)
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                val logs = snapshot?.documents?.mapNotNull { doc ->
                    doc.toObject(ClimateLog::class.java)?.copy(id = doc.id)
                } ?: emptyList()
                trySend(logs)
            }
        awaitClose { listener.remove() }
    }

    suspend fun insertBatch(batch: Batch) {
        batchesCollection.add(batch).await()
    }

    suspend fun insertClimateLog(log: ClimateLog) {
        logsCollection.add(log).await()
    }

    suspend fun archiveBatch(batchId: String) {
        batchesCollection.document(batchId).update("status", "ARCHIVED").await()
    }
}