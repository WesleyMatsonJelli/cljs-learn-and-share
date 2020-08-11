(ns cljs-learn-and-share.core
  (:require
   [reagent.dom :as rdom]
   [re-frame.core :as re-frame]
   [cljs-learn-and-share.db :as db]
   [day8.re-frame.tracing :refer-macros [fn-traced]]
   [cljs-learn-and-share.router :as router]
   [cljs-learn-and-share.config :as config]))

(re-frame/reg-event-db
 ::initialize-db
 (fn-traced [_ _]
            db/default-db))

(defn dev-setup []
  (when config/debug?
    (println "dev mode")))

(defn main-panel []
  [router/router-component])

(defn ^:dev/after-load mount-root []
  (re-frame/clear-subscription-cache!)
  (router/init-router!)
  (let [root-el (.getElementById js/document "app")]
    (rdom/unmount-component-at-node root-el)
    (rdom/render [main-panel] root-el)))

(defn init []
  (re-frame/dispatch-sync [::initialize-db])
  (dev-setup)
  (mount-root))
