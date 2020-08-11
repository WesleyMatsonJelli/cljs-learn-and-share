(ns cljs-learn-and-share.router
  (:require [reitit.core :as reitit]
            [re-frame.core :as re-frame]
            [reitit.frontend :as reitit-fe]
            [reitit.frontend.easy :as reitit-feasy]
            [cljs-learn-and-share.home.views :refer [home-page]]
            [cljs-learn-and-share.about.views :refer [about-page]]))

(re-frame/reg-sub
 :routes/current-route
 (fn [db _]
   (:routes/current-route db)))

(re-frame/reg-event-db
 :routes/navigated
 (fn [db [_ new-match]]
   (assoc db :routes/current-route new-match)))

(defn- on-navigate [new-match]
  (when new-match
    (re-frame/dispatch [:routes/navigated new-match])))

(def routes
  ["/"
   ["" {:name :routes/home
        :view home-page
        :link-text "Home"}]
   ["about" {:name :routes/about
             :view about-page
             :link-text "Resources"}]])
   

(def router
  (reitit-fe/router routes))

(defn init-router! []
  (println "Initializing routes")
  (reitit-feasy/start! router on-navigate {:use-fragment true}))
           
(defn router-component []
  (let [current-route @(re-frame/subscribe [:routes/current-route])]
    (println current-route)
    [:<>
     [:nav.navbar
      [:div.navbar-brand>div.navbar-item [:a {:href (reitit-feasy/href :routes/home)} "Learn and Share"]]
      [:div.navbar-menu
       (for [route-name (reitit/route-names router)
             :let       [route (reitit/match-by-name router route-name)
                         text (-> route :data :link-text)]]
         ^{:key route-name}
         [:a.navbar-item {:href (reitit-feasy/href route-name)
                          :class (when (= route-name (-> current-route :data :name))
                                   "is-active")}
          text])]]
     (if current-route
       [(-> current-route :data :view)]
       [:div "Not Found"])]))
  
