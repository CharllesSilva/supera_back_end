
# Supera back-end

## Descrição

O projeto foi desenvolvido para solucionar o desafio proposto e está disponível no repositório [supera_back_end](https://github.com/CharllesSilva/supera_back_end.git). A aplicação visa atender aos requisitos específicos do desafio e fornecer funcionalidades relacionadas às transações financeiras.

## Requisitos

Certifique-se de ter o seguinte software instalado em sua máquina:

- Java Development Kit (JDK) versão 11

## Instalação

Siga as etapas abaixo para instalar e executar a aplicação:

1. Clone este repositório para o seu diretório local:

```bash
git clone https://github.com/CharllesSilva/supera_back_end.git
```

2. Navegue até o diretório do projeto:

```bash
cd supera_back_end
```

3. Compile o projeto usando o Maven:

```bash
mvn clean install
```

## Uso

Após executar a aplicação, você pode interagir com a API usando a URL de teste a seguir:

```http
GET http://localhost:8080/transactions?initial.date=2019-01-01&final.date=2023-12-31&account.id=1&name=Ronnyscley
```

Certifique-se de substituir os parâmetros `initial.date`, `final.date`, `account.id` e `name` pelos valores adequados, conforme necessário.
