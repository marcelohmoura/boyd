(ns boyd.http-in
  (:require [schema.core :as s]
            [ring.util.http-response :as http-response]
            [boyd.wire.out.product :as out.product]
            [boyd.wire.in.product :as in.product]))

(s/defn register-product! :- out.product/Product
  [product :- in.product/Product]
  (http-response/ok "return product registration result..."))
