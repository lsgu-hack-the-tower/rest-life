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
