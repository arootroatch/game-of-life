(ns game-of-life.gui-spec
  (:require [game-of-life.gui :as gui]
            [quil.core :as q]
            [speclj.core :refer :all]))

(describe "Game of Life GUI"
  (with-stubs)
  (redefs-around [q/fill (stub :fill)
                  q/rect (stub :rect)
                  q/background (stub :background)])

  (context "create-square"
    (it "places [0 0] in center of window"
      (gui/create-square [0 0] #{[0 0]})
      (should-have-invoked :rect {:with [400 400 50 50]}))

    (it "places [-1 0] 50 pixels to the left"
      (gui/create-square [-1 0] #{[0 0]})
      (should-have-invoked :rect {:with [350 400 50 50]}))

    (it "colors a live square"
      (gui/create-square [0 0] #{[0 0]})
      (should-have-invoked :fill {:with [0 183 150]}))

    (it "makes dead cells black"
      (gui/create-square [0 1] #{[0 0]})
      (should-have-invoked :fill {:with [0 0 0]}))
    )

  (context "->full-grid"
    (it "creates full grid with live and dead cells"
      (should= #{[1 0] [-1 0] [1 1] [-1 -1] [1 -1] [-1 1] [0 -1] [0 1]} (gui/->full-grid #{[0 0]}))
      (should= #{[0 0] [1 0] [-1 0] [1 1] [-1 2] [-1 -1] [1 -1] [0 2] [-1 1] [0 -1] [1 2] [0 1]}
               (gui/->full-grid #{[0 0] [0 1]})))))

