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

      (GET "/lookup-product" []
        :return {:result out.product/Product}
        :query-params [product-name :- s/Str]
        :summary "Lookup for a product by product name"
        (http-in/lookup-product! product-name))

      (POST "/register" []
        :return out.product/Product
        :body [product in.product/Product]
        :summary "Register a product"
        (http-in/register-product! product))

      (PUT "/update" []
        :body [product in.product/UpdateProduct]
        :summary "Update a product"
        (http-in/update-product! product))

      (DELETE "/delete" []
        :body [product in.product/DeleteProduct]
        :summary "Delete a product"
        (http-in/delete-product! product)))))
