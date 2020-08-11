(ns cljs-learn-and-share.posts.subs
  (:require [re-frame.core :as re-frame]))

(defn get-post [posts post-id]
  (->> posts
       (filter (comp #{post-id} :id))
       first))

(re-frame/reg-sub
 :post-details
 :<- [:posts]
 (fn [posts [_ post-id]]
   (get-post posts post-id)))

(comment
  (get-post (:posts @re-frame.db/app-db) 1))
