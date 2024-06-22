(ns a-3-comp348.menu
  (:require [clojure.string :as str]))

(defn list-cities [citiesDB]
  (println "\n*** List Cities Submenu ***")
  (println "1.1. List all cities, ordered by city name (ascending)")
  (println "1.2. List all cities for a given province, ordered by size (descending) and name (ascending)")
  (println "1.3. List all cities for a given province, ordered by population density (ascending)")
  (print "Enter an option? ")
  (flush)
  (let [option (read-line)]
    (case option
      "1.1" (println (str "\n[" (str/join " " (map #(str "\"" % "\"") (map :city (sort-by :city citiesDB)))) "]"))
      "1.2" (do
              (println "\nEnter province name: ")
              (flush)
              (let [province (read-line)
                    cities (filter #(= (:province %) province) citiesDB)
                    sorted-cities (sort-by (juxt (comp - :size-value) :city) cities)]
                (doseq [[i city] (map-indexed vector sorted-cities)]
                  (println (str (inc i) ": " [(city :city) (city :size) (city :population)])))))
      "1.3" (do
              (print "\nEnter province name: ")
              (flush)
              (let [province (read-line)
                    cities (filter #(= (:province %) province) citiesDB)
                    sorted-cities (sort-by #(float (/ (:population %) (:area %))) cities)]
                (doseq [[i city] (map-indexed vector sorted-cities)]
                  (println (str (inc i) ": " [(city :city) (city :size) (city :population)])))))
      (println "\nInvalid option"))))

(defn display-city-info [citiesDB]
  (print "\nEnter city name: ")
  (flush)
  (let [city-name (read-line)
        city-info (first (filter #(= (:city %) city-name) citiesDB))]
    (if city-info
      (println (str "\n[" (str/join " " [(str "\"" (city-info :city) "\"")
                                       (str "\"" (city-info :province) "\"")
                                       (str "\"" (city-info :size) "\"")
                                       (city-info :population)
                                       (city-info :area)]) "]"))
      (println "\nCity not found"))))

(defn list-provinces [citiesDB]
  (let [province-groups (group-by :province citiesDB)
        province-counts (map (fn [[k v]] [k (count v)]) province-groups)
        sorted-provinces (sort-by (comp - second) province-counts)]
    (doseq [[i [province count]] (map-indexed vector sorted-provinces)]
      (println (str (inc i) ": " [province count])))
    (println (str "Total: " (count province-counts) " provinces, " (count citiesDB) " cities on file."))))

(defn display-province-info [citiesDB]
  (let [province-groups (group-by :province citiesDB)
        province-populations (map (fn [[k v]] [k (reduce + (map :population v))]) province-groups)
        sorted-provinces (sort-by first province-populations)]
    (doseq [[i [province population]] (map-indexed vector sorted-provinces)]
      (println (str (inc i) ": " [province population])))))

(defn show-menu [citiesDB]
  (loop []
    (println "\n*** City Information Menu ***")
    (println "-----------------------------")
    (println "1. List Cities")
    (println "2. Display City Information")
    (println "3. List Provinces")
    (println "4. Display Province Information")
    (println "5. Exit")
    (print "Enter an option? ")
    (flush)
    (let [option (read-line)]
      (case option
        "1" (list-cities citiesDB)
        "2" (display-city-info citiesDB)
        "3" (list-provinces citiesDB)
        "4" (display-province-info citiesDB)
        "5" (do (println "\nGood Bye") (System/exit 0))
        (println "\nInvalid option"))
      (recur))))
