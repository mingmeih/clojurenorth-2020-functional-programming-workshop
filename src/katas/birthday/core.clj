(ns katas.birthday.core
  (:require
   [clojure.data.csv :as csv]
   [clojure.java.io :as io]
   [clojure.string :as string]
   [postal.core :as postal])
  (:import
   (java.time LocalDate)
   (java.time.format DateTimeFormatter)
   (java.time.temporal ChronoUnit)))

(defn ->key
  [header]
  (keyword (string/replace header #"_" "-")))

(defn load-csv
  "Load the csv from the resource file, converting it to a sequence of maps.
  Assumes the first row is a header row that gives the name of each column."
  [file-name]
  (with-open [f (io/reader (io/resource file-name))]
    (let [[header & rows] (csv/read-csv f)
          header (map ->key header)]
      (doall (map (partial zipmap header) rows)))))

(defn parse-birthdate
  "Parse a birthdate string in the form yyyy/MM/dd into a local date"
  [date-str]
  (LocalDate/parse date-str (DateTimeFormatter/ofPattern "yyyy/MM/dd")))

(defn birthday-today?
  "Given a map representing one user, return true if their birthday is
  today, where today is given as an argument"
  [^LocalDate today date-of-birth]
  (string/ends-with?
    date-of-birth
    (.format today (DateTimeFormatter/ofPattern "MM/dd"))))

(defn age
  [date-of-birth today]
  (.between ChronoUnit/YEARS date-of-birth today))

(defn make-email
  [{:keys [name email date-of-birth]}]
  {:from "me@example.com"
   :to email
   :subject "Happy Birthday!"
   :body (str "Happy Birthday " name "! "
              "Wow, you're "
              (age (parse-birthdate date-of-birth)
                   (LocalDate/now))
              " already!")})

(comment
  (load-csv "birthday/employees.csv")
  )

(defn emails-for-day
  [day]
  (->> (load-csv "birthday/employees.csv")
       (filter (partial birthday-today? day))
       (map make-email)))

(defn send-email!
  [msg]
  (postal/send-message
    ;; [TODO] load config from somewhere
    {:host "localhost"
     :user "azurediamond"
     :pass "hunter2"
     :port 2525}
    msg))

(defn greet! []
  (doseq [email (emails-for-day (LocalDate/now))]
    (send-email! email)))
