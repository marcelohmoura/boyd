(ns boyd.wire.db.product)

(def Product [{:db/ident       :product/id
               :db/valueType   :db.type/uuid
               :db/cardinality :db.cardinality/one}

              {:db/ident       :product/name
               :db/valueType   :db.type/string
               :db/cardinality :db.cardinality/many}

              {:db/ident       :product/price
               :db/valueType   :db.type/bigdec
               :db/cardinality :db.cardinality/many}

              {:db/ident       :product/category
               :db/valueType   :db.type/string
               :db/cardinality :db.cardinality/many}

              {:db/ident       :product/description
               :db/valueType   :db.type/string
               :db/cardinality :db.cardinality/many}])
