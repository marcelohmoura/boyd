(ns boyd.http-in
  (:require [schema.core :as s]
            [boyd.wire.out.product :as out.product]
            [boyd.wire.in.product :as in.product]
            [boyd.controller :as controller]))

(s/defn register-product! :- out.product/Product
  [product :- in.product/Product]
  (controller/register-product! product))

(s/defn lookup-product! :- out.product/Product
  [product-name :- s/Str]
  (controller/lookup-product! product-name))

(s/defn update-product!
  [product :- in.product/UpdateProduct]
  (controller/update-product! product))

(s/defn delete-product!
  [{:keys [id]} :- in.product/DeleteProduct]
  (controller/delete-product! id))
