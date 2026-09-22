# Checkers JavaKillers

Jugador automático de **damas** para la práctica de PROP (EPSEVG, UPC). El bot del equipo JavaKillers elige jugada con **Minimax**.

## Qué hace

- Juega dentro del marco `edu.upc.epsevg.prop.checkers` (`IPlayer`, `IAuto`).
- `PlayerMiniMax` explora el árbol de jugadas hasta una profundidad dada y se queda con la mejor para el jugador que maximiza.
- Apoyo de `Movement`, `Node` y hashing **Zobrist** para no reevaluar posiciones.
- Incluye también un jugador humano y uno aleatorio para probar partidas.
- Hay una `Game` con interfaz y una `HeadlessGame` sin ventana.

## Stack

- Java
- Swing para el tablero con controles (`JControlsPanel`)
- Proyecto NetBeans (`build.xml`, `nbproject`)

## Estructura

```
src/edu/upc/epsevg/prop/checkers/
├── Game.java                      # partida con interfaz
├── HeadlessGame.java              # partida sin interfaz
├── Board.java
├── players/HumanPlayer.java
├── players/RandomPlayer.java
└── players/javakillers/
    ├── PlayerMiniMax.java         # bot Minimax
    ├── PlayerID.java
    └── utils/                     # Movement, Node, Zobrist
```

`lib/javadoc` es la documentación generada del paquete de damas, no el código del bot.

## Cómo ejecutarlo

Abre el proyecto en NetBeans y ejecuta `edu.upc.epsevg.prop.checkers.Game`.

Elige el jugador `PlayerMiniMax` como uno de los dos bandos. La profundidad y el conteo de nodos explorados están en esa clase.
