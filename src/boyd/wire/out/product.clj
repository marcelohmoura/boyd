(ns boyd.wire.out.product
  (:require [schema.core :as s]))

(s/defschema Product
  {:id          {:schema   s/Uuid
                 :required true}
   :name        {:schema   s/Str
                 :required true}
   :price       {:schema   s/Num
                 :required true}
   :category    {:schema   s/Keyword
                 :required true}
   :description {:schema   s/Str
                 :required true}})
