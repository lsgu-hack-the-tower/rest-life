# RESTful API

The restful API will accept a simulation request with the following format:

    {
      "board": [ [2, 3], [3, 6], ...],
      "steps": 10
    }

And will respond with the following:

    [[ [x, y], [w, z], ...], ...]

# Running the application

In the root of the project, run 'sbt run' command to start the server. You can use curl to test the API:
```
curl -X POST -H "Content-Type: application/json" -d '{"board": [[0,0],[0,1],[0,2]],"steps": 10}' http://localhost:9909/
```


# Rules of the game of life

Any live c with fewer than two live neighbours dies, as if caused by under-population.

Any live c with two or three live neighbours lives on to the next generation.

Any live c with more than three live neighbours dies, as if by overcrowding.

Any dead c with exactly three live neighbours becomes a live c, as if by reproduction.
