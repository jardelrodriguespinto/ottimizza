CREATE TABLE boards (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE columns (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    position INTEGER NOT NULL,
    board_id UUID NOT NULL,
    CONSTRAINT fk_board FOREIGN KEY (board_id) REFERENCES boards(id)
);

CREATE TABLE tasks (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    position INTEGER NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    due_date TIMESTAMP WITH TIME ZONE,
    completed BOOLEAN NOT NULL DEFAULT FALSE,
    tags TEXT,
    column_id UUID NOT NULL,
    CONSTRAINT fk_column FOREIGN KEY (column_id) REFERENCES columns(id)
);
