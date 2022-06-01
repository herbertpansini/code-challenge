package com.digitalrepublic.codechallenge.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = "sala")
class Sala {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L

    @JsonIgnoreProperties(value = ["sala"], allowSetters = true)
    @OneToMany(mappedBy = "sala", fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true)
    var paredes: MutableList<Parede> = mutableListOf()
        set(value: MutableList<Parede>) {
            field.clear()
            value.forEach(this::addParede)
        }

    val area get(): Double {
        var sum: Double = 0.0
        this.paredes.forEach{
            sum += it.area - it.areaTotalPortasJanelas
        }
        return sum
    }

    val latas get(): MutableList<String> {
        var latas18: Int = 0
        var latas36: Int = 0
        var latas25: Int = 0
        var latas05: Int = 0

        var litrosTinta = this.area / METROS_QUADRADOS_LITRO

        while(litrosTinta > 0) {
            if (litrosTinta > Latas.LATA_18L) {
                litrosTinta -= Latas.LATA_18L
                latas18++
            } else if (litrosTinta > Latas.LATA_3_6L) {
                litrosTinta -= Latas.LATA_3_6L
                latas36++
            } else if (litrosTinta > Latas.LATA_2_5L) {
                litrosTinta -= Latas.LATA_2_5L
                latas25++
            } else {
                litrosTinta -= Latas.LATA_0_5L
                latas05++
            }
        }
        return this.result(latas18, latas36, latas25, latas05)
    }

    fun result(latas18: Int, latas36: Int, latas25: Int, latas05: Int): MutableList<String> {
        val mutableList: MutableList<String> = mutableListOf()

        if (latas18 > 0) {
            mutableList.add("$latas18 lata de 18L")
        }

        if (latas36 > 0) {
            mutableList.add("$latas36 lata de 3,6L")
        }

        if (latas25 > 0) {
            mutableList.add("$latas25 lata de 2,5L")
        }

        if (latas05 > 0) {
            mutableList.add("$latas05 lata de 0,5L")
        }

        return mutableList
    }

    fun addParede(parede: Parede) {
        this.paredes.add(parede)
        parede.sala = this
    }

    fun removeParede(parede: Parede) {
        this.paredes.remove(parede)
        parede.sala = null
    }

    companion object {
        const val METROS_QUADRADOS_LITRO = 5
    }
}