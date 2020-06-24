(ns katas.bowling.core)

;;; pseudo-functional, mostly-imperative loop
(defn total-score-loop
  [rolls]
  (loop [rolls rolls
         frame 1
         score 0]
    (if (empty? rolls)
      score
      (let [[r1 r2 & next] rolls]
        (cond
          (= 10 r1)
          (if (= 10 frame)
            (+ score r1 r2 (first next))
            (recur
              (cons r2 next)
              (inc frame)
              (+ score r1 r2 (first next))))

          (= 10 (+ r1 r2))
          (if (= 10 frame)
            (+ score 10 (first next))
            (recur
              next
              (inc frame)
              (+ score 10 (first next))))

          :else
          (recur
            next
            (inc frame)
            (+ score r1 r2)))))))

;;; more functional approach

(defn rolls->frames
  [rolls]
  (loop [rolls rolls
         frames []]
    (cond
      (empty? rolls) frames

      (= 10 (first rolls))
      (recur (next rolls)
             (conj frames [(first rolls)]))

      :else
      (recur (nnext rolls)
             (conj frames [(first rolls)
                           (fnext rolls)])))))

(defn frame-score
  "Calculate the score for `frame`, given `frame-1` and `frame-2` are
  the subsequent frames."
  [[frame frame-1 frame-2]]
  (->> frame
       (concat
         (cond
           (= 10 (first frame))
           (take 2 (concat frame-1 frame-2))

           (= 10 (apply + frame))
           (take 1 frame-1)

           :else [0]))
       (reduce +)))

(defn total-score
  [rolls]
  (->> (concat rolls [0 0 0 0]) ; pad the end, so we can always take enough frames
       rolls->frames
       (partition 3 1) ; need up to two frames of look-ahead
       (map-indexed vector) ; add index to count frame # (bonus frames are different)
       (take-while (comp (partial > 10) first))
       (map second)
       (map frame-score)
       (reduce + 0)))

(comment
  (rolls->frames [1 2 3 4 5 1 2 3 4 5 1 2 3 4 5 1 2 3 4 5])
  (rolls->frames [10 3 4 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0])


  (frame-score [[10] [10] [10]])
  (frame-score [[5 5] [1 2] [3 4]])
  (frame-score [[0 10] [1 2] [3 4]])
  (frame-score [[5 4] [1 2] [3 4]])

  (total-score [10 3 4 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0])

  (total-score [1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1])
  )
