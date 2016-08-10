# rbhanot-ticTacToe
A tic-tac-toe game that can be played via slash commands on Slack.

Commands :-
/ttt start [user] : start a new TicTacToe game with user on a 3x3 board
/ttt play [position] : choose an empty position for your next play based on this given board
            | 1 | 2 | 3 |
            | 4 | 5 | 6 |
            | 7 | 8 | 9 |
/ttt end : end the game at the current state leading to your opponent's victory.
/ttt status : display current board status with a reminder of whose turn it is.
/ttt help : manual for the ttt slash command.

Built using gradle so requires "gradle stage" to do clean, build and jar which is also
used by heroku for deployment.

Can be deployed to heroku. Just need to set config var SLACK_TOKEN
config:set SLACK_TOKEN=<your slack token>

Use repository "simple-slack-api" to connect a session on a channel. SLACK_TOKEN is required for the same.

Uses sparkjava to create two routes-
/health - Used to check health of the server
/game - Used to do everything in the game

PORT is assigned dynamically by heroku so need to read it at runtime to fully ascertain the endpoint and route.