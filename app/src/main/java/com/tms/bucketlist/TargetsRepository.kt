package com.tms.bucketlist

import com.github.javafaker.Faker
import com.tms.bucketlist.domain.Category
import com.tms.bucketlist.domain.Privacy
import com.tms.bucketlist.domain.Target

class TargetsRepository {
    public var targets = mutableListOf<Target>()

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
}