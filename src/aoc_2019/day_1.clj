(ns aoc-2019.day-1
  (:require [clojure.java.io :as io]
            [clojure.math.numeric-tower :as math]))

(defn fuel-for-mass
  [mass]
  (let [fuel (-> mass
                 (/ 3)
                 (math/floor)
                 (- 2))]
    (if (< fuel 0)
      0
      fuel)))

(defn fuel-for-module
  [module-mass]
  (loop [total-fuel 0
         fuel (fuel-for-mass module-mass)]
    (if (= 0 fuel)
      total-fuel
      (recur (+ total-fuel fuel) (fuel-for-mass fuel)))))

(defn read-file
  [input]
  (with-open [rdr (io/reader input)]
    (doall (line-seq rdr))))

(defn part-1 [path]
  (let [input (read-file path)]
    (->> input
         (map read-string)
         (map fuel-for-mass)
         (reduce +))))

(defn part-2 [path]
  (let [input (read-file path)]
    (->> input
         (map read-string)
         (map fuel-for-module)
         (reduce +))))

(part-1 (io/resource "day-1-input.txt"))
(part-2 (io/resource "day-1-input.txt"))
