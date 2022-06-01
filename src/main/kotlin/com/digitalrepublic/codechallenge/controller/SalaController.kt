package com.digitalrepublic.codechallenge.controller

import com.digitalrepublic.codechallenge.service.SalaService
import com.digitalrepublic.codechallenge.service.dto.SalaDto
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/sala")
class SalaController(private val salaService: SalaService) {

    @GetMapping
    fun findAll(pageable: Pageable) = ResponseEntity.ok(this.salaService.findAll(pageable))

    @GetMapping("{id}")
    fun findById(@PathVariable id: Long) = ResponseEntity.ok(this.salaService.findById(id))

    @PostMapping
    fun save(@Valid @RequestBody sala: SalaDto) = ResponseEntity.status(HttpStatus.CREATED).body(this.salaService.save(sala))

    @PutMapping("{id}")
    fun update(@PathVariable id: Long, @Valid @RequestBody sala: SalaDto) = ResponseEntity.ok(this.salaService.update(id, sala))

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteById(@PathVariable id: Long) = ResponseEntity.ok(this.salaService.findById(id))
}