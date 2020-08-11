(ns cljs-learn-and-share.home.views
  (:require [re-frame.core :as re-frame]
            [reitit.frontend.easy :as reitit-feasy]))

(defn- post-item [post]
  [:div.card
   [:div.card-header>p.title
    [:a {:href (reitit-feasy/href :routes/post-details {:id (:id post)})}
     (:title post)]]
   [:div.card-content>div.content.is-info (:body post)]])

(defn home-page []
  [:div.container
   [:h2.title "Home"]
   [:h3.title (str "Post Count: " @(re-frame/subscribe [:post-count]))]
   (if @(re-frame/subscribe [:loading-posts])
     [:div.box>progress.progress.is-small.is-info]
     (for [post @(re-frame/subscribe [:posts])]
       ^{:key (:id post)}
       [post-item post]))])
 
