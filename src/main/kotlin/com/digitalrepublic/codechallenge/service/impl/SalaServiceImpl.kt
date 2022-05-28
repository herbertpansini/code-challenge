package com.digitalrepublic.codechallenge.service.impl

import com.digitalrepublic.codechallenge.model.Sala
import com.digitalrepublic.codechallenge.repository.SalaRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class SalaServiceImpl(private val salaRepository: SalaRepository) {

    fun salvar(sala: Sala) {
        validate(sala);
    }

    private fun validate(sala: Sala) {
        sala.paredes.forEach{
            it.area >= 1 && it.area <= 50
        }
    }
}