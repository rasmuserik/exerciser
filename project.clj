(defproject exerciser "0.1.0-SNAPSHOT"
  :description "FIXME: write this!"
  :url "https://solsort.com/"
  :license {:name "none-yet" :url "http://TODO"}

  :dependencies
  [[org.clojure/clojure "1.7.0"]
   [org.clojure/clojurescript "0.0-3308"]
   [org.clojure/core.async "0.1.346.0-17112a-alpha"]
   [cljsjs/pouchdb "3.5.0-0"]
   [reagent "0.5.0"]]

  :plugins
  [[lein-cljsbuild "1.0.6"]
   [lein-figwheel "0.3.7"]
   [lein-kibit "0.1.2"]
   [lein-bikeshed "0.2.0"]
   [cider/cider-nrepl "0.9.1"] 
   ]

  :source-paths ["src"]

  :clean-targets ^{:protect false} 
  ["resources/public/js/compiled" "target"
   "figwheel_server.log"
   ]

  :cljsbuild 
  { :builds 
   [{:id "dev"
     :source-paths ["src"]

     :figwheel { :on-jsload "exerciser.core/on-js-reload" }

     :compiler {:main exerciser.core
                :asset-path "js/compiled/out"
                :output-to "resources/public/js/compiled/main.js"
                :output-dir "resources/public/js/compiled/out"
                :source-map-timestamp true }}
    {:id "dist"
     :source-paths ["src"]
     :compiler {:output-to "resources/public/js/compiled/main.js"
                :main exerciser.core
                :optimizations :advanced
                :pretty-print false}}]}

  :figwheel { :nrepl-port 7888})
