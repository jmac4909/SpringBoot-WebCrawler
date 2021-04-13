# SpringBoot-WebCrawler
Spring Boot demo of a web crawler parsing websites, storing content in DB, allowing for querying of pages.

The general idea behind this project was to have a service parse a given URL for its text contents.
Saving the parsed contents in a database of (word, frequency, site) tuples.
Then allowing queries to be made on the data to retrieve the relevant pages.

The code I wrote is located under src/main/java/com/jmac/demo/controllers.

JMTokenizer:
  Tokenizing object class that can take in a string and output a list of strings. Separating words on whitespace, removing punctuation / special characters, and lowercasing everything.
  The tokenizing object can take in a list of stop words (generally from an external file) that will be removed from the final output. 
  This is useful for removing very common words such as 'a' or 'div', words that would help very little based on what we wanted to query.
  
UrlParserController:
  This class handles my one spring boot URL. Going to http://www.localhost.com:8080/parse and passing it a parameter with " ?urlString=https://google.com " will activate the parser.
  The controller mimics a browser's user agent and attempts to form a connection with the passed URL. Should the URL be invalid or the site unreachable, the parser will return that in its string. 
  Once the controller successfully GETS the content string of the passed-in URL, it will pass it through to the tokenizer. Once tokenized the words of the list are counted and saved in a map. 
  This map will be saved in a database along with the site URL that produced it.
  
WordEntity:
  This class is an example of the word entity to be stored in an H2 database. 
  If I had more time I would have liked to store parsed sites and their word frequencies into a DB. 
  Then have a table for visualizing what words are most common on each site.
  
  
Overall this was quite fun to put together as a quick demonstration. I think an addition of visual tables, as well as easier URL input would greatly improve the look/feel.

Future additions:
  Adding a text field to input the URL param.
  Adding a table to visualize stored frequencies and words.
  Customizable stop word removal, have a separate way of inputting custom stop words.
  Search field for retrieving sites related to some query. 
