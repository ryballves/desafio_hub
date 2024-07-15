package com.ryballves.forum_hub.domain.dto;

import com.ryballves.forum_hub.domain.model.Topico;

public record DadosDetalhamentoTopico(Topico topico, String extraInfo, String titulo, String mensagem, String nomeAutor, String emailAutor, String dataDeCriacao, String estadoDoTopico, String curso) {

    public DadosDetalhamentoTopico(Topico topico, String extraInfo) {
        this(topico, extraInfo, topico.getTitulo(), topico.getMensagem(), topico.getNomeAutor(), topico.getEmailAutor(), topico.getDataDeCriacao().toString(), topico.getEstadoDoTopico(), topico.getCurso());
    }
}