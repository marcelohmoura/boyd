(ns boyd.core-test
  (:require [cheshire.core :as cheshire]
            [clojure.test :refer :all]
            [boyd.handler :refer :all]
            [ring.mock.request :as mock]
            [boyd.helpers :as helpers]))

(defn parse-body [body]
  (cheshire/parse-string (slurp body) true))

(deftest product-register
  (with-redefs [helpers/->uuid (fn [] #uuid"2f7f1ec2-5ada-4e63-a595-9ab83c839e8b")]

    (testing "Should register a valid product"
      (let [product {:name        "Laptop"
                     :price       1958.58
                     :category    "Laptops"
                     :description "A Good Laptop"}
            response (app (-> (mock/request :post "/api/register")
                              (mock/content-type "application/json")
                              (mock/body (cheshire/generate-string product))))
            body (parse-body (:body response))]
        (is (= (:status response) 200))
        (is (= body
               (assoc product :id "2f7f1ec2-5ada-4e63-a595-9ab83c839e8b")))))))

(deftest get-product
  (testing "Get a product by name"
    (let [response (app (-> (mock/request :get "/api/lookup-product?product-name=Laptop")))
          body (parse-body (:body response))]
      (is (= (:status response) 200))
      (is (= (:result body)
             {:id          "2f7f1ec2-5ada-4e63-a595-9ab83c839e8b"
              :name        "Laptop"
              :price       1958.58
              :category    "Laptops"
              :description "A Good Laptop"}))))

  (testing "Should return 404 when looking for a non existent product"
    (let [response (app (-> (mock/request :get "/api/lookup-product?product-name=fridge")))]
      (is (= (:status response) 404)))))

(deftest update-product
  (testing "Should update a registered product"
    (let [product {:id #uuid"2f7f1ec2-5ada-4e63-a595-9ab83c839e8b"
                   :name        "Laptop"
                   :price       1958.58
                   :category    "Laptops"
                   :description "A Very good Laptop"}
          response (app (-> (mock/request :put "/api/update")
                            (mock/content-type "application/json")
                            (mock/body (cheshire/generate-string product))))]
      (is (= (:status response) 200))))

  (testing "Cannot update a non existent product"
    (let [product {:id #uuid"a95e183c-af33-48eb-8212-05ed75f132c7"
                   :name        "Smartphone"
                   :price       899.0
                   :category    "Smartphones"
                   :description "New 2024 model!"}
          response (app (-> (mock/request :put "/api/update")
                            (mock/content-type "application/json")
                            (mock/body (cheshire/generate-string product))))]
      (is (= (:status response) 404)))))
