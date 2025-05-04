package com.panevrn.data.mapper

import com.panevrn.data.entity.CourseDto
import com.panevrn.data.entity.CourseListResponse
import com.panevrn.domain.model.CourseModel


// Маппер: API → Domain
fun CourseListResponse.toDomain(): List<CourseModel> {
    return courses.map { dto ->
        CourseModel(
            id = dto.id.toString(),
            title = dto.title,
            text = dto.text,
            price = dto.price,
            rate = dto.rate.toDouble(),
            startDate = dto.startDate,
            publishDate = dto.publishDate,
            hasLike = dto.hasLike
        )
    }
}


//  Маппер: Dto → Domain
fun CourseDto.toDomain(): CourseModel {
    return CourseModel(
        id = id.toString(),
        title = title,
        text = text,
        price = price,
        rate = rate.toDouble(),
        startDate = startDate,
        hasLike = hasLike,
        publishDate = publishDate
    )
}

