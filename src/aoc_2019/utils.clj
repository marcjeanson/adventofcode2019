(ns aoc-2019.utils
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(defn read-file
  [file-name]
  (->> (io/reader file-name)
       line-seq
       (map #(read-string %))))

(defn read-file-to-vector
  [filename]
  (mapv #(read-string %) (-> (io/resource filename)
                             (slurp)
                             (str/split #","))))
