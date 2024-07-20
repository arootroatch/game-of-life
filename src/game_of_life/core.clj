(ns game-of-life.core)

(defn get-neighbors-of [[x y]]
  (set (for [Y [(dec y) y (inc y)]
             X [(dec x) x (inc x)]
             :when (not= [x y] [X Y])] [X Y])))

(defn evolve-cell [live-cells cell]
    (let [neighbors (get-neighbors-of cell)
          live-neighbors (filter live-cells neighbors)]
      (case (count live-neighbors)
        2 (when (contains? live-cells cell) cell)
        3 cell
        nil)))

(defn evolve [grid]
  (let [full-grid (set (mapcat get-neighbors-of grid))]
    (set (filter some? (map #(evolve-cell grid %) full-grid)))))