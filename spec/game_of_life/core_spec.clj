(ns game-of-life.core-spec
  (:require [game-of-life.core :refer :all]
            [speclj.core :refer :all]))

(def diagonal-world #{[-1 -1] [0 0] [1 1]})
(def horizontal-world #{[-1 0] [0 0] [1 0]})
(def vertical-world #{[0 -1] [0 0] [0 1]})
(def block-world #{[-1 -1] [0 -1] [-1 0] [0 0]})
(def L-world #{[-1 -1] [-1 0] [0 0]})
(def P-world #{[-1 -1] [0 -1] [-1 0] [0 0] [-1 1]})


(describe "Game of Life"
  (context "evolve"
    (it "stays dead when there's no life"
      (should= #{} (evolve #{})))

    (it "dies with only one life"
      (should= #{} (evolve #{[0 0]})))

    (it "dies with only two lives"
      (should= #{} (evolve #{[-1 0] [0 0]})))

    (it "lives with two neighbors"
      (should= #{[0 0]} (evolve diagonal-world)))

    (it "stands up when horizontal"
      (should= vertical-world (evolve horizontal-world)))

    (it "turns Ls into blocks"
      (should= block-world (evolve L-world)))
    )

  (context "evolve-cell"
    (it "dies if it's alone"
      (should= nil (evolve-cell #{[0 0]} [0 0])))

    (it "dies if it only has one neighbor"
      (should= nil (evolve-cell #{[0 0] [0 1]} [0 0]))
      (should= nil (evolve-cell horizontal-world [-1 0])))

    (it "stays alive with two neighbors"
      (should= [0 0] (evolve-cell diagonal-world [0 0])))

    (it "stays alive with three neighbors"
      (should= [0 0] (evolve-cell block-world [0 0])))

    (it "dies with more than three active neighbors"
      (should= nil (evolve-cell P-world [0 0])))

    (it "is revived with three neighbors"
      (should= [0 -1] (evolve-cell horizontal-world [0 -1])))
    )

  (context "get-neighbors-of"
    (it "gets neighbors of a cell"
      (should= #{[-1 -1] [0 -1] [1 -1]
                 [-1 0] [1 0]
                 [-1 1] [0 1] [1 1]} (get-neighbors-of [0 0]))
      (should= #{[0 0] [1 0] [2 0]
                 [0 1] [2 1]
                 [0 2] [1 2] [2 2]}
               (get-neighbors-of [1 1])))
    ))
