(ns boyd.config
  (:require [clojure.java.io :as io]
            [environ.core :refer [env]]
            [clojure.edn :as edn]))

(def db-config
  (edn/read-string (slurp (io/resource "config.edn"))))

(defn db-uri []
  (if (= (env :environment) "production")
    (:production (:datomic db-config))
    (:test (:datomic db-config))))
