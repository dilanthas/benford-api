package org.benford.common

interface Service {
    suspend fun start()
    suspend fun stop()
}