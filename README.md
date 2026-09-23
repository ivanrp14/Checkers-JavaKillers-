# Checkers JavaKillers

An automatic **checkers** player for the PROP course (EPSEVG, UPC). The JavaKillers bot picks a move with **Minimax**.

## What it does

- Plays inside the `edu.upc.epsevg.prop.checkers` framework (`IPlayer`, `IAuto`).
- `PlayerMiniMax` searches the move tree up to a given depth and keeps the best move for the maximizing player.
- `Movement`, `Node`, and **Zobrist** hashing avoid re-evaluating positions.
- A human player and a random player are included so you can test games.
- `Game` has a UI; `HeadlessGame` runs without a window.

## Stack

- Java
- Swing for the board and controls (`JControlsPanel`)
- NetBeans project (`build.xml`, `nbproject`)

## Layout

```
src/edu/upc/epsevg/prop/checkers/
├── Game.java                      # game with UI
├── HeadlessGame.java              # game without UI
├── Board.java
├── players/HumanPlayer.java
├── players/RandomPlayer.java
└── players/javakillers/
    ├── PlayerMiniMax.java         # Minimax bot
    ├── PlayerID.java
    └── utils/                     # Movement, Node, Zobrist
```

`lib/javadoc` is generated documentation for the checkers package, not the bot source.

## How to run

Open the project in NetBeans and run `edu.upc.epsevg.prop.checkers.Game`.

Pick `PlayerMiniMax` as one of the two sides. Depth and the explored-node count live in that class.
