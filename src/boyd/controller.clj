(ns boyd.controller
  (:require [boyd.wire.in.product :as in.product]
            [boyd.wire.out.product :as out.product]
            [boyd.adapters :as adapters]
            [ring.util.http-response :as http-response]
            [boyd.db :as db]
            [schema.core :as s]))

(s/defn register-product! :- out.product/Product
  [product :- in.product/Product]
  (try
    (-> (adapters/product:wire->internal product)
        db/register-product!
        http-response/ok)
    (catch Exception e
      (.getMessage e))))

(s/defn lookup-product! :- out.product/Product
  [product-name :- s/Str]
  (if-let [product (db/lookup-product! product-name)]
    (http-response/ok {:result product})
    (http-response/not-found)))

(s/defn update-product!
  [{:keys [id] :as product} :- in.product/UpdateProduct]
  (if-let [product-entity (db/lookup-product-entity! id)]
    (try
      (-> (adapters/update-product:wire->internal product-entity product)
          db/update-product!)
      (http-response/ok)
      (catch Exception e
        (.getMessage e)))
    (http-response/not-found)))

(s/defn delete-product!
  [id :- s/Uuid]
  (if-let [product-entity (db/lookup-product-entity! id)]
    (try
      (db/delete-product! product-entity)
      (http-response/no-content)
      (catch Exception e
        (.getMessage e)))
    (http-response/not-found)))
