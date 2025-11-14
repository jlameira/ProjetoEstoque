# 📦 Sistema de Gerenciamento de Estoque

Sistema de cadastro e gerenciamento de estoque desenvolvido em Java, aplicando conceitos de Orientação a Objetos e Padrões de Projeto.

## 🎯 Funcionalidades

- ✅ Adicionar produtos (Comum, Alimento, Eletrônico, Roupa)
- ✅ Listar todos os produtos
- ✅ Buscar produto por código
- ✅ Atualizar preço de produtos
- ✅ Remover produtos
- ✅ Validação de dados de entrada
- ✅ Detecção de produtos vencidos (Alimentos)

## 🏗️ Arquitetura

### Estrutura de Classes

```
Produto (classe base)
├── Alimento (data de validade)
├── Eletronico (meses de garantia)
└── Roupa (tamanho)

Estoque (gerencia coleção de produtos)
ProdutoFactory (cria produtos - Factory Pattern)
TipoProduto (enum para tipos)
TamanhoRoupa (enum para tamanhos)
App (interface do usuário)
```

### Padrões de Projeto Aplicados

- **Factory Pattern**: `ProdutoFactory` centraliza criação de produtos
- **Enum Pattern**: `TipoProduto` e `TamanhoRoupa` para type safety
- **Single Responsibility**: Cada classe tem uma responsabilidade clara

## 🚀 Como Executar

### Pré-requisitos
- Java 8 ou superior

### Compilação e Execução

```bash
# Compilar
javac *.java

# Executar
java App
```

## 💡 Conceitos Demonstrados

### Orientação a Objetos
- ✅ **Herança**: Produto → Alimento, Eletronico, Roupa
- ✅ **Polimorfismo**: Override de `toString()`
- ✅ **Encapsulamento**: Atributos privados com getters/setters
- ✅ **Abstração**: Interface clara para o estoque

### Clean Code
- ✅ **DRY**: Sem código duplicado
- ✅ **SRP**: Separação de responsabilidades
- ✅ **Fail Fast**: Validações explícitas com exceções
- ✅ **Type Safety**: Tipos corretos (LocalDate, Enum)
- ✅ **Métodos pequenos**: Cada um faz uma coisa

### Boas Práticas
- ✅ Validação de entrada com exceções
- ✅ Uso de `ArrayList` ao invés de array fixo
- ✅ `LocalDate` para datas (ao invés de String)
- ✅ Enum para conjuntos fixos de valores
- ✅ Constantes para magic numbers
- ✅ Fechamento correto de recursos (Scanner)
- ✅ Enhanced for loops

## 📝 Exemplo de Uso

```
=== Sistema de Cadastro de Estoque ===
[1] Adicionar Produto
[2] Listar Produtos
[3] Buscar Produto por Código
[4] Atualizar Preço
[5] Remover Produto
[0] Sair
Escolha uma opção: 1

=== Tipo de Produto ===
[1] Produto Comum
[2] Alimento
[3] Eletrônico
[4] Roupa
Escolha o tipo: 2

Digite o nome do produto: Leite
Digite o Codigo do Produto: 101
Digite o preço do produto: 5.50
Digite a validade do Alimento (dd/MM/yyyy): 20/12/2024
✓ Produto adicionado com sucesso!
```

## 🔄 Refatorações Aplicadas

Este projeto passou por refatoração educacional. Principais melhorias:

1. **Array → ArrayList**: Capacidade dinâmica
2. **Validações silenciosas → Exceções**: Fail Fast
3. **String → LocalDate**: Tipo correto para datas
4. **int → Enum**: Type safety para tamanhos
5. **Método monolítico → Métodos pequenos**: Melhor organização
6. **Magic numbers → Constantes**: Código mais legível

📖 **Veja o guia completo:** [REFACTORING_GUIDE.md](REFACTORING_GUIDE.md)

## 🎓 Aprendizados

### Para Iniciantes
Este projeto demonstra:
- Como estruturar um sistema OO básico
- Quando e como usar herança
- Importância de validações
- Uso correto de tipos de dados
- Organização de código em métodos

### Próximos Passos
- [ ] Adicionar persistência (salvar em arquivo/banco)
- [ ] Implementar testes unitários (JUnit)
- [ ] Adicionar interface gráfica (JavaFX/Swing)
- [ ] Aplicar mais padrões (Strategy, Observer)
- [ ] Implementar busca por nome/categoria

## 📚 Tecnologias

- Java 8+
- Collections Framework (ArrayList)
- Java Time API (LocalDate)
- Enums
- Exception Handling

## 🤝 Contribuindo

Este é um projeto educacional. Sugestões e melhorias são bem-vindas!

## 📄 Licença

Projeto educacional - livre para uso e modificação.

---

**Nota:** Este projeto foi refatorado com foco educacional, demonstrando evolução de código iniciante para código com boas práticas profissionais.
