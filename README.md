# Kalliope
Grails project which serves fonts for websites.

## Motivation
Google Web Fonts and other such services are great, but all too often designers will insist on exotic fonts wihch are simply not "web safe". Arguing the point is fruitless, but using 3rd party conversion tools is a drawn-out (and I felt unnecessary) process.

To that end, I created this project, which uses FontForge to convert any supported font into the various browser font formats required (eot, woff, ttf, svg), and provides a mechanism for using the converted fonts by simply including a URL reference to the font (CSS output is generated, which can then be used either using <link> or @import).
