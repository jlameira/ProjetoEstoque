# 🔧 Guia de Refatoração - Projeto Estoque

Este documento explica todas as melhorias aplicadas ao projeto original, com foco educacional.

## 📋 Índice
1. [Visão Geral](#visão-geral)
2. [Refatorações Aplicadas](#refatorações-aplicadas)
3. [Princípios de Clean Code](#princípios-de-clean-code)
4. [Padrões de Projeto](#padrões-de-projeto)
5. [Próximos Passos](#próximos-passos)

---

## 🎯 Visão Geral

### O que foi mantido (pontos fortes do código original)
✅ **Factory Pattern** bem implementado  
✅ **Herança** corretamente aplicada  
✅ **Enum** para tipos de produto  
✅ **Tratamento de exceções** básico  

### O que foi melhorado
🔄 Array fixo → ArrayList dinâmico  
🔄 Validações silenciosas → Exceções explícitas  
🔄 Tipos inadequados → Tipos corretos (LocalDate, Enum)  
🔄 Código monolítico → Métodos pequenos e focados  
🔄 Magic numbers → Constantes nomeadas  

---

## 🔄 Refatorações Aplicadas

### REFACTOR #1: Array → ArrayList

**Arquivo:** `Estoque.java`

#### Antes
```java
private Produto[] produto;
private int proximaPosLivre = 0;

public Estoque(int capacidade) {
    this.produto = new Produto[capacidade];
}
```

#### Depois
```java
private List<Produto> produtos;

public Estoque() {
    this.produtos = new ArrayList<>();
}
```

#### Por quê?
- ❌ **Array fixo**: Limitado a 10 produtos, código complexo para remoção
- ✅ **ArrayList**: Cresce dinamicamente, métodos prontos (add, remove)
- ✅ **Padrão da indústria**: Coleções dinâmicas sempre usam List/ArrayList

#### Impacto
- Código de remoção: **8 linhas → 3 linhas**
- Complexidade: **O(n) manual → O(n) otimizado**
- Bugs potenciais: **Reduzidos drasticamente**

---

### REFACTOR #2: Validações com Exceções

**Arquivo:** `Produto.java`

#### Antes
```java
public void setPreco(double precoParametro) {
    if(precoParametro >= 0) {
        preco = precoParametro;
    }
    // Se negativo, simplesmente ignora (SILENCIOSO!)
}
```

#### Depois
```java
public void setPreco(double precoParametro) {
    if (precoParametro < 0) {
        throw new IllegalArgumentException(
            "Preço do produto não pode ser negativo: " + precoParametro
        );
    }
    this.preco = precoParametro;
}
```

#### Por quê?
- ❌ **Falha silenciosa**: Usuário não sabe que deu erro
- ❌ **Estado inválido**: Objeto pode ficar com preço = 0 sem querer
- ✅ **Fail Fast**: Falha imediatamente onde o erro aconteceu
- ✅ **Debug facilitado**: Mensagem clara do problema

#### Princípio: Fail Fast
> "É melhor falhar rápido e explicitamente do que continuar com estado inválido"

---

### REFACTOR #3: LocalDate para Datas

**Arquivo:** `Alimento.java`

#### Antes
```java
private String dataDeValidade;

public void setDataDeValidade(String dataDeValidade) {
    this.dataDeValidade = dataDeValidade;
}
```

**Problemas:**
- Aceita qualquer texto: `"banana"`, `"abc123"`
- Impossível comparar datas
- Impossível validar se está vencido

#### Depois
```java
private LocalDate dataDeValidade;
private static final DateTimeFormatter FORMATO_BR = 
    DateTimeFormatter.ofPattern("dd/MM/yyyy");

public void setDataDeValidade(String dataDeValidade) {
    try {
        this.dataDeValidade = LocalDate.parse(dataDeValidade, FORMATO_BR);
    } catch (DateTimeParseException e) {
        throw new IllegalArgumentException(
            "Data inválida. Use o formato dd/MM/yyyy"
        );
    }
}

// NOVO: Métodos só possíveis com LocalDate
public boolean estaVencido() {
    return LocalDate.now().isAfter(this.dataDeValidade);
}

public long diasAteVencer() {
    return ChronoUnit.DAYS.between(LocalDate.now(), this.dataDeValidade);
}
```

#### Benefícios
✅ Validação automática de formato  
✅ Comparações de data funcionam  
✅ Métodos úteis: `isBefore()`, `isAfter()`, etc  
✅ Padrão Java 8+ (moderno)  

---

### REFACTOR #4: Enum para Tamanho de Roupa

**Arquivo:** `Roupa.java` + novo `TamanhoRoupa.java`

#### Antes
```java
private int tamanho; // ??? Tamanho 42? 5? -10?
```

#### Depois
```java
public enum TamanhoRoupa {
    PP("Extra Pequeno"),
    P("Pequeno"),
    M("Médio"),
    G("Grande"),
    GG("Extra Grande"),
    XG("Extra Extra Grande");
    
    private final String descricao;
    // ...
}

private TamanhoRoupa tamanho;
```

#### Por quê?
- ❌ **int não faz sentido**: Tamanho 999? -5?
- ✅ **Type Safety**: Só aceita valores válidos
- ✅ **Autocomplete**: IDE sugere opções
- ✅ **Impossível errar**: Não compila se usar valor inválido

---

### REFACTOR #5: Separação de Responsabilidades

**Arquivo:** `Estoque.java`

#### Antes
```java
public void adicionarProduto(Produto p) {
    if(this.proximaPosLivre < this.produto.length) {
        // ...
    } else {
        System.out.println("Erro, Estoque Cheio"); // ❌ UI na lógica!
    }
}
```

#### Depois
```java
public void adicionarProduto(Produto p) {
    if (p == null) {
        throw new IllegalArgumentException("Produto não pode ser nulo");
    }
    
    if (buscarProdutoPorCodigo(p.getCodigo()) != null) {
        throw new IllegalArgumentException(
            "Já existe um produto com o código " + p.getCodigo()
        );
    }
    
    produtos.add(p);
}
```

#### Princípio: Single Responsibility Principle (SRP)
- **Estoque**: Cuida da lógica de negócio
- **App/UI**: Cuida de mostrar mensagens ao usuário
- **Cada classe tem uma responsabilidade**

---

### REFACTOR #6: Extração de Métodos

**Arquivo:** `App.java`

#### Antes
```java
public static void main(String[] args) {
    // 106 linhas de código monolítico
    // Tudo misturado: menu, lógica, validação
}
```

#### Depois
```java
public static void main(String[] args) {
    estoque = new Estoque();
    scanner = new Scanner(System.in);
    
    try {
        executarSistema();
    } finally {
        scanner.close(); // ✅ Sempre fecha recursos
    }
}

private static void executarSistema() { /* ... */ }
private static void exibirMenu() { /* ... */ }
private static void processarOpcao(int opcao) { /* ... */ }
private static void adicionarProduto() { /* ... */ }
private static void listarProdutos() { /* ... */ }
private static void buscarProduto() { /* ... */ }
private static void atualizarPreco() { /* ... */ }
private static void removerProduto() { /* ... */ }
```

#### Benefícios
✅ **Legibilidade**: Cada método tem um nome descritivo  
✅ **Manutenção**: Fácil encontrar e modificar funcionalidades  
✅ **Testabilidade**: Métodos pequenos são fáceis de testar  
✅ **Reusabilidade**: Métodos podem ser reutilizados  

#### Regra de Ouro
> "Um método deve fazer UMA coisa e fazer BEM"

---

### REFACTOR #7: Constantes ao invés de Magic Numbers

#### Antes
```java
if (escolhaInterface == 1) {
    // O que é 1? Adicionar? Remover?
}
```

#### Depois
```java
private static final int OPCAO_ADICIONAR = 1;
private static final int OPCAO_LISTAR = 2;
private static final int OPCAO_BUSCAR = 3;
// ...

if (opcao == OPCAO_ADICIONAR) {
    // Fica claro o que é!
}
```

#### Por quê?
- ✅ **Legibilidade**: Nome explica o significado
- ✅ **Manutenção**: Muda em um lugar só
- ✅ **Menos erros**: Difícil confundir valores

---

### REFACTOR #8: Enhanced For Loop

#### Antes
```java
for(int j = 0; j < this.proximaPosLivre; j++) {
    System.out.println(produto[j].toString());
}
```

#### Depois
```java
for (Produto produto : produtos) {
    System.out.println(produto);
}
```

#### Benefícios
- ✅ Mais legível
- ✅ Menos propenso a erros (sem índices)
- ✅ Padrão moderno do Java

---

## 📚 Princípios de Clean Code Aplicados

### 1. **DRY (Don't Repeat Yourself)**
- Código duplicado foi extraído para métodos
- Validações comuns centralizadas

### 2. **KISS (Keep It Simple, Stupid)**
- Código simples e direto
- Sem complexidade desnecessária

### 3. **Single Responsibility Principle (SRP)**
- Cada classe/método tem uma responsabilidade
- Estoque não faz System.out.println
- App não faz lógica de negócio

### 4. **Fail Fast**
- Validações lançam exceções imediatamente
- Não continua com estado inválido

### 5. **Type Safety**
- Tipos corretos (LocalDate, Enum)
- Compilador ajuda a evitar erros

### 6. **Defensive Programming**
- Validação de null
- Validação de código duplicado
- Mensagens de erro claras

---

## 🎨 Padrões de Projeto

### Factory Pattern ✅ (Já estava implementado)

**Onde:** `ProdutoFactory`

**O que faz:** Centraliza a criação de objetos complexos

**Por quê:** 
- Separa criação de uso
- Facilita adicionar novos tipos de produto
- Código mais organizado

---

## 🚀 Próximos Passos (Sugestões para evolução)

### 1. **Persistência de Dados**
```java
// Salvar estoque em arquivo
public void salvarEmArquivo(String nomeArquivo) throws IOException {
    // Usar JSON ou serialização
}
```

### 2. **Testes Unitários**
```java
@Test
public void testAdicionarProduto() {
    Estoque estoque = new Estoque();
    Produto produto = new Produto("Teste", 1, 10.0);
    estoque.adicionarProduto(produto);
    assertEquals(1, estoque.quantidadeProdutos());
}
```

### 3. **Strategy Pattern para Validações**
```java
public interface ValidadorProduto {
    void validar(Produto produto);
}

public class ValidadorPreco implements ValidadorProduto {
    public void validar(Produto produto) {
        if (produto.getPreco() < 0) {
            throw new IllegalArgumentException("Preço inválido");
        }
    }
}
```

### 4. **Observer Pattern para Notificações**
```java
// Notificar quando produto está prestes a vencer
public interface ObservadorEstoque {
    void onProdutoAdicionado(Produto produto);
    void onProdutoRemovido(Produto produto);
}
```

### 5. **Builder Pattern para Produtos Complexos**
```java
Produto produto = new ProdutoBuilder()
    .comNome("Notebook")
    .comCodigo(123)
    .comPreco(2500.00)
    .comGarantia(24)
    .build();
```

---

## 📊 Comparação: Antes vs Depois

| Aspecto | Antes | Depois |
|---------|-------|--------|
| **Linhas em App.java** | 106 (monolítico) | 236 (organizado) |
| **Métodos em App.java** | 1 | 8 |
| **Capacidade Estoque** | Fixo (10) | Ilimitado |
| **Validações** | Silenciosas | Exceções explícitas |
| **Tipo de Data** | String | LocalDate |
| **Tipo de Tamanho** | int | Enum |
| **Resource Leak** | Sim (Scanner) | Não (fechado) |
| **Código Duplicado** | Sim (if-else) | Não (métodos) |
| **Magic Numbers** | Sim | Não (constantes) |

---

## 💡 Lições Aprendidas

### 1. **Use as ferramentas certas**
- ArrayList para coleções dinâmicas
- LocalDate para datas
- Enum para conjuntos fixos de valores

### 2. **Valide explicitamente**
- Não falhe silenciosamente
- Lance exceções com mensagens claras
- Fail Fast é melhor que Fail Late

### 3. **Separe responsabilidades**
- UI não deve estar na lógica de negócio
- Cada classe/método faz uma coisa

### 4. **Métodos pequenos e focados**
- Mais fácil de entender
- Mais fácil de testar
- Mais fácil de manter

### 5. **Nomes importam**
- Constantes ao invés de números mágicos
- Nomes descritivos para métodos
- Código auto-documentado

---

## 🎓 Recursos para Estudo

### Livros
- **Clean Code** - Robert C. Martin
- **Effective Java** - Joshua Bloch
- **Design Patterns** - Gang of Four

### Conceitos para aprofundar
- SOLID Principles
- Design Patterns (Strategy, Observer, Builder)
- Test-Driven Development (TDD)
- Dependency Injection

---

## ✅ Checklist de Qualidade de Código

Use este checklist em seus próximos projetos:

- [ ] Sem magic numbers (use constantes)
- [ ] Métodos pequenos (< 20 linhas idealmente)
- [ ] Validações explícitas (exceções, não silêncio)
- [ ] Tipos corretos (LocalDate, Enum, etc)
- [ ] Recursos fechados (Scanner, Files, etc)
- [ ] Sem código duplicado (DRY)
- [ ] Nomes descritivos (variáveis, métodos, classes)
- [ ] Separação de responsabilidades (SRP)
- [ ] Comentários explicam "por quê", não "o quê"
- [ ] Testes unitários (quando possível)

---

## 🤝 Contribuindo

Este é um projeto educacional. Sinta-se livre para:
- Fazer perguntas sobre as mudanças
- Sugerir melhorias adicionais
- Aplicar estes conceitos em seus projetos

**Lembre-se:** Código bom é código que outros conseguem entender e manter!

---

**Autor da Refatoração:** Cascade AI  
**Data:** Novembro 2024  
**Objetivo:** Educacional - Demonstrar boas práticas de programação Java
