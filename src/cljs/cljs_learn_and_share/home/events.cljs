(ns cljs-learn-and-share.home.events
  (:require [re-frame.core :as re-frame]
            [day8.re-frame.http-fx]
            [ajax.core :refer [json-request-format json-response-format]]))

(re-frame/reg-event-fx
 :get-posts
 (fn [{:keys [db]} _]
   (if (:posts db)
     {}
     {:http-xhrio
      {:method :get
       :uri "https://jsonplaceholder.typicode.com/posts"
       :on-success [:posts-arrived]
       :response-format (json-response-format {:keywords? true})}
      :db (assoc-in db [:loading :posts] true)})))

(re-frame/reg-event-db
 :posts-arrived
 (fn [db [_ posts]]
   (-> db
       (assoc :posts posts)
       (assoc-in [:loading :posts] false))))
