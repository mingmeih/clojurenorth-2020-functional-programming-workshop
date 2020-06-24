(ns katas.monty-hall.core)

(defn stay-with-original-door
  [picked other]
  picked)

(defn switch-doors
  [picked other]
  other)

(defn simulate-one
  [strategy]
  (let [choices [:one :two :three]
        answer (rand-nth choices)
        ;; assume, without loss of generality, person always picks
        ;; first door
        picked :one
        revealed (first (remove (set [picked answer]) choices))
        other (first (remove (set [revealed picked]) choices))
        picked (strategy picked other)]
    (= picked answer)))

(defn simulate-strategy
  [strategy]
  (->> (for [_ (range 1000)]
         (simulate-one strategy))
       (group-by identity)
       (map (fn [[k v]] [k (count v)]))
       (into {})))

(defn simulate
  []
  (let [stay (simulate-strategy stay-with-original-door)
        switch (simulate-strategy switch-doors)]
    {:stay stay
     :switch switch}))

(defn display-result
  [{won true lost false}]
  (str (Math/floor (* (/ won (+ lost won)) 100)) "%"))

(defn display-results
  [{:keys [stay switch]}]
  (println "Stay strategy won " (display-result stay) " of the time")
  (println "Switch strategy won " (display-result switch) " of the time")
  )

(comment
  (simulate)
  (display-results (simulate))
  )
