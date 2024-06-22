(ns a-3-comp348.core
  (:require [a-3-comp348.db :as db]
            [a-3-comp348.menu :as menu])
  (:gen-class))

(def citiesDB (db/loadData "resources/cities.txt"))

(defn -main [& args]
  (menu/show-menu citiesDB))
