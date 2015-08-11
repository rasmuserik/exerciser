(ns ^:figwheel-always exerciser.core
  (:require
    [reagent.core :as reagent :refer []]))

(enable-console-print!)

(defonce app-state
  (reagent/atom
    { :state :front-page
     }))

(defn front-page []
  [:div
   [:h1 "Hello"]])

(defn main []
  (case (:state @app-state)
    :front-page (front-page)
    ))


(reagent/render-component [main] (.getElementById js/document "app"))
