(ns aoc-2019.day-2
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(def input-filename "day-2-input.txt")

(defn read-file-to-vector
  [filename]
  (mapv #(read-string %) (-> (io/resource filename)
                             (slurp)
                             (str/split #","))))

(defn intcode
  [memory]
  (let [memory (atom memory)
        iptr (atom 0)]

    (while (not= 99 (get @memory @iptr))
      (let [op1 (get @memory (get @memory (+ @iptr 1)))
            op2 (get @memory (get @memory (+ @iptr 2)))
            op3 (get @memory (+ @iptr 3))]
        (case (get @memory @iptr)
          1 (swap! memory assoc op3 (+ op1 op2))
          2 (swap! memory assoc op3 (* op1 op2)))
        (reset! iptr (+ 4 @iptr))))
    @memory))

(defn restore-gravity-assist
  []
  (let [memory (read-file-to-vector input-filename)]
    (first (-> memory
               (assoc 1 12 2 2)
               (intcode)))))

(defn find-noun-and-verb
  [for-output]
  (let [memory (read-file-to-vector input-filename)
        pairs (for [noun (range 0 100)
                    verb (range 0 100)]
                [noun verb])]
    (first (filter (fn [pair]
                     (= for-output (first (-> memory
                                              (assoc 1 (first pair))
                                              (assoc 2 (last pair))
                                              (intcode))))) pairs))))

(comment
  (restore-gravity-assist)
  (find-noun-and-verb 19690720))
