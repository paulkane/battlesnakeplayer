# battlesnakeplayer

This will play many battle snake games on against your server. This helpful to get play a large number of games between 
many different snakes.

# Board and snakes 

Define your board in `board.json`
Define all the snakes you want to play in `snakes.json`

# Application properties

| Name | default | notes |
| ---- | ----- | --- |
| battlesnake.engine.url | http://localhost:3005 | the url of engine |
| battlesnake.board.url | http://localhost:3009 | the url of the board |
| battlesnake.output.played.games.file | played-games.txt | where to write the url to view a board |
| battlesnake.max.snakes | 4 | the max number of snakes per board |
| battlesnake.number.battles | 30 | the number of times to play the same board |
| battlesnake.number.combinations | 1 | the number of different combination of boards to play | 
| battlesnake.print.results.every | 10 | output to screen the results after this many games complete |

# Screen output

```
..........
Results
     random [ 11/ 70]
hungry-food [ 27/ 70]
 eager-food [ 31/ 70]
 ```

The dots represent finished games. 

# board file content

```
http://localhost:3009/?engine=http://localhost:3005&game=d505eb7f-31d7-497f-b121-93cc31b17456
http://localhost:3009/?engine=http://localhost:3005&game=f7d68884-7af8-439b-aa91-980d0d227d45
```
You can copy and paste the links into a browser to watch the board.