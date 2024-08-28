create table destinos(

    id SERIAL PRIMARY KEY,
    foto TEXT[],
    meta TEXT NOT NULL,
    texto_descritivo TEXT,
    nome TEXT      NOT NULL,
    preco double precision    NOT NULL
);