(ns boyd.helpers
  (:require [schema.core :as s]))

(s/defn ->uuid :- s/Uuid
  []
  (java.util.UUID/randomUUID))
