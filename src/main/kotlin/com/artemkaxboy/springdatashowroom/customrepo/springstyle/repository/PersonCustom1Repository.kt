package com.artemkaxboy.springdatashowroom.customrepo.springstyle.repository

import com.artemkaxboy.springdatashowroom.customrepo.springstyle.entity.PersonCustom1
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PersonCustom1Repository : JpaRepository<PersonCustom1, Long>, PersonCustom1RepositoryCustom
