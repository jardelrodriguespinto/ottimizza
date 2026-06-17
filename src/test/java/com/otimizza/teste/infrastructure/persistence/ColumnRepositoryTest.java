package com.otimizza.teste.infrastructure.persistence;

import com.otimizza.teste.domain.entities.Column;
import com.otimizza.teste.domain.repositories.BoardRepository;
import com.otimizza.teste.domain.repositories.ColumnRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import({JpaColumnRepositoryImpl.class, JpaBoardRepositoryImpl.class})
@ActiveProfiles("test")
class ColumnRepositoryTest {

    @Autowired
    private ColumnRepository columnRepository;

    @Autowired
    private BoardRepository boardRepository;

    private String boardId;

    @BeforeEach
    void setUp() {
        boardId = UUID.randomUUID().toString();
    }

    @Test
    @DisplayName("Deve salvar e buscar colunas por boardId")
    void deveSalvarEBuscarColunas() {
        Column col2 = new Column(UUID.randomUUID().toString(), "Fazendo", 1, boardId);
        Column col1 = new Column(UUID.randomUUID().toString(), "A Fazer", 0, boardId);

        columnRepository.save(col2);
        columnRepository.save(col1);

        List<Column> columns = columnRepository.findByBoardId(boardId);

        assertThat(columns.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("Deve deletar coluna por ID")
    void deveDeletarColuna() {
        String id = UUID.randomUUID().toString();
        Column col = new Column(id, "Deletar", 0, boardId);
        columnRepository.save(col);

        columnRepository.deleteById(id);

        assertThat(columnRepository.findById(id)).isEmpty();
    }
}
