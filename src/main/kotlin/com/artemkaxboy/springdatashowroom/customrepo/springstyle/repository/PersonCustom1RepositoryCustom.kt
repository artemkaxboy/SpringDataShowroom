package com.artemkaxboy.springdatashowroom.customrepo.springstyle.repository

import com.artemkaxboy.springdatashowroom.customrepo.springstyle.entity.PersonCustom1

interface PersonCustom1RepositoryCustom {

    fun updateRatingById(id: Long, rating: Int): PersonCustom1?
}
