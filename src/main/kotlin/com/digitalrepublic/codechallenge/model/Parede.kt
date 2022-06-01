package com.digitalrepublic.codechallenge.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "parede")
class Parede {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L

    @JsonIgnoreProperties(value = ["paredes"])
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="sala.id")
    var sala: Sala? = null

    var largura: Double = 0.00

    var altura: Double = 0.00

    @Column(name = "quantidade_portas")
    var quantidadePortas: Int = 0

    @Column(name = "quantidade_janelas")
    var quantidadeJanelas: Int = 0

    val area get() = this.largura * this.altura

    val areaPortas get() = this.quantidadePortas * Porta.AREA

    val areaJanelas get() = this.quantidadeJanelas * Janela.AREA

    val areaTotalPortasJanelas get() = this.areaPortas + this.areaJanelas

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Parede

        if (id != other.id) return false

        return true
    }
}