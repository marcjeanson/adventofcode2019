(ns aoc-2019.day-8
  (:require [clojure.java.io :as io]))

(def day-8-input "day-8-input.txt")

(defn part1
  [width height]
  (let [input (map #(Character/digit % 10)(-> (slurp (io/resource day-8-input))
                                              (char-array)
                                              (seq)))
        layers (partition (* width height) input)
        min-zero-layer (apply min-key (fn [layer]
                                        (count (filter #(= 0 %) layer))) layers)]

    (* (count (filter #(= 1 %) min-zero-layer)) (count (filter #(= 2 %) min-zero-layer)))))

(defn render
  [input width height]
  (let [layer-size (* width height)
        black-and-white (map (fn [n]
                               (first (->> input
                                           (drop n)
                                           (take-nth layer-size)
                                           (drop-while #(= \2 %))))) (range layer-size))
        rendered (map {\0 \space \1 \u25A0} black-and-white)
        image (partition width rendered)]

    (for [row image]
      (println row))))

(defn part2
  []
  (let [input (-> (slurp (io/resource day-8-input))
              (char-array)
              (seq))]
    (render input 25 6)))

(comment
  (part1 25 6)
  (part2))
