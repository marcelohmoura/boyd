(ns boyd.http-in
  (:require [schema.core :as s]
            [boyd.wire.out.product :as out.product]
            [boyd.wire.in.product :as in.product]
            [boyd.controller :as controller]))

(s/defn register-product! :- out.product/Product
  [db-conn :- s/Any
   product :- in.product/Product]
  (controller/register-product! db-conn product))

(s/defn lookup-product! :- out.product/Product
  [db-conn :- s/Any
   product-name :- s/Str]
  (controller/lookup-product! db-conn product-name))

(s/defn update-product!
  [db-conn :- s/Any
   product :- in.product/UpdateProduct]
  (controller/update-product! db-conn product))

(s/defn delete-product!
  [db-conn :- s/Any
   {:keys [id]} :- in.product/DeleteProduct]
  (controller/delete-product! db-conn id))
