package com.artemkaxboy.springdatashowroom.customrepo.delegate.repository

import com.artemkaxboy.springdatashowroom.customrepo.delegate.entity.PersonCustom3
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PersonCustom3Repository : JpaRepository<PersonCustom3, Long>
