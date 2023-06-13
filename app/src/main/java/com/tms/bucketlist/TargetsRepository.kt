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

    init {
        val faker = Faker.instance() // Переменная для создания случайных данных

        targets = (1..5).map {
            val data = mutableListOf<Todo>()
            val rnd = Random(10)
            (0..5).forEach { i -> data.add(Todo("name", i.toString(), rnd.nextBoolean())) }
            Target(
                id = it.toLong(),
                name = faker.name().fullName(),
                deadline = faker.date().birthday().toString(),
                description = faker.name().bloodGroup(),
                budget = faker.number().digit(),
                todo = data
            )
        }.toMutableList()
    }

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

    private fun notifyChanges() = listeners.forEach { it.invoke(targets) }

}