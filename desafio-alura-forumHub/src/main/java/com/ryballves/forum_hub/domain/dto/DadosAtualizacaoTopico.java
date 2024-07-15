package com.ryballves.forum_hub.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoTopico(

        @NotNull
        Long id,

        String emailAutor,

        @NotBlank
        String mensagem,

        String curso)
{


}
