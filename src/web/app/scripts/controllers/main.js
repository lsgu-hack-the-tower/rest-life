'use strict';

angular.module('webApp')
  .controller('MainCtrl', function ($interval, $http, $scope) {
    var boardConfig = {
      v: 30,
      h: 45,
      cellSize: 18,
      cellMargin: 1
    };

    var dist = function(index) {
      return boardConfig.cellMargin + index * (boardConfig.cellSize + 2 * boardConfig.cellMargin);
    };

    var cellFactory = function(x, y) {
      return {
        x: x,
        y: y,
        posX: dist(x),
        posY: dist(y),
        l: boardConfig.cellSize
      };
    };

    var cells = function() {
      cells = [];
      for (var x = 0; x < boardConfig.h; x++) {
        for (var y = 0; y < boardConfig.v; y++) {
          cells.push(cellFactory(x, y));
        }
      }

      return cells;
    };

    var board = {
      boardConfig: boardConfig,
      cells: cells(),
      selectedCells: [],
      class: function(cell) {
        return _.find(board.selectedCells, function(c) { return c.x == cell.x && c.y == cell.y; }) ? "cell-live" : "cell";
      }
    };

    $scope.toggle = function(c) {
      var idx = board.selectedCells.indexOf(c);
      if (idx == -1) {
        board.selectedCells.push(c);
      } else {
        board.selectedCells.splice(idx, 1);
      }
    }

    var symStop = null;

    $scope.startSym = function() {
      console.log(board.selectedCells);
      if (symStop === null) {
        var toSymData = function(board, steps) {
          return _.map(board, function(c) { return [c.x, c.y]; });
        }

        var symCells = function(step) {
          return _.map(step, function(s) { return cellFactory(s[0], s[1]); });
        }

        var steps = [];
        var requestSteps = function() {
          var symData = steps.length > 0 ? _.last(steps) : toSymData(board.selectedCells)

          $http({method: 'POST', data: { board: symData, steps: 40 }, url: '/board'})
            .success(function(data) {
              steps = steps.concat(data);
            });
        }

        requestSteps();
        $interval(function() {
          requestSteps();
        }, 2500);

        $interval(function() {
          if (steps.length > 0) {
            var nextStep = _.last(steps);
            var nextCells = symCells(nextStep);
            steps = _.without(steps, nextStep);

            console.log(nextCells);
            board.selectedCells = nextCells;
          }
        }, 250);
      }
    }

    $scope.board = board;
  });
