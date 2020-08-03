(ns cljs-learn-and-share.events
  (:require
   [re-frame.core :as re-frame]
   [cljs-learn-and-share.db :as db]
   [day8.re-frame.tracing :refer-macros [fn-traced]]))

(re-frame/reg-event-db
 ::initialize-db
 (fn-traced [_ _]
            db/default-db))

(re-frame/reg-event-db
 ::edit-name
 (fn-traced [db [_ new-name]]
            (assoc db :name new-name)))

