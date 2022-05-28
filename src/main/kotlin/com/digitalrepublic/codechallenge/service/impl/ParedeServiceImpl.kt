package com.digitalrepublic.codechallenge.service.impl

import com.digitalrepublic.codechallenge.repository.ParedeRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class ParedeServiceImpl(private val paredeRepository: ParedeRepository) {

}