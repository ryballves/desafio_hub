package com.ryballves.forum_hub.domain.repository;

import com.ryballves.forum_hub.domain.model.Topico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TopicoRepository extends JpaRepository<Topico, Long> {

    Optional<Topico> findByTituloAndNomeAutor(String titulo, String nomeAutor);

}