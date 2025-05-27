# 📄 Documentação do Aplicativo

## Projeto: Cadastro de Participantes de Clube de Leitura

### 1. Introdução
Este projeto tem como objetivo o desenvolvimento de um sistema simples para cadastro e gerenciamento dos participantes de um clube de leitura. O foco principal está em organizar as informações pessoais dos membros e seus interesses literários, permitindo um controle eficaz da frequência e preferências.

### 2. Funcionalidades principais
- Cadastro de novos participantes com dados pessoais (nome, email, telefone)
- Registro do gênero literário preferido pelo participante
- Controle da frequência de participação nas reuniões (Ex: Semanal, Quinzenal, Mensal)
- Registro da data de inscrição do participante
- Busca por nome ou email para facilitar a localização dos participantes
- Edição e exclusão dos dados dos participantes
- (Opcional) Filtragem da lista por frequência ou gênero preferido
- (Opcional) Geração de estatísticas simples, como contagem de participantes por gênero literário

### 3. Estrutura dos dados
Cada participante é representado por um registro contendo:
- ID (número único gerado automaticamente pelo banco de dados)
- Nome (texto, obrigatório)
- Email (texto, obrigatório, idealmente único)
- Telefone (texto, opcional)
- Gênero Literário Preferido (texto, opcional)
- Frequência de Participação (texto, opcional)
- Data de Inscrição (data, obrigatória)

### 4. Tecnologias utilizadas
- **Flutter**: para a interface gráfica e desenvolvimento multiplataforma (foco no Android).
- **Drift (com SQLite)**: para armazenamento local dos dados no dispositivo, garantindo persistência e consultas eficientes de forma reativa.
- **Dart**: linguagem principal para a lógica do aplicativo, interface e manipulação dos dados.
- **Provider**: para gerenciamento de estado e disponibilização da instância do banco de dados.

### 5. Layout e interação
- A tela inicial exibe a lista de participantes, mostrando nome, email e outras informações relevantes (como data de inscrição, gênero preferido, frequência).
- O usuário pode (ou poderá, se implementado) buscar participantes pelo nome ou email utilizando um campo de busca.
- O formulário de cadastro/edição possui campos de texto para as informações pessoais (nome, email, telefone, gênero literário), um seletor (dropdown) para frequência de participação e um seletor de data para a data de inscrição.
- Botões para salvar alterações, excluir participantes (com confirmação) e adicionar novos membros são facilmente acessíveis.
- (Opcional, se implementado) Filtros podem permitir visualizar grupos específicos por frequência ou gênero literário.

### 6. Possíveis melhorias futuras
- Implementação de um sistema de sessões ou encontros para registro de presença em eventos específicos.
- Inclusão de comentários ou avaliações dos livros discutidos pelos participantes.
- Integração com serviços externos para envio de notificações sobre reuniões (ex: usando Firebase Cloud Messaging).
- Visualização gráfica de estatísticas, como temas literários mais populares no clube.
- Melhorias na busca e filtros avançados.
- Backup e restauração dos dados.
