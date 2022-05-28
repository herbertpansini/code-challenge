package com.digitalrepublic.codechallenge.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "parede")
class Parede(@Id
             @GeneratedValue(strategy = GenerationType.IDENTITY)
             var id: Long,
             @ManyToOne
             @JoinColumn(name="sala.id", nullable = false)
             var sala: Sala,
             var largura: Float,
             var altura: Float,
             var area: Float = largura * altura,
             var janelas: Int,
             var portas: Int)