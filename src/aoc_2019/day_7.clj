(ns aoc-2019.day-7
  (:require [aoc-2019.intcode :refer [intcode]]
            [aoc-2019.utils :as u]
            [clojure.math.combinatorics :as combo]))

(def input-filename "day-7-input.txt")

(defn amp-output
  [input phase acs]
  (let [input (:output input)
        ic (intcode {:input (list phase input)
                     :output []
                     :mem acs
                     :ip 0
                     :status :running})]
    {:output (last (:output ic))}))

(defn part1
  []
  (let [acs (u/read-file-to-vector input-filename)
        phase-settings (combo/permutations (range 5))]

    (->> phase-settings
         (map (fn [phase-setting]
                (-> {:output 0}
                    (amp-output (nth phase-setting 0) acs)
                    (amp-output (nth phase-setting 1) acs)
                    (amp-output (nth phase-setting 2) acs)
                    (amp-output (nth phase-setting 3) acs)
                    (amp-output (nth phase-setting 4) acs)
                    (get :output))))
         (apply max))))

(defn amp-output-2
  [input memory]
  (let [input (-> (:input @memory)
                  (conj (if (= (:status input) :halted) (last (:input input)) (last (:output input))))
                  reverse)
        ic-input (-> @memory
                     (assoc :input input)
                     (assoc :output []))
        ic (intcode ic-input)]
    (reset! memory ic)
    ic))

(defn part2 []
  (let [acs (u/read-file-to-vector input-filename)
        phase-settings (combo/permutations (range 5 10))
        init-intcode (fn [phase]
                       {:input (list phase)
                        :output []
                        :mem acs
                        :ip 0
                        :rbase 0
                        :status :running})]
    (->> phase-settings
         (map (fn [phase-setting]
                (let [a-mem (atom (init-intcode (nth phase-setting 0)))
                      b-mem (atom (init-intcode (nth phase-setting 1)))
                      c-mem (atom (init-intcode (nth phase-setting 2)))
                      d-mem (atom (init-intcode (nth phase-setting 3)))
                      e-mem (atom (init-intcode (nth phase-setting 4)))
                      feedback (atom {:output [0]
                                      :status :running})]

                  (while (= (:status @feedback) :running)
                    (reset! feedback (-> @feedback
                                         (amp-output-2 a-mem)
                                         (amp-output-2 b-mem)
                                         (amp-output-2 c-mem)
                                         (amp-output-2 d-mem)
                                         (amp-output-2 e-mem))))
                  (first (:input @feedback)))))
         (apply max))))

(comment
  (part1)
  (part2))
