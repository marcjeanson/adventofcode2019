(ns aoc-2019.day-9
  (:require [aoc-2019.intcode :refer [intcode]]
            [aoc-2019.utils :as u]))


(def input-filename "day-9-input.txt")

(defn part1
  []
  (let [boost (u/read-file-to-vector input-filename)]
    (:output (intcode boost '(1)))))

(defn part2
  []
  (let [boost (u/read-file-to-vector input-filename)]
    (:output (intcode boost '(2)))))

(comment
  (part1)
  (part2))
