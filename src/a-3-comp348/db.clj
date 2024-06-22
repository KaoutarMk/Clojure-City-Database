(ns a-3-comp348.db
  (:require [clojure.string :as str]))

(def size-mapping
  {"Small" 1
   "Medium" 2
   "Large urban" 3})

(defn parse-line [line]
  (let [[city province size population area] (str/split line #"\|")]
    {:city city
     :province province
     :size size
     :size-value (size-mapping size) ; Add this line to map size to a numeric value
     :population (read-string population)
     :area (read-string area)}))

(defn loadData [filename]
  (let [lines (str/split-lines (slurp filename))]
    (map parse-line lines)))
