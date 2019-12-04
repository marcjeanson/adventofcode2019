(ns aoc-2019.day-4)

(def start 240920)
(def end 789857)

(defn adjacent-matching-digits?
  [lst]
  (->> (partition-by identity lst)
       (map #(>= (count %) 2))
       (some true?)))

(defn part1
  [start end]
  (count (filter true? (map (fn [x]
                              (let [lst (map str (str x))]
                                (and (= lst (sort lst)) (adjacent-matching-digits? lst)))) (range start end)))))

(defn valid-matching-digits?
  [lst]
  (->> (partition-by identity lst)
       (map #(= (count %) 2))
       (some true?)))

(defn part2
  [start end]
  (count (filter true? (map (fn [x]
                              (let [lst (map str (str x))]
                                (and (= lst (sort lst)) (valid-matching-digits? lst)))) (range start end)))))

(comment
  (part1 start (inc end))
  (part2 start (inc end)))
