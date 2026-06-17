package com.otimizza.teste.domain.entities;

import com.otimizza.teste.domain.defaults.DefaultProvider;
import com.otimizza.teste.domain.defaults.NullDefaultProvider;
import com.otimizza.teste.domain.validators.Validator;
import com.otimizza.teste.domain.validators.common.NotBlankValidator;
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
public class Board implements Serializable {
    private String id;
    private String name;

    @Builder
    public Board(String id, String name) {
        this.id = id;
        this.name = name;
        initializeDefaults();
        validate();
    }

    private void initializeDefaults() {
        DefaultProvider<Board> defaults = new NullDefaultProvider<>(Board::id, (b, id) -> { b.id(id); }, b -> UUID.randomUUID().toString());
        defaults.provide(this);
    }

    private void validate() {
        Validator<Board> validator = new NotBlankValidator<>(Board::name, "Board name cannot be null or blank");
        validator.validate(this);
    }
}
