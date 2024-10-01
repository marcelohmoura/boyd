(ns boyd.wire.in.product
  (:require [schema.core :as s]))

(s/defschema Product
  {:name        s/Str
   :price       s/Num
   :category    s/Str
   :description s/Str})
