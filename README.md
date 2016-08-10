# rbhanot-ticTacToe
A tic-tac-toe game that can be played via slash commands on Slack.

Commands :-<br />
/ttt start [user] : start a new TicTacToe game with user on a 3x3 board<br />
/ttt play [position] : choose an empty position for your next play based on this given board<br />
           | 1 | 2 | 3 |<br />
           | 4 | 5 | 6 |<br />
           | 7 | 8 | 9 |<br />
/ttt end : end the game at the current state leading to your opponent's victory.<br />
/ttt status : display current board status with a reminder of whose turn it is.<br />
/ttt help : manual for the ttt slash command.<br />
<br />
Built using gradle so requires "gradle stage" to do clean, build and jar which is also<br />
used by heroku for deployment.<br />

Can be deployed to heroku. Just need to set config var SLACK_TOKEN<br />
config:set SLACK_TOKEN=<your slack token><br />

Use repository "simple-slack-api" to connect a session on a channel. SLACK_TOKEN is required for the same.<br />

Uses sparkjava to create two routes-<br />
/health - Used to check health of the server<br />
/game - Used to do everything in the game<br />

PORT is assigned dynamically by heroku so need to read it at runtime to fully ascertain the endpoint and route.<br />
