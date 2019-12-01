(ns aoc-2019.day-1-test
  (:require [aoc-2019.day-1 :as sut]
            [clojure.test :refer [deftest is]]))

(deftest fuel-for-mass
  (is (= 0 (sut/fuel-for-mass 2)))
  (is (= 2 (sut/fuel-for-mass 12)))
  (is (= 2 (sut/fuel-for-mass 14)))
  (is (= 654 (sut/fuel-for-mass 1969)))
  (is (= 33583 (sut/fuel-for-mass 100756))))

(deftest fuel-for-module
  (is (= 2 (sut/fuel-for-module 14)))
  (is (= 966 (sut/fuel-for-module 1969)))
  (is (= 50346 (sut/fuel-for-module 100756))))
