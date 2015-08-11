(ns ^:figwheel-always exerciser.core
  (:require
    [reagent.core :as reagent :refer []]))

(enable-console-print!)

(println "Edits to this text should show up in your developer console.")

;; define your app data so that it doesn't get over-written on reload

(defonce app-state
  (reagent/atom
    {
     :state :front-page
     :workouts
     {
      "Basic 7 minutes"
      {:exercises
       ["Jumping Jacks"
        "Wall Sit"
        "Push Ups"
        "Abdominal Crunches"
        "Step-up Onto Chair"
        "Squat"
        "Triceps Dips on Chair"
        "Plank"
        "High Knees Running in Place"
        "Lunge"
        "Push Up and Rotation"
        "Side Plank"]
       :exercise-time 30
       :break-time 10}
      "Custom 7 minutes"
      {:exercises
       ["Jumping Jacks"
        "Wall Sit"
        "Abdominal Crunches"
        "Step-up Onto Chair"
        "Squat"
        "Triceps Dips on Chair"
        "Plank"
        "High Knees Running in Place"
        "Lunge"
        "Burpee"
        "Yoga boat"
        "Pull ups"
        ]
       :exercise-time 30
       :break-time 10}
      }
     }
    ))

(defn workout-title [[title desc]]
  (print title)
  [:li
   {:on-click (fn []
                (swap! app-state assoc-in [:state] :workout)
                (swap! app-state assoc-in [:workout] title)
                )}
   title])

(defn front-page []
  [:div
   [:h1 "Workouts"]
   (into [:ul]
         (map workout-title (:workouts @app-state)))])

(defn workout []
  [:div
   [:h1 (:workout @app-state)]
   [:button {:on-click #(swap! app-state assoc-in [:state] :front-page)} "back"]
   ]
  )

(defn main []
  (case (:state @app-state)
    :front-page (front-page)
    :workout (workout))
  )

(print "hi")

(reagent/render-component [main]
                          (.getElementById js/document "app"))

(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
  )
