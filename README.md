# jchess
A small chess game written in java, designed to be played in CLI environment.

The game is currently only playable locally between players.

Notes:
In order to use the save option, you need to create a savefiles folder in the root of the project.
You can use the pom file to create a runnable jar file with maven to play the game in a terminal such as bash.

Planning to add:
A working AI used in the pve mode of the game
Work out the remaining inputs that can crash the game
Create networking possibility in pvp mode instead of only being able to play locally
Make the colors print properly in bash and cmd
Add menu options:
        - To change the color of both players
        - To let the black side start instead of white side
Implement the castling mechanism

Dependencies:
JCDP by dialex

Thanks to @dialex for sharing his work in JCDP to make colored printing in terminals a whole lot easier.
