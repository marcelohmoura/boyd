(ns boyd.db
  (:require [com.stuartsierra.component :as component]
            [boyd.config :refer [db-uri]]
            [boyd.adapters :as adapters]
            [boyd.models :as models]
            [boyd.wire.out.product :as out.product]
            [boyd.wire.db.product :as db.product]
            [schema.core :as s]
            [datomic.api :as d]))

(defrecord Database []
  component/Lifecycle
  (start [this]
    (assoc this :conn (d/connect (db-uri))))
  (stop [this]
    (d/release (get this :conn))
    (assoc this :conn nil)))

(defn new-database []
  (d/create-database (db-uri))
  (d/transact (d/connect (db-uri)) db.product/Product)
  (map->Database {}))

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

(def get-product-entity
  '[:find ?e
    :in $ ?id
    :where [?e :product/id ?id]])

(s/defn register-product! :- out.product/Product
  [db-conn :- s/Any
   product :- models/Product]
  (d/transact db-conn [product])
  (adapters/product:db->out product))

(s/defn lookup-product! :- out.product/Product
  [db-conn :- s/Any
   product-name :- s/Str]
  (first (d/q get-product-by-name (d/db db-conn) product-name)))

(s/defn lookup-product-entity! :- s/Num
  [db-conn :- s/Any
   product-id :- s/Uuid]
  (first (first (d/q get-product-entity (d/db db-conn) product-id))))

(s/defn update-product!
  [db-conn :- s/Any
   product :- models/Product]
  (d/transact db-conn [product]))

(s/defn delete-product!
  [db-conn :- s/Any
   id :- s/Uuid]
  (let [entity (d/entity db-conn id)
        retracts (for [[attr value] entity]
                   [:db/retract id attr value])]
    (d/transact db-conn retracts)))
