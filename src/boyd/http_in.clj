(ns boyd.http-in
  (:require [schema.core :as s]
            [boyd.wire.out.product :as out.product]
            [boyd.wire.in.product :as in.product]
            [boyd.controller :as controller]))

(s/defn register-product! :- out.product/Product
  [product :- in.product/Product]
  (controller/register-product! product))
