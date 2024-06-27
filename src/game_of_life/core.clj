(ns game-of-life.core)

(defn get-neighbors-of [[x y]]
  #{[(dec x) (dec y)] [x (dec y)] [(inc x) (dec y)]
    [(dec x) y] [(inc x) y]
    [(dec x) (inc y)] [x (inc y)] [(inc x) (inc y)]})

(defn evolve-cell [grid cell]
    (let [neighbors (get-neighbors-of cell)
          count-active-neighbors (count (filter grid neighbors))]
      (case count-active-neighbors
        2 (when (contains? grid cell) cell)
        3 cell
        nil)))

(defn evolve [grid]
  (let [full-grid (set (mapcat get-neighbors-of grid))]
    (set (filter some? (map #(evolve-cell grid %) full-grid)))))