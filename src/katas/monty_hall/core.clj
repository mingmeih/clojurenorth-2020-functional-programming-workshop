(ns katas.monty-hall.core)

(def doors {0 0
            1 0
            2 1})
;; each key is a door, and val of 0 is a goat, val of 1 is a car

(defn simulate [n]
  (reduce (fn [result _]
            (let [choice (rand-int 3)
                  switch (val (last (dissoc doors choice)))]
              ;; out of the 2 remaining doors, the first will be a goat in the map so pick the last one (?)
              (-> result
                  (update :stay + (get doors choice))
                  (update :switch + switch))))
          {:stay 0 :switch 0} (range n)))