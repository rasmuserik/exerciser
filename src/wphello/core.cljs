(ns ^:figwheel-always wphello.core
  (:require-macros
    [cljs.core.async.macros :refer  [go alt!]])

  (:require
    [goog.net.XhrIo]
    [reagent.core :as reagent :refer []]
    [cljs.core.async.impl.channels :refer [ManyToManyChannel]]
    [cljs.core.async :refer [>! <! chan put! take! timeout close!]]))

(enable-console-print!)


;; http-client/AJAX
(def -unique-id-counter (atom 0))
(defn unique-id [] (str "id" (swap! -unique-id-counter inc)))
(defn ajax [url & {:keys [post-data CORS jsonp]}]
  (let [c (chan)
        req (js/XMLHttpRequest.)]
    (.open req (if post-data "POST" "GET") url true)
    (when CORS (aset req "withCredentials" true))
    (aset req "onreadystatechange"
          (fn []
            (when (= (aget req "readyState") (or (aget req "DONE") 4))
              (let [text (aget req "responseText")]
                (if text
                  (put! c text)
                  (close! c))))))
    (.send req)
    c))


(defn ajaxJSON 
  "Do an ajax request and return the result as JSON"
  [url]
  (let [c (chan)]
        (js/console.log "here1" url)
    (goog.net.XhrIo/send 
      url
      (fn [o]
        (js/console.log "here" o)
        (when
          (and o (.-target o))
          (put! c (.getResponseJson (.-target o))))
        (close! c)))
      c))

(defonce app-state
  (reagent/atom
    {:state :front-page 
     :json []}))

(defn front-page []
  [:div
   [:h1 "hello"] 
   [:pre (str (:json @app-state))]])

(defn main []
  (case (:state @app-state)
    :front-page (front-page))
  )

(go
  (print  (<! (ajaxJSON "http://localhost/wp-json/wp/v2/posts?page=2")))
  )
(goog.net.XhrIo/send
  "http://localhost/wp-json/wp/v2/posts?page=2"
  #(js/console.log "xhr" (.getResponseJson (.-target  %)))

  )

(reagent/render-component [main] (.getElementById js/document "app"))
(defn on-js-reload [])
