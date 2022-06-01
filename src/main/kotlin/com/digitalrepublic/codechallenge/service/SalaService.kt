package com.digitalrepublic.codechallenge.service

import com.digitalrepublic.codechallenge.service.dto.SalaDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface SalaService {
    fun findAll(pageable: Pageable): Page<SalaDto>

    fun findById(id: Long): SalaDto

    fun save(sala: SalaDto): SalaDto

    fun update(id: Long, sala: SalaDto): SalaDto

    fun deleteById(id: Long)
}