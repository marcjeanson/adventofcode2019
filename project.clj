(defproject aoc-2019 "0.1.0-SNAPSHOT"
  :description "Advent of Code 2019"
  :url "https://adventofcode.com"
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [org.clojure/math.numeric-tower "0.0.4"]]
  :main ^:skip-aot aoc-2019.core
  :target-path "target/%s"
  :resource-paths ["resources"]
  :profiles {:uberjar {:aot :all}})
