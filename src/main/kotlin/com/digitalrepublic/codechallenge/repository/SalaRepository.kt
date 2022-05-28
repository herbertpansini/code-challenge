package com.digitalrepublic.codechallenge.repository

import com.digitalrepublic.codechallenge.model.Sala
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SalaRepository: JpaRepository<Sala, Long> {
}