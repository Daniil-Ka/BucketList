package com.tms.bucketlist

import com.github.javafaker.Faker
import com.tms.bucketlist.domain.Target
import com.tms.bucketlist.domain.Todo
import java.util.*
import kotlin.random.Random

typealias TargetListener = (persons: List<Target>) -> Unit

class TargetsRepository private constructor(){
    var targets = mutableListOf<Target>()

    companion object {
        val instance = TargetsRepository()
    }

    fun getTargetById(id: Long) : Target? = targets.firstOrNull { it.id == id }

    fun removeTarget(target: Target) {
        targets.remove(target)
        notifyChanges()
    }

    fun addTarget(target: Target) {
        targets.add(target)
        notifyChanges()
    }

    private var listeners = mutableListOf<TargetListener>() // Все слушатели

    fun addListener(listener: TargetListener) {
        listeners.add(listener)
        listener.invoke(targets)
    }

    fun removeListener(listener: TargetListener) {
        listeners.remove(listener)
    }

    fun notifyChanges() = listeners.forEach { it.invoke(targets) }

}