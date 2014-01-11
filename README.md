# RESTful API

The restful API will accept a simulation request with the following format:

    {
      "board": [ [2, 3], [3, 6], ...],
      "steps": 10
    }

    And will respond with the following:

    {
      "statuses": [ [ @board1, @board2, ... ]  ]
    }

# Rules of the game of life

Any live cell with fewer than two live neighbours dies, as if caused by under-population.
Any live cell with two or three live neighbours lives on to the next generation.
Any live cell with more than three live neighbours dies, as if by overcrowding.
Any dead cell with exactly three live neighbours becomes a live cell, as if by reproduction.
