(ns cljs-learn-and-share.posts.views
  (:require [cljs-learn-and-share.posts.subs]
            [re-frame.core :as re-frame]))

(defn post-details-page []
  [:div.content
   [:pre (.stringify js/JSON (clj->js @(re-frame/subscribe [:posts 1]))
                     nil " ")]])
