package com.digitalrepublic.codechallenge.service.mapper

import com.digitalrepublic.codechallenge.model.Parede
import com.digitalrepublic.codechallenge.service.dto.ParedeDto
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface ParedeMapper: EntityMapper<ParedeDto, Parede> {
}