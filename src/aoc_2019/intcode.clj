(ns aoc-2019.intcode)

(defn int-to-vector
  [start]
  (loop [x start
         acc []]
    (if (= (quot x 10) 0)
      (conj acc x)
      (recur (quot x 10) (conj acc (rem x 10))))))

(defn parse-instruction
  [inst]
  (let [v (int-to-vector inst)]
    {:modes (into [] (nthrest v 2))
     :opcode (if (= 1 (count v))
               (first v)
               (+ (* 10 (get v 1)) (get v 0)))}))

(defn process-instruction
  [{:keys [input ip mem rbase] :as state}]
  (let [mget (fn [addr] (get mem addr 0))
        mget-rel (fn [rel-addr] (mget (+ ip rel-addr)))
        mset (fn [state addr value]
               (let [mem-size (count (:mem state))
                     memory (if (> addr mem-size)
                              ((comp vec flatten conj) (:mem state) (repeat (- addr mem-size) 0))
                              (:mem state))]
                 (assoc state :mem (assoc memory addr value))))
        move-ip (fn [state rel-addr] (assoc state :ip (+ ip rel-addr)))
        inst (parse-instruction (mget ip))
        mode-1 (get-in inst [:modes 0] 0)
        mode-2 (get-in inst [:modes 1] 0)
        mode-3 (get-in inst [:modes 2] 0)
        mget-param (fn [mode rel-addr]
                     (case mode
                       0 (mget (mget-rel rel-addr))
                       1 (mget-rel rel-addr)
                       2 (mget (+ rbase (mget-rel rel-addr)))))
        param-1 (fn [] (mget-param mode-1 1))
        param-2 (fn [] (mget-param mode-2 2))
        param-3 (fn [] (mget-param mode-3 3))
        target (fn [mode rel-addr]
                 (case mode
                   0 (mget-rel rel-addr)
                   2 (+ rbase (mget-rel rel-addr))))]
    (case (:opcode inst)
      1 (-> state
            (mset (target mode-3 3) (+ (param-1) (param-2)))
            (move-ip 4))
      2 (-> state
            (mset (target mode-3 3) (* (param-1) (param-2)))
            (move-ip 4))
      3 (-> state
            (mset (target mode-1 1) (peek input))
            (assoc :input (pop input))
            (move-ip 2))
      4 (-> state
            (update :output conj (param-1))
            (move-ip 2))
      5 (if (not= 0 (param-1))
          (assoc state :ip (param-2))
          (move-ip state 3))
      6 (if (= 0 (param-1))
          (assoc state :ip (param-2))
          (move-ip state 3))
      7 (-> state
            (mset (target mode-3 3) (if (< (param-1) (param-2)) 1 0))
            (move-ip 4))
      8 (-> state
            (mset (target mode-3 3) (if (= (param-1) (param-2)) 1 0))
            (move-ip 4))
      9 (-> state
            (update :rbase + (param-1))
            (move-ip 2))
      99 (-> state
             (assoc :status :halted)))))

(defn intcode
  [state]
  (->> (iterate process-instruction state)
       (some #(and (or (seq (:output %)) (= (:status %) :halted)) %))))
