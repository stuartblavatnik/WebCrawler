WebCrawler

This application is a simple webcrawler that takes as an input a well formed URL and visits all of the pages within
the domain.  It will gather all of the internal links to other pages, links to external URL's and links to static
content.  It's output will be a simple structured site map containing all of the links found.


Requirements:
1) Java 1.8
2) Maven 3.5.2

This project is a maven project and supports the following standard commands to do the following:

build, run tests -- maven clean package

To run on the command line
within the project\target directory

java -jar webcrawler-1.0-SNAPSHOT-jar-with-dependencies.jar [url]

The output is in the form of:

Parent Page
    Children

Thoughts / Improvements

1) I decided to use Jsoup for parsing the webpages.  My thought on this goes back to something I learned at JP Morgan
when I was doing some training for becoming an Application Security Champion.  The training mentioned that limiting
the amount of rolling you own code for standard solutions is usually a dangerous practice because you most likely will
forget something or implement a solution in the wrong way.  Common libraries have been used for years and (hopefully)
well tested.

2) A point of item 1 which I did not really care for in my solution was the definition of linkTypes in the WebParser
class.  I would have wanted Jsoup to know about all links that could be in a webpage.

3) I also made use of a URL validator class in apache commons, although the latest version of the library has a runtime
error so I had to use an older version.

4) One tradeoff that I made was the use of in-memory (HashTables) vs. database.  This was done since this was a
demo project.  For robustness I would have definitely used a database.

5) If this crawler was meant to work on multiple sites, I would have gone into the fun world of threading.

6) With more time I could have added a more heirarchical output

--- Version 1.1 Updates Oct 27, 2018

Refactored the code to not use hashes and instead a more object oriented solution with a Site object that stores the
parent child relationship between pages.

Additionally, the processor treats https:// and http:// prefixes as equals.

Added the ignoring of anchor links within pages (i.e. urls containing the hash character)

Added an integration test.  To launch mvn clean install -DskipTests=false

Made the code a bit more interactive as it's processing sites by displaying the current page.
