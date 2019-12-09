(ns aoc-2019.day-6
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [clojure.set :as set]))

(def input-filename "day-6-input.txt")

(defn read-file
  [file-name]
  (map #(str/split % #"\)") (->> (io/reader file-name)
                                 line-seq)))

(defn orbit-map
  [input]
  (reduce (fn [orbit path]
            (derive orbit (keyword "orbit" (peek path)) (keyword "orbit" (first path)))) (make-hierarchy) input))

(defn part1
  []
  (let [input (read-file (io/resource input-filename))
        orbit-map (orbit-map input)]
    (reduce + (map (fn [node] (count (ancestors orbit-map node)))(descendants orbit-map :orbit/COM)))))


(defn transfers
  [orbit-map]
  (let [you (ancestors orbit-map :orbit/YOU)
        san (ancestors orbit-map :orbit/SAN)
        both (set/intersection you san)]
    (count (set/union (set/difference you both) (set/difference san both)))))

(defn part2
  []
  (let [input (read-file (io/resource input-filename))
        orbit-map (orbit-map input)]
    (transfers orbit-map)))

(comment
  (part1)
  (part2))
