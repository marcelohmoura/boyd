(ns boyd.handler
  (:require [compojure.api.sweet :refer :all]
            [ring.util.http-response :refer :all]
            [boyd.system :refer [system]]
            [com.stuartsierra.component :as component]
            [schema.core :as s]
            [boyd.wire.out.product :as out.product]
            [boyd.wire.in.product :as in.product]
            [boyd.http-in :as http-in]))

(def app
  (let [db-conn (-> (component/start (system)) :database :conn)]
    (api
      {:swagger
       {:ui   "/"
        :spec "/swagger.json"
        :data {:info {:title       "Boyd"
                      :description "A product registration API"}
               :tags [{:name "api", :description "Operations"}]}}}

      (context "/api" []
        :tags ["api"]

        (GET "/lookup-product" []
          :return {:result out.product/Product}
          :query-params [product-name :- s/Str]
          :summary "Lookup for a product by product name"
          (http-in/lookup-product! db-conn product-name))

        (POST "/register" []
          :return out.product/Product
          :body [product in.product/Product]
          :summary "Register a product"
          (http-in/register-product! db-conn product))

        (PUT "/update" []
          :body [product in.product/UpdateProduct]
          :summary "Update a product"
          (http-in/update-product! db-conn product))

        (DELETE "/delete" []
          :body [product in.product/DeleteProduct]
          :summary "Delete a product"
          (http-in/delete-product! db-conn product))))))
