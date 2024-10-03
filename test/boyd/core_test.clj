(ns boyd.core-test
  (:require [cheshire.core :as cheshire]
            [clojure.test :refer :all]
            [boyd.handler :refer :all]
            [ring.mock.request :as mock]
            [boyd.helpers :as helpers]))

(defn parse-body [body]
  (cheshire/parse-string (slurp body) true))

(deftest handler

  #_(testing "Test GET request to /hello?name={a-name} returns expected response"
      (let [response (app (-> (mock/request :get "/api/plus?x=1&y=2")))
            body (parse-body (:body response))]
        (is (= (:status response) 200))
        (is (= (:result body) 3))))

  (with-redefs [helpers/->uuid (fn [] #uuid"2f7f1ec2-5ada-4e63-a595-9ab83c839e8b")]
    (testing "Should register a valid product"
      (let [product {:name        "Laptop Lenovo Ideapad"
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
