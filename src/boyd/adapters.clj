(ns boyd.adapters
  (:require [boyd.wire.in.product :as in.product]
            [boyd.models :as models]
            [boyd.wire.out.product :as out.product]
            [schema.core :as s]
            [boyd.helpers :as helpers]))

(s/defn product:wire->internal :- models/Product
  [{:keys [name price category description]} :- in.product/Product]
  {:product/id          (helpers/->uuid)
   :product/name        name
   :product/price       (bigdec price)
   :product/category    category
   :product/description description})

(s/defn product:db->out :- out.product/Product
  [product :- models/Product]
  {:id          (:product/id product)
   :name        (:product/name product)
   :price       (:product/price product)
   :category    (:product/category product)
   :description (:product/description product)})

(s/defn update-product:wire->internal :- models/Product
  [{:keys [id name price category description]} :- in.product/UpdateProduct]
  {:product/id          id
   :product/name        name
   :product/price       (bigdec price)
   :product/category    category
   :product/description description})
