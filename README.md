# Resumo do projeto
Projeto realizado visando a prática com a linguagem Java juntamente com SpringBoot proposto pelo Alura Challenges - Back-End.

Neste desafio foi desenvolvido uma API integrada ao front-end de um site, que disponibiliza informações e recursos do banco de dados, relacionados a destinos de viagens, para exibir fotos, depoimentos e textos ao usuário.

## Requisitos

- ``Java 11 ou superior``
- ``Maven 3.6.0 ou superior``
- ``Banco de dados MySQL``

## Funcionalidades do projeto

- `Funcionalidade 1` `CRUD de um depoimento`: O projeto tem as funcionalidades necessarias para criar cadastrar alterar e deletar uma entidade do tipo Depoimentos.
- `Funcionalidade 2` `Seleção aleatória de 3 depoimentos`: Consulta 3 depoimentos aleatórios do banco para exibir na pagina inicial.
- `Funcionalidade 3` `CRUD de um destino`:  Também é possivel realizar criar cadastrar alterar e deletar uma entidade do tipo Destinos.
- `Funcionalidade 4` `Testes para verificação das Requisições HTTP`:  Testes que verificam o Status Code das requisições GET, POST, PUT e DELETE de todos os endpoints
- 
## Configuração

1. Clone o repositório:

    ```bash
   git clone https://github.com/josermca01/Api-plataforma-de-viagens.git
   cd Api-plataforma-de-viagens

2. Configure o banco de dados:

   Dentro do arquivo application.properties altere os seguintes campos para as informações do seu banco de dados:
   ```
    spring.datasource.url=url_do_seu_banco_de_dados
    spring.datasource.username=seu_usuario
    spring.datasource.password=sua_senha
