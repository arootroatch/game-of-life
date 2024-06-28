(ns game-of-life.gui
  (:require [quil.core :as q]
            [quil.middleware :as m]
            [clojure.set :as set]
            [game-of-life.core :as life]))

(defn setup []
  (q/frame-rate 2)
  (q/color-mode :rgb)
  {:live-cells #{[-1 -2][-1 -3][-1 -4][-2 -1][-3 -1][-4 -1][-6 -2][-6 -3][-6 -4][-2 -6][-3 -6][-4 -6]
                 [1 -2][1 -3][1 -4][2 -1][3 -1][4 -1][6 -2][6 -3][6 -4][2 -6][3 -6][4 -6]
                 [-1 2][-1 3][-1 4][-2 1][-3 1][-4 1][-6 2][-6 3][-6 4][-2 6][-3 6][-4 6]
                 [1 2][1 3][1 4][2 1][3 1][4 1][6 2][6 3][6 4][2 6][3 6][4 6]}
   :x-range nil
   :y-range nil})

(defn get-x-min [state]
  (let [world (:live-cells state)
        new-min (apply min (map #(first %) world))
        x-min (if (nil? (:x-range state)) new-min (first (:x-range state)))]
    (if (< new-min x-min) new-min x-min)))

(defn get-y-min [state]
  (let [world (:live-cells state)
        new-min (apply min (map #(second %) world))
        y-min (if (nil? (:y-range state)) new-min (first (:y-range state)))]
    (if (< new-min y-min) new-min y-min)))

(defn get-x-max [state]
  (let [world (:live-cells state)
        new-max (apply max (map #(first %) world))
        x-max (if (nil? (:x-range state)) new-max (second (:x-range state)))]
    (if (> new-max x-max) new-max x-max)))

(defn get-y-max [state]
  (let [world (:live-cells state)
        new-max (apply max (map #(second %) world))
        y-max (if (nil? (:y-range state)) new-max (second (:y-range state)))]
    (if (> new-max y-max) new-max y-max)))

(defn get-max-cell-count [state]
  (let [x-size (- (get-x-max state) (get-x-min state))
        y-size (- (get-y-max state) (get-y-min state))]
    (max x-size y-size)))

(defn update-state [state]
  {:live-cells (life/evolve (:live-cells state))
   :x-range [(get-x-min state) (get-x-max state)]
   :y-range [(get-y-min state) (get-y-max state)]
   :cell-size (/ 600 (get-max-cell-count state))}
  )

(defn draw-state [state]
  (q/background 240)

  (let [min-cell (min (get-x-min state) (get-y-min state))
        max-cell (max (get-x-max state) (get-y-max state))
        live-cells (:live-cells state)
        dead-cells (for [x (range (- min-cell 4) (+ 6 max-cell))
                         y (range (- min-cell 4) (+ 6 max-cell))]
                     [x y])
        all-cells (set/union live-cells dead-cells)]
    (doseq [cell all-cells]
      (let [center (/ 800 2)
            cell-size (/ 600 (get-max-cell-count state))
            cell-radius (/ cell-size 2)
            x (+ center (- (* (first cell) cell-size) cell-radius))
            y (+ center (- (* (second cell) cell-size) cell-radius))]
        (if (contains? live-cells cell)
          (q/fill 0 183 150)
          (q/fill 0 0 0))
        (q/rect x y cell-size cell-size))))
  )



(q/defsketch gui-quil
             :title "Game of Life"
             :size [800 800]
             :setup setup
             :update update-state
             :draw draw-state
             :features [:keep-on-top]
             :middleware [m/fun-mode])
