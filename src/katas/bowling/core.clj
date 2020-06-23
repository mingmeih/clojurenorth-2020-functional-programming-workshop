(ns katas.bowling.core)

(defn total-score
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
