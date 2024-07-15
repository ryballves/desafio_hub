package com.ryballves.forum_hub.domain.dto;

import com.ryballves.forum_hub.domain.model.Topico;

import java.time.LocalDate;

public record DadosListaTopico(String titulo, String mensagem, String nomeAutor, String email_autor, LocalDate data_criacao, String estadoDoTopico, String curso) {

    public DadosListaTopico (Topico topico){
        this(   topico.getTitulo(),
                topico.getMensagem(),
                topico.getNomeAutor(),
                topico.getEmailAutor(),
                topico.getDataDeCriacao(),
                topico.getEstadoDoTopico(),
                topico.getCurso());
    }
}
