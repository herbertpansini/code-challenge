package com.digitalrepublic.codechallenge.repository

import com.digitalrepublic.codechallenge.model.Parede
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ParedeRepository: JpaRepository<Parede, Long> {
}