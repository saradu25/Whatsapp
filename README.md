## Aluna: Sara Valentina Durán Campos |  Prontuario: AQ3006883 ##

# Clone de WhatsApp - Aplicativo Android em Kotlin
Projeto final da disciplina de DMO, este é um clone simples do WhatsApp, desenvolvido utilizando Kotlin e Firebase para Android. O projeto permite que os usuários se registrem, façam login, enviem mensagens e atualizem seus perfis.

## Funcionalidades Principais

- **Registro e Login de Usuários:** Permite que os usuários criem contas e façam login.
- **Envio de Mensagens:** Os usuários podem enviar mensagens em tempo real com Firebase Firestore.
- **Edição de Perfil:** Atualize a imagem de perfil e o nome do usuário.
- **Logout Seguro:** O usuário pode fazer logout da aplicação.

## Tecnologias Utilizadas

- **Kotlin**: Linguagem de programação para Android.
- **Firebase Authentication**: Para autenticação de usuários.
- **Firebase Firestore**: Para armazenamento de mensagens em tempo real.
- **Firebase Storage**: Para armazenamento de imagens de perfil.
- **Picasso**: Biblioteca para carregamento de imagens.
- **Android Jetpack Components**: Incluindo Fragments, RecyclerView e ViewModel.

## Fluxo da Aplicação

### 1. Tela de Login

Na tela de login, o usuário pode inserir seu e-mail e senha para acessar a aplicação. Se as credenciais forem válidas, o aplicativo procederá à tela de conversas e contatos.

![Tela de Login](./assets/1- TelaLogin.png)

### 2. Tela de Cadastro

Se o usuário não possui uma conta, ele pode se registrar através da tela de cadastro. Deve usar:
- Senha segura
- E-mail disponivel (não cadastrado previamente)
- Nome preenchido

  Assim que o usuario se cadastra, o app vai para a tela de conversas e contatos.

![Tela de Cadastro](./assets/2 - TelaCadastro.png)

### 3. Tela de Conversas

Após o login, o usuário é levado para a tela de conversas, onde pode ver as últimas mensagens trocadas.

![Tela de Conversas](./assets/3 - TelaConversas.png)

### 4. Tela de Contatos

O usuário pode acessar sua lista de contatos para iniciar novas conversas.

![Tela de Contatos](./assets/4 - TelaContatos.png)

### 5. Menu de Opções

No menu de opções, o usuário pode acessar o seu perfil ou sair da conta.

![Menu de Opções](./assets/5 - MenuOptions.png)

### 6. Editar Perfil

Na tela de perfil, o usuário pode alterar seu nome e sua foto de perfil.

![Editar Perfil](./assets/6 - VisualizarEditarPerfil.png)

### 7. Escolher Nova Foto

O usuário pode selecionar uma nova foto para seu perfil diretamente do dispositivo.

![Escolher Nova Foto](./assets/7 - EscolherFotoNova.png)

### 8. Conversa em Funcionamento

As conversas entre os usuários funcionam em tempo real, com mensagens sendo exibidas imediatamente.

![Conversa Funcionando](./assets/8 - ConversaFuncionando.png)

### 9. Tela de Logout

O usuário pode fazer logout da conta com segurança.

![Logout](./assets/9 - Logout.png)

## Instalação e Configuração

### Requisitos

- **Android Studio** (versão mais recente)
- Conta no [Firebase](https://firebase.google.com/) para configurar Authentication, Firestore e Storage.
  
### Passos para Instalar

1. **Clonar o Repositório**:
   ```bash
   git clone https://github.com/seu-usuario/whatsapp-clone-kotlin.git
   cd whatsapp-clone-kotlin

2. **Configurar Firebase:**

  - Vá até o Firebase Console, crie um novo projeto e ative Firebase Authentication, Firestore e Storage.
  - Baixe o arquivo google-services.json e coloque-o dentro da pasta app/ do projeto.
  
3. **Instalar as Dependências:**
  - No Android Studio, sincronize o Gradle para instalar todas as dependências necessárias, como Firebase e Picasso.
  - 
4. **Executar o Projeto:**
  - Agora, basta compilar e rodar o aplicativo no emulador ou em um dispositivo físico.

## Permissões Necessárias

Este aplicativo solicita as seguintes permissões para funcionar corretamente:

- **Acesso à Internet** (`android.permission.INTERNET`): Necessário para conectar-se ao Firebase e enviar/receber mensagens em tempo real.
- **Acesso ao Armazenamento Externo** (`android.permission.READ_EXTERNAL_STORAGE` e `android.permission.WRITE_EXTERNAL_STORAGE`): Utilizado para permitir que o usuário envie e visualize fotos em seu perfil.
- **Acesso à Câmera** (`android.permission.CAMERA`): Permite que o usuário tire fotos para atualizar sua imagem de perfil diretamente pela câmera.
- **Gerenciar Imagens e Mídia** (`android.permission.READ_MEDIA_IMAGES` para Android 13+): Permite que o app acesse a galeria de imagens para selecionar uma nova foto de perfil.

### Como as Permissões são Solicitadas

Quando o aplicativo precisar de uma dessas permissões, será exibido um diálogo solicitando ao usuário que aceite ou negue a permissão. Se o usuário não conceder, algumas funcionalidades podem ser limitadas, como enviar uma foto de perfil diretamente da câmera ou galeria.


