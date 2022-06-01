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
        var latasTintas = mutableListOf(LatasTinta(TamanhoLatasTinta.DEZOITO_LITROS),
            LatasTinta(TamanhoLatasTinta.TRES_VIRGULA_SEIS_LITROS),
            LatasTinta(TamanhoLatasTinta.DOIS_LITROS_E_MEIO),
            LatasTinta(TamanhoLatasTinta.MEIO_LITRO))

        var litrosTinta = this.area / METROS_QUADRADOS_LITRO

        latasTintas.forEach{
            if (litrosTinta >= it.tamanhoLatasTinta.value) {
                it.quantidade = if (TamanhoLatasTinta.MEIO_LITRO.equals(it.tamanhoLatasTinta))
                                    (this.rountToUp(litrosTinta) / it.tamanhoLatasTinta.value).toInt()
                                else
                                    (litrosTinta / it.tamanhoLatasTinta.value).toInt()
                litrosTinta %= it.tamanhoLatasTinta.value
            }
        }
        return this.result(latasTintas)
    }

    private fun rountToUp(value: Double): Double {
        val str = "%.1f".format(value).toDouble().toString().split('.')
        var num = str[0].toInt()
        var dec = str[1].toInt()
        num = if (dec > 5) ++num else num
        dec = if (dec in 1..5) 5 else 0
        return "%.1f".format("$num.$dec".toDouble()).toDouble()
    }

    private fun result(latasTintas: MutableList<LatasTinta>): MutableList<String> {
        val mutableList: MutableList<String> = mutableListOf()
        latasTintas.forEach{
            if (it.quantidade > 0) {
                mutableList.add("${it.quantidade} Lata de ${it.tamanhoLatasTinta.label}")
            }
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