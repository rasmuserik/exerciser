[![Build Status](https://travis-ci.org/rasmuserik/wphello.svg?branch=master)](https://travis-ci.org/rasmuserik/wphello)
[![Dependencies Status](http://jarkeeper.com/rasmuserik/wphello/status.png)](http://jarkeeper.com/rasmuserik/wphello)

# wp-front

- TODO: setup dev environment, http://astashov.github.io/blog/2014/07/30/perfect-clojurescript-development-environment-with-vim/
- setup wordpress with wp-api
- extract posts, and visualise them
- rename project
 
# Start

To get an interactive development environment run:

    lein figwheel

and open your browser at [localhost:3449](http://localhost:3449/).
This will auto compile and send all changes to the browser without the
need to reload. After the compilation process is complete, you will
get a Browser Connected REPL. An easy way to try it is:

    (js/alert "Am I connected?")

and you should see an alert in the browser window.

`nrepl` is on port 7888.

To clean all compiled files:

    lein clean

To create a production build run:

    lein cljsbuild once min

And open your browser in `resources/public/index.html`. You will not
get live reloading, nor a REPL. 
