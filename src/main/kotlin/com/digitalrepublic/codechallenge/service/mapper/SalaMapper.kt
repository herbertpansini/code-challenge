package com.digitalrepublic.codechallenge.service.mapper

import com.digitalrepublic.codechallenge.model.Sala
import com.digitalrepublic.codechallenge.service.dto.SalaDto
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface SalaMapper: EntityMapper<SalaDto, Sala> {
}