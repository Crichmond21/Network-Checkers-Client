# Network-Checkers-Client

This game was created for Network Programming (COMP 2100) at Wentworth Institute of Technology.

A compiled version of the server for this client is included but if you would like the actual code or the latest server version you can find that [here](https://github.com/Crichmond21/Network-Checkers-Server).

## Background
Network Checkers is a Multiplayer Java game that utilizes multithreading and socket programming. This project has produced many unexpected issues as usual when you learn certain coding stategies for the first time. The project was not fully completed before the due date but I plan to continue to work on it into the future.

## Future Plans

### GUI
- [X] Update Board to include turn indicator
- [X] Update Board to face the correct direction depending on if you are Player 1 or Player 2

### Client
- [X] Fix syncronization bugs
- [X] Overhaul Information Transport protocol to make it more reliable
- [X] ~~Reformat data transport to all be on same thread~~
	UPDATE *** Data transport still occurs on two seperate threads but one is recieving data while the other is sending it

### Game
- [ ] Fix jumping recognized as invalid moves
- [ ] Remove jumped pieces
- [ ] Test and fixed king pieces
