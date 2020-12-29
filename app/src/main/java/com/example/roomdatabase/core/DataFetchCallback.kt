package com.example.roomdatabase.core

interface DataFetchCallback<T> {
    fun onSuccess(data: T)
    fun onError(throwable: Throwable)
}