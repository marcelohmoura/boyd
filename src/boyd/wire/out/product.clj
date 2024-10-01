(ns boyd.wire.out.product
  (:require [schema.core :as s]))

(s/defschema Product
  {:id          s/Uuid
   :name        s/Str
   :price       s/Num
   :category    s/Str
   :description s/Str})
