(ns boyd.db
  (:require [boyd.adapters :as adapters]
            [boyd.models :as models]
            [boyd.wire.out.product :as out.product]
            [schema.core :as s]
            [datomic.api :as d]))

(def db-uri "datomic:dev://localhost:4334/dev")

(def conn (d/connect db-uri))

(def db (d/db conn))

(def get-product-by-name
  '[:find ?id ?name ?price ?category ?description
    :keys id name price category description
    :in $ ?search-name
    :where
    [?e :product/name ?search-name]
    [?e :product/name ?name]
    [?e :product/id ?id]
    [?e :product/price ?price]
    [?e :product/category ?category]
    [?e :product/description ?description]])

(s/defn register-product! :- out.product/Product
  [product :- models/Product]
  (d/transact conn [product])
  (adapters/product:db->out product))

(s/defn lookup-product! :- out.product/Product
  [product-name :- s/Str]
  (first (d/q get-product-by-name db product-name)))
