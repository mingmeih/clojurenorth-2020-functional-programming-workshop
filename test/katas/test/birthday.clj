(ns katas.test.birthday
  (:require
    [clojure.test :refer :all]
    [katas.birthday.core :as birthday])
  (:import
   (java.time LocalDate)))

(deftest ->key
  (testing "turn strings into keys for the map"
    (is (= :foo (birthday/->key "foo")))
    (is (= :foo-bar (birthday/->key "foo_bar")))
    (is (= :foo-bar (birthday/->key "foo-bar")))
    (is (= :foo-bar-baz (birthday/->key "foo_bar_baz")))
    (is (= :foo-bar-baz (birthday/->key "foo-bar_baz")))))

(deftest birthday-today?
  (testing "checks if birthday is today"
    (is (true? (birthday/birthday-today?
                 (LocalDate/of 1848 3 6)
                 "1383/03/06")))
    (is (true? (birthday/birthday-today?
                 (LocalDate/of 1848 3 6)
                 "1970/03/06")))
    (is (false?
          (birthday/birthday-today?
            (LocalDate/of 1848 3 6)
            "1383/06/03")))))

(deftest age
  (testing "Get the age in years"
    (is (= 30 (birthday/age (LocalDate/of 1989 7 22)
                            (LocalDate/of 2020 6 23))))
    (is (= -30 (birthday/age (LocalDate/of 2020 6 23)
                             (LocalDate/of 1989 7 22))))

    (is (= 100 (birthday/age (LocalDate/of 1920 6 23)
                             (LocalDate/of 2020 6 23) )))))
