package com.tms.bucketlist

import com.github.javafaker.Faker
import com.tms.bucketlist.domain.Category
import com.tms.bucketlist.domain.Privacy
import com.tms.bucketlist.domain.Target

typealias TargetListener = (persons: List<Target>) -> Unit

class TargetsRepository private constructor(){
    var targets = mutableListOf<Target>()

    companion object {
        val instance = TargetsRepository()
    }

    fun getTargetById(id: Long) : Target? = targets.firstOrNull { it.id == id }

    init {
        val faker = Faker.instance() // Переменная для создания случайных данных

        targets = (1..5).map {
            Target(
                id = it.toLong(),
                name = faker.name().fullName(),
                description = faker.name().bloodGroup(),
                photoUrl = "fake",
                category = Category.DefaultCategory,
                privacy = Privacy.Public,
                todo = listOf()
            )
        }.toMutableList()
    }

    fun removeTarget(target: Target) {
        targets.remove(target)
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

    private fun notifyChanges() = listeners.forEach { it.invoke(targets) }
}