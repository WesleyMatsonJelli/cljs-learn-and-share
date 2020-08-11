(ns cljs-learn-and-share.home.subs
  (:require [re-frame.core :as re-frame]))

(re-frame/reg-sub
 :loading-posts
 (fn [db _]
   (get-in db [:loading :posts])))

(re-frame/reg-sub
 :posts
 (fn [db _]
   (:posts db)))

(re-frame/reg-sub
 :post-count
 :<- [:posts]
 (fn [posts _]
   (count posts)))
