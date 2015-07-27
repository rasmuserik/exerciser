[![Build Status](https://travis-ci.org/rasmuserik/exerciser.svg?branch=master)](https://travis-ci.org/rasmuserik/exerciser)
[![Dependencies Status](http://jarkeeper.com/rasmuserik/exerciser/status.png)](http://jarkeeper.com/rasmuserik/exerciser)

# Exerciser - exercise app

## Backlog

- actual exercise ticker
  - current exercise
    - tick
  - break with list of all exercises
- start screen
  - liste of exercise collections
- edit exercise
  - title
  - name/tags
- edit exercise collection
  - break between exercises
  - time per exercise
  - time per break
  - list of exercise names/tags


## Overview

FIXME: Write a paragraph about the library/project and highlight its goals.

# Setup

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
