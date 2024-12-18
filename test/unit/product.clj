(ns unit.product
  (:require [boyd.helpers :as helpers]
            [clojure.test :refer :all]
            [boyd.adapters :as adapters]))

(def uuid #uuid"2f7f1ec2-5ada-4e63-a595-9ab83c839e8b")

(def product
  {:name        "Lenovo Ideapad"
   :price       1858.85
   :category    :laptops
   :description "A Intel core i3 10th, 4GB and 128GB SSD"})

(deftest product:wire->db
  (with-redefs [helpers/->uuid (fn [] uuid)]
    (testing "Given a product should return a internal product model"
      (is (= {:product/id          uuid
              :product/name        "Lenovo Ideapad"
              :product/price       1858.85M
              :product/category    :laptops
              :product/description "A Intel core i3 10th, 4GB and 128GB SSD"}
             (adapters/product:wire->internal product))))))

(deftest product:db->out
  (testing "Given a product should return a internal product model"
    (is (= (assoc product :id uuid)
           (adapters/product:db->out {:product/id          uuid
                                      :product/name        "Lenovo Ideapad"
                                      :product/price       1858.85
                                      :product/category    :laptops
                                      :product/description "A Intel core i3 10th, 4GB and 128GB SSD"})))))

(deftest update-product:wire->internal
  (testing "Given a product to update should return a internal product model"
    (is (= {:db/id               17592186045418
            :product/id          uuid
            :product/name        "Lenovo Ideapad"
            :product/price       1858.85M
            :product/category    :laptops
            :product/description "A Intel core i3 10th, 4GB and 256GB SSD"}
           (adapters/update-product:wire->internal 17592186045418
                                                   {:id          uuid
                                                    :name        "Lenovo Ideapad"
                                                    :price       1858.85M
                                                    :category    :laptops
                                                    :description "A Intel core i3 10th, 4GB and 256GB SSD"})))))
