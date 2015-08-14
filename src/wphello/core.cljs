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
     :posts []}))

(defn getPostHtml []
  (let [id (:post-id @app-state)
        post (first (filter #(= (% "id") id) (:posts @app-state)))
        post (or post
                 (first (:posts @app-state))
                 {"title" {"rendered" "not loaded"}
                  "content" {"rendered" ""}})]
    [:div
     [:h1 ((post "title") "rendered")]
     [:div {"dangerouslySetInnerHTML" #js  {:__html  ((post "content") "rendered")}}] ]
    ))
(defn post-line [o]
  [:li
   [:a
    {:on-click (fn []
                 (.foundation (js/$ ".off-canvas-wrap") "offcanvas" "hide" "move-right")
                 (swap! app-state assoc :post-id (o "id")))}
    (str ((o "title") "rendered"))]])
(defn front-page []
  (js/console.log (clj->js (:post-id @app-state)))
  [:div.row
   [:div.large-12.columns
    [:div.off-canvas-wrap {:data-offcanvas "data-offcanvas"}
     [:div.inner-wrap {:style {:min-height js/window.innerHeight}}
      [:nav.tab-bar
       [:section.left-small [:a.left-off-canvas-toggle.menu-icon [:span]]]
       [:section.right.tab-bar-section [:h1.title "solsort.com"]]]
      [:aside.left-off-canvas-menu
       (into [:ul.off-canvas-list [:li [:label "Posts"]]] (map post-line (:posts @app-state)))]
      [:section.main-section
       [:div.row
        [:div.large-12.columns
         (getPostHtml)
         ]
        ]
       ]
      ]]]])

(defn main []
  (case (:state @app-state)
    :front-page (front-page))
  )

(defn getPosts []
  (go
    (loop [page 1
           acc []]
      (let [data (js->clj (<! (ajaxJSON (str "http://solsort.com/wp-json/wp/v2/posts?page=" page))))]
        (if-not (empty? data)
          (recur (inc page) (into acc data))
          acc)))))
(defn updatePostsState []
  (go
    (swap! app-state assoc :posts (<! (getPosts)))))
(updatePostsState)
(js/console.log (clj->js @app-state))

(reagent/render-component
  [(with-meta
     main
     { :component-did-mount #(.foundation (js/$ js/document))})]
  (.getElementById js/document "app"))
(defn on-js-reload [])
