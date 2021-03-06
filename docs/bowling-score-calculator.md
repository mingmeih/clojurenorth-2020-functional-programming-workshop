# Bowling Score Calculator

American Ten-Pin Bowling has a non-trivial way of calculating the score.

The rules are as follows:
  - each game of bowling includes 10 turns (aka "frames")
  - in each frame, a bowler gets 2 attempts to knock down all 10 pins
  - if in 2 attempts, they fail, the score for that frame is the total number of pins knocked down
  - if in 2 attempts, they succeed (a "spare"), the score for that frame is 10 plus the number of pins knocked down in the next roll
  - if on the first try, they knock all the pins down (a "strike"), the score for that frame is 10 plus the number of pins knocked down in the next 2 rolls
  - if a bowler gets a "spare" in the last frame (the 10th frame), they get 1 bonus roll, with the number of pins knocked down added to the score of the last frame
  - if a bowler gets a "strike" in the last frame (the 10th frame), they get 2 bonus rolls, with the number of pins knocked down added to the score of the last frame

Another explanation is available [on Wikipedia](https://en.wikipedia.org/wiki/Ten-pin_bowling#Scoring).

## Tasks

Your task is to create a function to calculate the total score in a game of American Ten-Pin Bowling.

In ../src/katas/bowling/core.clj, we have started you off with a function:

```clojure
(defn total-score [rolls])
```

The input, `rolls`, will be a vector of integers, corresponding to the pins knocked down in each roll of a game. For example: `[10 10 10 10 10 10 10 10 10 10 10 10]` for a perfect game.  You can assume that the input data is always valid.

In ./test/katas/test/bowling.clj we've provided you a reasonable comprehensive test set.

Try to write code as you would expect from your coworkers on a non-trivial production project.

<sup>This kata is based on [UncleBob's Bowling Game Kata](http://www.butunclebob.com/ArticleS.UncleBob.TheBowlingGameKata)</sup>
