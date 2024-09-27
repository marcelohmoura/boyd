(ns boyd.handler
  (:require [compojure.api.sweet :refer :all]
            [ring.util.http-response :refer :all]
            [schema.core :as s]
            [boyd.wire.out.product :as out.product]
            [boyd.wire.in.product :as in.product]
            [boyd.http-in :as http-in]))

(s/defschema Pizza
  {:name s/Str
   (s/optional-key :description) s/Str
   :size (s/enum :L :M :S)
   :origin {:country (s/enum :FI :PO)
            :city s/Str}})

(def app
  (api
    {:swagger
     {:ui "/"
      :spec "/swagger.json"
      :data {:info {:title "Boyd"
                    :description "A product registration API"}
             :tags [{:name "api", :description "Operations"}]}}}

    (context "/api" []
      :tags ["api"]

      (GET "/plus" []
        :return {:result Long}
        :query-params [x :- Long, y :- Long]
        :summary "adds two numbers together"
        (ok {:result (+ x y)}))

      (POST "/register" []
        :return out.product/Product
        :body [product in.product/Product]
        :summary "echoes a Pizza"
        (http-in/register-product! product)))))
