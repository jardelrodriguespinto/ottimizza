package com.otimizza.teste.domain.entities;

import com.otimizza.teste.domain.defaults.DefaultProvider;
import com.otimizza.teste.domain.defaults.NullDefaultProvider;
import com.otimizza.teste.domain.validators.Validator;
import com.otimizza.teste.domain.validators.common.NotBlankValidator;
import com.otimizza.teste.domain.validators.common.NotNullValidator;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Accessors(fluent = true)
public class Column implements Serializable {
    private String id;
    private String name;
    private int position;
    private String boardId;

    @Builder
    public Column(String id, String name, int position, String boardId) {
        this.id = id;
        this.name = name;
        this.position = position;
        this.boardId = boardId;
        initializeDefaults();
        validate();
    }

    private void initializeDefaults() {
        DefaultProvider<Column> defaults = new NullDefaultProvider<>(Column::id, (c, id) -> { c.id(id); }, c -> UUID.randomUUID().toString());
        defaults.provide(this);
    }

    private void validate() {
        Validator<Column> validator = new NotBlankValidator<>(Column::name, "Column name cannot be null or blank");
        validator.linkWith(new NotNullValidator<>(Column::boardId, "Board id cannot be null"));
        validator.validate(this);
    }
}
