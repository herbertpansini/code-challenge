package com.digitalrepublic.codechallenge.service.impl

import com.digitalrepublic.codechallenge.model.Parede
import com.digitalrepublic.codechallenge.model.Porta
import com.digitalrepublic.codechallenge.model.Sala
import com.digitalrepublic.codechallenge.service.dto.SalaDto
import com.digitalrepublic.codechallenge.repository.SalaRepository
import com.digitalrepublic.codechallenge.service.SalaService
import com.digitalrepublic.codechallenge.service.mapper.SalaMapper
import org.springframework.beans.BeanUtils
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.server.ResponseStatusException

@Service
@Transactional
class SalaServiceImpl(private val salaRepository: SalaRepository,
                      private val salaMapper: SalaMapper): SalaService {
    @Transactional(readOnly = true)
    override fun findAll(pageable: Pageable) = this.salaRepository.findAll(pageable).map(this.salaMapper::toDto)

    @Transactional(readOnly = true)
    override fun findById(id: Long) = this.salaMapper.toDto(salaRepository.findById(id).orElseThrow { ResponseStatusException(HttpStatus.NO_CONTENT, "Sala $id não encontrada") })

    override fun save(sala: SalaDto): SalaDto {
        val salaEntity = this.salaMapper.toEntity(sala)
        this.validate(salaEntity)
        return this.salaMapper.toDto(this.salaRepository.save(salaEntity))
    }

    override fun update(id: Long, sala: SalaDto): SalaDto {
        val salaEntity = this.salaMapper.toEntity(sala)
        this.validate(salaEntity)
        val salaUpdate = this.salaMapper.toEntity(this.findById(id))

        var eliminarParedes = mutableListOf<Parede>()
        salaUpdate.paredes.forEach{
            if (!salaEntity.paredes.contains(it)) {
                eliminarParedes.add(it)
            }
        }
        eliminarParedes.forEach(salaUpdate::removeParede)

        BeanUtils.copyProperties(salaEntity, salaUpdate, "id")
        return this.salaMapper.toDto(this.salaRepository.save(salaUpdate))
    }

    override fun deleteById(id: Long) = this.salaRepository.deleteById(id)

    private fun validate(sala: Sala) {
        if (this.validaQuantidadeParedes(sala)) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "A sala de conter $QUANTIDADE_PAREDES paredes.")
        }

        if (!this.validaAreaParede(sala)) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Nenhuma parede pode ter menos de $METROS_QUADRADOS_MINIMO metro quadrado nem mais de $METROS_QUADRADOS_MAXIMO metros quadrados.")
        }

        if (!this.validaTotalAreaPortasJanelas(sala)) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "O total de área das portas e janelas deve ser no máximo $TOTAL_AREA_PORTAS_JANELAS% da área de parede.")
        }

        if (!this.validaAlturaParedeComPorta(sala)) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "A altura de paredes com porta deve ser, no mínimo, ${ALTURA_PAREDES_COM_PORTAS * 10} centímetros maior que a altura da porta.")
        }
    }

    private fun validaQuantidadeParedes(sala: Sala): Boolean {
        return sala.paredes.size != QUANTIDADE_PAREDES
    }

    private fun validaAreaParede(sala: Sala): Boolean {
        sala.paredes.forEach {
            if (it.area < METROS_QUADRADOS_MINIMO || it.area > METROS_QUADRADOS_MAXIMO) {
                return false
            }
        }
        return true
    }

    private fun validaTotalAreaPortasJanelas(sala: Sala): Boolean {
        sala.paredes.forEach {
            if ((it.areaTotalPortasJanelas * 100) / it.area > TOTAL_AREA_PORTAS_JANELAS) {
                return false
            }
        }
        return true
    }

    private fun validaAlturaParedeComPorta(sala: Sala): Boolean {
        sala.paredes.forEach {
            if (it.quantidadePortas > 0 && it.altura - Porta.ALTURA < ALTURA_PAREDES_COM_PORTAS) {
                return false
            }
        }
        return true
    }

    companion object {
        const val QUANTIDADE_PAREDES = 4
        const val METROS_QUADRADOS_MINIMO = 1
        const val METROS_QUADRADOS_MAXIMO = 50
        const val TOTAL_AREA_PORTAS_JANELAS = 50
        const val ALTURA_PAREDES_COM_PORTAS = 0.3
    }
}