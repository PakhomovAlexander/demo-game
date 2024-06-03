# ROCKS PAPER SCISSORS

This is the game of Rock, Paper, Scissors. The game is played by two players. Each player simultaneously forms one of three shapes with an outstretched hand. The possible shapes are:

## How to play

Make sure you have Java 21 installed on your machine `java -version`.

To play the game you need to start a server first:

```bash
git clone git@github.com:PakhomovAlexander/demo-game.git
./gradlew run
```

Then you can play the game by running the following command:
```bash
telnet localhost 8080
```

Note, that the game is multiplayer, so you need to run the second player in another terminal or ask 
your friend to play with you :)
