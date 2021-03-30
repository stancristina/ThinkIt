package org.fmi.unibuc.service.mapper;


import org.fmi.unibuc.domain.*;
import org.fmi.unibuc.service.dto.WordDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Word} and its DTO {@link WordDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface WordMapper extends EntityMapper<WordDTO, Word> {



    default Word fromId(Long id) {
        if (id == null) {
            return null;
        }
        Word word = new Word();
        word.setId(id);
        return word;
    }
}
