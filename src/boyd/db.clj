(ns boyd.db
  (:require [boyd.adapters :as adapters]
            [boyd.models :as models]
            [boyd.wire.out.product :as out.product]
            [schema.core :as s]))

(s/defn register-product! :- out.product/Product
  [product :- models/Product]
  ;; TODO: Implement DB persistence
  (adapters/product:db->out product))
