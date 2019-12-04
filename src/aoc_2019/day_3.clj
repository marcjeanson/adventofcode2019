(ns aoc-2019.day-3
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [clojure.set :as set]))

(def input-filename "day-3-input.txt")

(defn read-file
  [file-name]
  (->> (io/reader file-name)
       line-seq))

(defn right
  [[x y] n]
  (vec (rest (take (+ 1 n) (iterate (fn [[a b]] [(+ a 1) b]) [x y])))))

(defn left
  [[x y] n]
  (vec (rest (take (+ 1 n) (iterate (fn [[a b]] [(- a 1) b]) [x y])))))

(defn up
  [[x y] n]
  (vec (rest (take (+ 1 n) (iterate (fn [[a b]] [a (+ b 1)]) [x y])))))

(defn down
  [[x y] n]
  (vec (rest (take (+ 1 n) (iterate (fn [[a b]] [a (- b 1)]) [x y])))))

(defn process-input
  [input]
  (let [input-vec (-> input
                      (str/split #","))]
    (map (fn [move]
           [(subs move 0 1) (read-string (subs move 1))]) input-vec)))

(defn calc-path
  [wire]
  (reduce (fn [acc path]
            (let [current (last acc)
                  direction (first path)
                  n (last path)]
              (into acc (case direction
                          "R" (right current n)
                          "L" (left current n)
                          "U" (up current n)
                          "D" (down current n))))) [[0 0]] wire))
(defn crossed-wires
  [wire1 wire2]
  (let [wire1-path (calc-path wire1)
        wire2-path (calc-path wire2)
        intersections (disj (set/intersection (set wire1-path) (set wire2-path)) [0 0])]
    (apply min (map (fn [intersection]
                (+ (Math/abs (first intersection)) (Math/abs (last intersection)))) intersections))))

(defn part1
  []
  (let [input (read-file (io/resource input-filename))
        wire1 (process-input (first input))
        wire2 (process-input (last input))]
    (crossed-wires wire1 wire2)))

(defn part2
  []
  (let [input (read-file (io/resource input-filename))
        wire1-path (-> (first input)
                       (process-input)
                       (calc-path))
        wire2-path (-> (last input)
                       (process-input)
                       (calc-path))
        intersections (disj (set/intersection (set wire1-path) (set wire2-path)) [0 0])]

    (apply min (map (fn [x]
                      (+ (.indexOf wire1-path x) (.indexOf wire2-path x))) intersections))))

(comment
  (part1)
  (part2))
