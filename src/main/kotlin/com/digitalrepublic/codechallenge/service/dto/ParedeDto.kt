package com.digitalrepublic.codechallenge.service.dto

import javax.persistence.Id

class ParedeDto {
    @Id
    var id: Long = 0L
    var largura: Double = 0.00
    var altura: Double = 0.00
    var quantidadePortas: Int = 0
    var quantidadeJanelas: Int = 0
}