(ns boyd.wire.in.product
  (:require [schema.core :as s]))

(s/defschema Product
  {:name        {:schema   s/Str
                 :required true}
   :price       {:schema   s/Num
                 :required true}
   :category    {:schema   s/Keyword
                 :required true}
   :description {:schema   s/Str
                 :required true}})
