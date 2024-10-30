(ns boyd.controller
  (:require [boyd.wire.in.product :as in.product]
            [boyd.wire.out.product :as out.product]
            [boyd.adapters :as adapters]
            [ring.util.http-response :as http-response]
            [boyd.db :as db]
            [schema.core :as s]))

(s/defn register-product! :- out.product/Product
  [db-conn :- s/Any
   product :- in.product/Product]
  (try
    (->> (adapters/product:wire->internal product)
         (db/register-product! db-conn)
         http-response/ok)
    (catch Exception e
      (.getMessage e))))

(s/defn lookup-product! :- out.product/Product
  [db-conn :- s/Any
   product-name :- s/Str]
  (if-let [product (db/lookup-product! db-conn product-name)]
    (http-response/ok {:result product})
    (http-response/not-found)))

(s/defn update-product!
  [db-conn :- s/Any
   {:keys [id] :as product} :- in.product/UpdateProduct]
  (if-let [product-entity (db/lookup-product-entity! db-conn id)]
    (try
      (->> (adapters/update-product:wire->internal product-entity product)
           (db/update-product! db-conn))
      (http-response/ok)
      (catch Exception e
        (.getMessage e)))
    (http-response/not-found)))

(s/defn delete-product!
  [db-conn :- s/Any
   id :- s/Uuid]
  (if-let [product-entity (db/lookup-product-entity! db-conn id)]
    (try
      (db/delete-product! db-conn product-entity)
      (http-response/no-content)
      (catch Exception e
        (.getMessage e)))
    (http-response/not-found)))
