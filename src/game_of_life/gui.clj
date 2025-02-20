(ns game-of-life.gui
  (:require [game-of-life.core :as life]
            [quil.core :as q]
            [quil.middleware :as m]))

(def window-size 800)
(def square-size 50)
(def pulsar #{[-1 -2] [-1 -3] [-1 -4] [-2 -1] [-3 -1] [-4 -1] [-6 -2] [-6 -3] [-6 -4] [-2 -6] [-3 -6] [-4 -6]
              [1 -2] [1 -3] [1 -4] [2 -1] [3 -1] [4 -1] [6 -2] [6 -3] [6 -4] [2 -6] [3 -6] [4 -6]
              [-1 2] [-1 3] [-1 4] [-2 1] [-3 1] [-4 1] [-6 2] [-6 3] [-6 4] [-2 6] [-3 6] [-4 6]
              [1 2] [1 3] [1 4] [2 1] [3 1] [4 1] [6 2] [6 3] [6 4] [2 6] [3 6] [4 6]})
(def pulsar-boat #{[0 0] [-1 -2] [-1 -3] [-1 -4] [-2 -1] [-3 -1] [-4 -1] [-6 -2] [-6 -3] [-6 -4] [-2 -6] [-3 -6] [-4 -6]
              [1 -2] [1 -3] [1 -4] [2 -1] [3 -1] [4 -1] [6 -2] [6 -3] [6 -4] [2 -6] [3 -6] [4 -6]
              [-1 2] [-1 3] [-1 4] [-2 1] [-3 1] [-4 1] [-6 2] [-6 3] [-6 4] [-2 6] [-3 6] [-4 6]
              [1 2] [1 3] [1 4] [2 1] [3 1] [4 1] [6 2] [6 3] [6 4] [2 6] [3 6] [4 6]})
(def glider #{[-10 -9][-9 -8][-8 -8][-8 -9][-8 -10]})

(defn create-square [[x y] live-cells]
  (let [center-of-window (/ window-size 2)
        center-x (+ (* square-size x) center-of-window)
        center-y (+ (* square-size y) center-of-window)]
    (if (contains? live-cells [x y])
      (q/fill 0 183 150)
      (q/fill 0 0 0))
    (q/rect center-x center-y 50 50)))

(defn setup []
  (q/rect-mode :center)
  :frame-rate (q/frame-rate 2)
  :color-mode (q/color-mode :rgb)
  {:live-cells pulsar-boat})


(defn update-state [state]
  {:live-cells (life/evolve (:live-cells state))})

(defn ->full-grid [live-cells]
  (set (mapcat life/get-neighbors-of live-cells)))

(defn draw-state [state]
  (q/background 0)
  (run! #(create-square % (:live-cells state)) (->full-grid (:live-cells state))))

(defn -main []
  (q/defsketch gui-quil
               :title "Game of Life"
               :size [window-size window-size]
               :setup setup
               :update update-state
               :draw draw-state
               :features [:keep-on-top]
               :middleware [m/fun-mode]))