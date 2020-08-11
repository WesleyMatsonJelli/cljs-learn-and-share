(ns cljs-learn-and-share.router
  (:require [reitit.core :as reitit]
            [reagent.core :as reagent]
            [re-frame.core :as re-frame]
            [reitit.frontend :as reitit-fe]
            [reitit.frontend.easy :as reitit-feasy]
            [cljs-learn-and-share.home.events]
            [cljs-learn-and-share.home.subs]
            [cljs-learn-and-share.home.views :refer [home-page]]
            [cljs-learn-and-share.about.views :refer [about-page]]))

(re-frame/reg-sub
 :routes/current-route
 (fn [db _]
   (:routes/current-route db)))

(re-frame/reg-event-fx
 :routes/navigated
 (fn [cofx [_ new-match]]
   (let [arrival-effects (-> new-match :data :on-arrive)]
     (cond-> {:db (assoc (:db cofx)
                         :routes/current-route new-match)}
       arrival-effects
       (assoc :dispatch-n arrival-effects)))))

(defn- on-navigate [new-match]
  (when new-match
    (re-frame/dispatch [:routes/navigated new-match])))

(def routes
  ["/"
   ["" {:name :routes/home
        :view home-page
        :link-text "Home"
        :on-arrive [[:get-posts]]}]
   ["about" {:name :routes/about
             :view about-page
             :link-text "Resources"}]
   ["posts/:id" {:name :routes/post-details
                 :navbar-excluded? true
                 :view (fn [] [:div.content "Post Details"])}]])
                         
(def router
  (reitit-fe/router routes))

(defn init-router! []
  (println "Initializing routes")
  (reitit-feasy/start! router on-navigate {:use-fragment true}))

(defn navbar []
  (reagent/with-let [menu-active? (reagent/atom false)]
    (let [current-route @(re-frame/subscribe [:routes/current-route])]
      [:nav.navbar
       [:div.navbar-brand
        [:span.navbar-item "Learn and Share"]
        [:a.navbar-burger.burger
         {:on-click (fn [_] (swap! menu-active? not))
          :class (when @menu-active? "is-active")}
         [:span {:aria-hidden true}]
         [:span {:aria-hidden true}]
         [:span {:aria-hidden true}]]]
       [:div.navbar-menu {:class (when @menu-active? "is-active")}
        [:div.navbar-start
         (for [route-name (reitit/route-names router)
               :let       [route (reitit/match-by-name router route-name)
                           text (-> route :data :link-text)]
               :when (not (-> route :data :navbar-excluded?))]
           ^{:key route-name}
           [:a.navbar-item {:href (reitit-feasy/href route-name)
                            :class (when (= route-name (-> current-route :data :name))
                                     "is-active")}
            text])]]])))
       
(defn router-component []
  (let [current-route @(re-frame/subscribe [:routes/current-route])]
    [:div
     [:header [navbar]]
     (if current-route
       [(-> current-route :data :view)]
       [:div "Not Found"])]))
  
