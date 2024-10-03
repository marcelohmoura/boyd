(ns boyd.db
  (:require [boyd.adapters :as adapters]
            [boyd.models :as models]
            [boyd.wire.out.product :as out.product]
            [schema.core :as s]
            [datomic.api :as d]))

(def db-uri "datomic:dev://localhost:4334/dev")

(def conn (d/connect db-uri))

(s/defn register-product! :- out.product/Product
  [product :- models/Product]
  (d/transact conn [product])
  (adapters/product:db->out product))
