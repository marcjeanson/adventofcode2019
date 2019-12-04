(ns aoc-2019.day-4)

(def start 240920)
(def end 789857)

(defn int-to-list
  [start]
  (loop [x start
         acc '()]
    (if (= (quot x 10) 0)
      (conj acc x)
      (recur (quot x 10) (conj acc (rem x 10))))))

(defn adjacent-matching-digits?
  [lst]
  (->> (partition-by identity lst)
       (map #(>= (count %) 2))
       (some true?)))

(defn part1
  [start end]
  (count (filter true? (map (fn [x]
                              (let [lst (int-to-list x)]
                                (and (= lst (sort lst)) (adjacent-matching-digits? lst)))) (range start end)))))

(defn valid-matching-digits?
  [lst]
  (->> (partition-by identity lst)
       (map #(= (count %) 2))
       (some true?)))

(defn part2
  [start end]
  (count (filter true? (map (fn [x]
                              (let [lst (int-to-list x)]
                                (and (= lst (sort lst)) (valid-matching-digits? lst)))) (range start end)))))

(comment
  (part1 start end)
  (part2 start end))