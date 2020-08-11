(ns cljs-learn-and-share.about.views)

(defn about-page []
  [:div.container
   [:div.tile.is-ancestor
    [:div.tile.is-vertical
     [:div.tile
      [:div.tile.is-parent.is-6
       [:article.tile.is-child.notification.is-info
        [:p.title "re-frame"]
        [:p.subtitle "A state management library"]
        [:div.content
         [:ul
          [:li>a {:href "http://day8.github.io/re-frame/"} "Docs"]
          [:li>a {:href "https://blog.klipse.tech/clojure/2019/02/17/reframe-tutorial.html"} "Tutorial/Demo"]]]]]
      [:div.tile.is-parent.is-6>article.tile.is-child.notification.is-info
       [:p.title "bulma"]
       [:p.subtitle "A framework-agnostic CSS library"]
       [:div.content [:a {:href "https://bulma.io/documentation/"} "Docs"]]]]
     [:div.tile.is-parent
      [:div.tile.is-child.is-12.notification.is-info
       [:p.title "Other Links"]
       [:div.content
        [:ul
         [:li>a {:href "https://medium.com/dailyjs/a-realworld-comparison-of-front-end-frameworks-2020-4e50655fe4c1"}
          "Real World App framework comparison"]
         [:li [:a {:href "https://jsonplaceholder.typicode.com/guide.html"}
               "JSON Placeholder API"]]]]]]]]])
          
