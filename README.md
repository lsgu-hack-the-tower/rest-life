# RESTful API

The restful API will accept a simulation request with the following format:
    {
        "board": {
            "cells": [
                { "x": 2, "y": 2 },
                { "x": 3, "y": 2 },
                { "x": 2, "y": 3 }
            ]
        },
        "steps": 3
    }

And will respond with the following:

    [{
        "cells": [
            {"x": 2, "y": 2},
            {"x": 3, "y": 2},
            {"x": 2, "y": 3},
            {"x": 3, "y": 3}
        ]
    },
    {
        "cells": [
            {"x": 2, "y": 2},
            {"x": 3, "y": 2},
            {"x": 2, "y": 3}
        ]
    }]

# Running the application

In the root of the project, run 'sbt run' command to start the server. You can use curl to test the API:
```
curl -X POST -H "Content-Type: application/json" -d '{"board": {"cells": [ { "x": 2, "y": 2 }, { "x": 3, "y": 2 }, { "x": 2, "y": 3 } ]},"steps": 10}' http://localhost:9909/
```


# Rules of the game of life

Any live cell with fewer than two live neighbours dies, as if caused by under-population.
Any live cell with two or three live neighbours lives on to the next generation.
Any live cell with more than three live neighbours dies, as if by overcrowding.
Any dead cell with exactly three live neighbours becomes a live cell, as if by reproduction.
