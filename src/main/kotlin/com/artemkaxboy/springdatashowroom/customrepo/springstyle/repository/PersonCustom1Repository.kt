package com.artemkaxboy.springdatashowroom.customrepo.springstyle.repository

import com.artemkaxboy.springdatashowroom.customrepo.springstyle.entity.PersonCustom1
import org.springframework.data.jpa.repository.JpaRepository

interface PersonCustom1Repository : JpaRepository<PersonCustom1, Long>, PersonCustom1RepositoryCustom
