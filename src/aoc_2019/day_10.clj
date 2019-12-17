(ns aoc-2019.day-10
  (:require [clojure.java.io :as io]))

(def day-10-input "day-10-input.txt")

(defn read-coordinates []
  (let [data (->> (io/reader (io/resource day-10-input))
                  line-seq
                  (mapv (fn [line]
                          (vec (char-array line)))))]
    (for [x (range 36)
          y (range 36)
          :when (= \# (get-in data [y x]))]
      {:x x
       :y y})))

(defn calc-angle [a1 a2]
  (let [angle (- (Math/toDegrees (Math/atan2 (- (:y a1) (:y a2)) (- (:x a1) (:x a2)))) 90)]
    (cond-> angle
      (neg? angle) (+ 360))))

(defn best-location [asteroids]
  (apply max-key :count (map (fn [asteroid]
                               (let [others (remove #(= asteroid %) asteroids)
                                     angles (map #(calc-angle asteroid %) others)
                                     unique (count (distinct angles))]
                                 (merge asteroid {:count unique}))) asteroids)))
(defn part1 []
  (-> (read-coordinates)
      (best-location)))

(defn calc-distance [a1 a2]
  (Math/sqrt (+ (* (- (:x a1) (:x a2)) (- (:x a1) (:x a2)))
                (* (- (:y a1) (:y a2)) (- (:y a1) (:y a2))))))

(defn targets [asteroids station]
  (let [angle-distances (vals (->> (remove #(= station %) asteroids)
                                   (map (fn [other]
                                          (merge other {:angle (calc-angle station other)
                                                        :distance (calc-distance station other)})))
                                   (group-by :angle)
                                   (reduce-kv (fn [m k v]
                                                (assoc m k (into [] (sort-by :distance v)))) {})
                                   (into (sorted-map))))
        max-counts (apply max (map count angle-distances))
        padded-lists (map (fn [a]
                            (let [pad-count (- max-counts (count a))]
                              (concat a (take pad-count (repeat nil))))) angle-distances)]

    (remove nil? (apply interleave padded-lists))))

(defn part2 []
  (let [targets (-> (read-coordinates)
                    (targets {:x 26 :y 29}))
        target (->> targets
                    (drop 199)
                    (first))]
    (+ (* (:x target) 100) (:y target))))

(comment
  (part1)
  (part2))
