(ns aoc-2019.utils
  (:require [clojure.java.io :as io]))

(defn read-file
  [file-name]
  (->> (io/reader file-name)
       line-seq
       (map #(read-string %))))
