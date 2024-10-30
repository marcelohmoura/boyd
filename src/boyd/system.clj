(ns boyd.system
  (:require [com.stuartsierra.component :as component]
            [boyd.db :refer [new-database]]))

(defn system []
  (component/system-map
    :database (new-database)))