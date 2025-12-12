# ControleEstoque-20240320

# Como baixar e executar o projeto

## 1 - Dependências
1. Java 25
2. VS Code
3. Xampp
4. Maven se não utilizar o VS Code

## 2 - Clonar repositório
No terminal utilizar o comando "git clone https://github.com/PedroHGMelim/ControleEstoque-20240320.git"

## 3 - Baixar as Extensões necessárias no VS Code
1. Debugger for Java by Microsoft
2. Gradle for Java by Microsoft
3. Language Suppor for Java by Red Hat
4. Maven for Java by Microsoft
5. Project Manager for Java by Microsoft
6. Test Runner for Java by Microsoft

## 4 - Executar o projeto
Deixe o Xampp aberto com o MySQL e o Apache rodando
No terminal utilize o comando "mvn clean install" para instalar as dependências do projeto
Depois utilize o comando "mvn spring-boot:run" para executar o projeto
Então a api começara a funcionar em "http://localhost:8080"

## 5 - Testar a api
### Utilizando o Insomnia ou o Postman

### POST

EndPoints: http://localhost:8080/api/clientes
           http://localhost:8080/api/fornecedores
           http://localhost:8080/api/categorias
           http://localhost:8080/api/produtos
           http://localhost:8080/api/vendas

### GET

EndPoints: http://localhost:8080/api/clientes
           http://localhost:8080/api/clientes/ID
           http://localhost:8080/api/fornecedores
           http://localhost:8080/api/fornecedores/ID
           http://localhost:8080/api/categorias
           http://localhost:8080/api/categorias/ID
           http://localhost:8080/api/produtos
           http://localhost:8080/api/produtos/ID
           http://localhost:8080/api/vendas
           http://localhost:8080/api/vendas/ID

### PUT

EndPoints: http://localhost:8080/api/clientes/ID
           http://localhost:8080/api/fornecedores/ID
           http://localhost:8080/api/categorias/ID
           http://localhost:8080/api/produtos/ID

### DELETE

EndPoints: http://localhost:8080/api/clientes/ID
           http://localhost:8080/api/fornecedores/ID
           http://localhost:8080/api/categorias/ID
           http://localhost:8080/api/produtos/ID
           http://localhost:8080/api/vendas/ID
