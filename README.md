# 🌌 Astro Jumper

Astro Jumper é um jogo de plataforma 2D em Java com tema espacial, inspirado em Super Mario Bros. Você controla um astronauta explorando um mundo industrial no espaço, saltando por plataformas, evitando perigos e sobrevivendo a meteoros caindo do céu.


---

## 🎮 Sobre o jogo

- **Gênero**: Plataforma 2D (Side-scroller)
- **Tema**: Espaço sideral industrial
- **Estilo**: Semi-realista, com arte vetorial (64x64px)
- **Fases**: Múltiplas fases com tilesets espaciais e obstáculos variados

---

## 🧑‍🚀 Personagem

- Astronauta azul animado com spritesheets para:
    - Idle (parado)
    - Correndo
    - Pulando
    - Caindo
    - Morre

---

## 🌌 Recursos

- **Engine**: [libGDX](https://libgdx.com/)
- **Arte**:
    - Tilesets: [Kenney Platformer Pack Industrial](https://kenney.nl/assets/platformer-pack-industrial)
    - Sprites personalizados (estrela verde, personagem, explosões)
- **Animações**:
    - Personagem animado com `TextureRegion` e `Animation`
    - Explosão animada ao colidir com meteoros
- **Tiled Maps**:
    - Fases criadas com o [Tiled](https://www.mapeditor.org/)
    - Tilesets integrados com suporte a camadas de imagem (backgrounds, objetos, chão, etc.)
- **Sons**:
    - Efeitos sonoros como explosões usando a classe `Sound` do libGDX

---

## 💥 Mecânicas especiais

- Meteoros caem do céu em tempo real e explodem ao colidir com o chão
- Colisão com meteoros causa game over
- Fim da fase ocorre ao tocar uma nave espacial (objeto fora do tileset)

---

## 🗺️ TiledMap

- Cada fase é criada em Tiled com suporte a:
    - `Image Layers` para fundos
    - `Tile Layers` para plataformas, obstáculos e decorações
    - `Object Layers` para colisões e triggers (como final de fase)
- Tamanho baseado em proporções do Super Mario Bros clássico

---

## 🛠️ Como rodar

1. **Clone o repositório**:
   ```bash
   git clone https://github.com/albierygs/astro-jumper.git

2. **Configurar o ambiente**:
    - Certifique-se de ter o [Java Development Kit (JDK)](https://www.oracle.com/java/technologies/javase-downloads.html) 8 ou superior instalado.
    - Instale uma IDE compatível, como [IntelliJ IDEA](https://www.jetbrains.com/idea/) ou [Eclipse](https://www.eclipse.org/).
    - Certifique-se de que o [Gradle](https://gradle.org/) está configurado (o projeto já inclui o `gradlew` para facilitar).

3. **Importar o projeto**:
    - Abra sua IDE e importe o projeto como um projeto Gradle.
    - O Gradle baixará automaticamente as dependências do libGDX especificadas no arquivo `build.gradle`.

4. **Rodar o jogo**:
    - Execute o comando abaixo no terminal, na raiz do projeto, para rodar a versão lwjgl3:
      ```bash
      ./gradlew lwjgl3:run
      ```
    - Alternativamente, na IDE, encontre a classe `Lwjgl3Launcher` no módulo `lwjgl3` e execute-a como uma aplicação Java.

5. **Testar as fases**:
    - As fases estão localizadas em `core/assets/maps/`. Certifique-se de que os arquivos `.tmx` (criados no Tiled) estão corretamente referenciados no código.

---

## 📂 Estrutura do projeto

A estrutura do projeto segue a organização padrão do libGDX, com módulos para o core e lwjgl3, além de assets centralizados. Abaixo está a estrutura principal:

```
astro-jumper/
├── assets/                   # Arquivos de assets (sprites, sons, mapas)
│   ├── maps/                 # Arquivos .tmx do Tiled
│   ├── textures/              # Spritesheets e imagens (personagem, tilesets, etc.)
│   └── sounds/               # Efeitos sonoros (explosões, etc.)
├── core/                         # Código principal do jogo (compartilhado entre plataformas)
│   ├── src/                      # Código-fonte Java
│   │   └── br/uneb/astrojumper/      # Pacotes do jogo
│   │       ├── sprites/         # Classes para entidades (jogador, meteoros, etc.)
│   │       ├── screens/          # Telas do jogo (menu, fases, game over)
│   │       └── utils/            # Utilitários (gerenciamento de assets, física, etc.)
├── lwjgl3/                       # Módulo para a versão lwjgl3
│   ├── src/                      # Código específico para lwjgl3
│   └── build.gradle              # Configurações Gradle para Android
├── build.gradle                  # Configuração principal do Gradle
├── gradlew                       # Script Gradle para Unix
├── gradlew.bat                   # Script Gradle para Windows
└── README.md                     # Este arquivo
```


---

## 📜 Licença

Este projeto é licenciado sob a **MIT License**. Você é livre para usar, copiar, modificar, mesclar, publicar, distribuir, sublicenciar e/ou vender cópias do software, sujeito às condições abaixo:

```
MIT License

Copyright (c) 2025 [Seu Nome] e [Nome do Colaborador]

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```

---

## 🙌 Créditos

- **Desenvolvimento**:
    - [Albiery Gonçalves](https://github.com/albierygs) - Desenvolvedor principal.
    - [Angela Michelle](https://github.com/Angela-Vidal) - Desenvolvedora principal.

- **Assets**:
    - [Kenney](https://kenney.nl/) - Tilesets (Platformer Pack Industrial) e outros recursos gráficos.

- **Ferramentas**:
    - [libGDX](https://libgdx.com/) - Framework de desenvolvimento do jogo.
    - [Tiled](https://www.mapeditor.org/) - Editor de mapas para criação de fases.

- **Inspiração**:
    - Super Mario Bros, pela mecânica clássica de plataforma.

Agradecemos a todos que contribuíram com feedback e suporte durante o desenvolvimento!
