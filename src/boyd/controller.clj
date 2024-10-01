(ns boyd.controller
  (:require [boyd.wire.in.product :as in.product]
            [boyd.wire.out.product :as out.product]
            [boyd.adapters :as adapters]
            [ring.util.http-response :as http-response]
            [boyd.db :as db]
            [schema.core :as s]))

(s/defn register-product! :- out.product/Product
  [product :- in.product/Product]
  (-> (adapters/product:wire->internal product)
      db/register-product!))
