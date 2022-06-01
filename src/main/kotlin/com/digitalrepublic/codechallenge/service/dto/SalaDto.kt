package com.digitalrepublic.codechallenge.service.dto

import javax.persistence.Id

class SalaDto {
    @Id
    var id: Long = 0L
    var paredes: MutableList<ParedeDto> = mutableListOf()
    var area: Double = 0.0
    var latas: MutableList<String> = mutableListOf()
}