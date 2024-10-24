(ns boyd.models
  (:require [schema.core :as s]))

(s/defschema Product
  {:product/id          s/Uuid
   :product/name        s/Str
   :product/price       s/Num
   :product/category    s/Str
   :product/description s/Str})

(s/defschema UpdateProduct
  (assoc Product :db/id s/Num))
