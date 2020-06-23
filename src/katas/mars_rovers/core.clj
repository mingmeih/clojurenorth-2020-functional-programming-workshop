(ns katas.mars-rovers.core)

(def directions [:N :E :S :W])

(defn turn
  [dirs start-dir]
  (-> (drop-while (partial not= start-dir) (cycle dirs))
      next first))

(def right (partial turn directions))
(def left (partial turn (reverse directions)))

(def dir->move-fn
  {:N (fn [[x y]] [x (inc y)])
   :E (fn [[x y]] [(inc x) y])
   :S (fn [[x y]] [x (dec y)])
   :W (fn [[x y]] [(dec x) y])})

(def instruction->fn
  {:L (fn [[x y dir]] [x y (left dir)])
   :R (fn [[x y dir]] [x y (right dir)])
   :M (fn [[x y dir]]
        (-> ((dir->move-fn dir) [x y])
            (conj dir)))})

(defn rover-step
  [[max-x max-y :as bounds] [x y dir :as rover] instruction]
  (-> ((instruction->fn instruction) rover)
      (update 0 #(max 0 (min % max-x)))
      (update 1 #(max 0 (min % max-y)))))

(defn go!
  [{:keys [plateau rovers]}]
  {:rovers (map
             (fn [{pos :position insts :instructions}]
               {:position
                (reduce (partial rover-step plateau)
                        pos
                        insts)})
             rovers)})
