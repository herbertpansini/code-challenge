package com.digitalrepublic.codechallenge.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = "sala")
class Sala(@Id
           @GeneratedValue(strategy = GenerationType.IDENTITY)
           var id: Long,
           @OneToMany(mappedBy = "sala")
           var paredes: Set<Parede>)