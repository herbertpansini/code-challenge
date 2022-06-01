package com.digitalrepublic.codechallenge.model

enum class TamanhoLatasTinta(val label: String, val value: Double) {
    DEZOITO_LITROS("18L",18.0),
    TRES_VIRGULA_SEIS_LITROS("3,6L",3.6),
    DOIS_LITROS_E_MEIO("2,5L",2.5),
    MEIO_LITRO("0,5L",0.5);

    override fun toString() = label
}