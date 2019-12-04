(ns aoc-2019.day-1
  (:require [aoc-2019.utils :as utils]
            [clojure.java.io :as io]))

(defn fuel-for-mass
  [mass]
  (-> mass
      (quot 3)
      (- 2)
      (Math/max 0)))

(defn fuel-for-module
  [module-mass]
  (loop [total-fuel 0
         fuel (fuel-for-mass module-mass)]
    (if (= 0 fuel)
      total-fuel
      (recur (+ total-fuel fuel) (fuel-for-mass fuel)))))

(defn part-1 [path]
  (->> (utils/read-file path)
       (map fuel-for-mass)
       (reduce +)))

(defn part-2 [path]
  (->> (utils/read-file path)
       (map read-string)
       (map fuel-for-module)
       (reduce +)))

(comment
  (part-1 (io/resource "day-1-input.txt"))
  (part-2 (io/resource "day-1-input.txt")))
