**Categorizer App**

The application downloads websites text and categorizes it according to the provided categories.  
URLs list and categories list are provided via text files.
You are required to provide a path to the both files as program arguments.  
Example URLs list and categories list files are bundled - `urls` and `categories.json` files respectively.

**Run instructions:** you need to have Java 18+ installed, then run:

`./gradlew run --args="PATH_TO_URLS_FILE PATH_TO_CATEGORIES_FILE"`

where `PATH_TO_URLS_FILE` is the path to the text file containing URLs and `PATH_TO_CATEGORIES_FILE` is the path to the
categories file.

For a **quick start** you are recommended to use bundled files mentioned above:

`./gradlew run --args="urls categories.json"  `

Then you should see something similar to this:

```
[main] INFO com.testtask.Main - URL: https://www.imdb.com/find?q=star+wars&ref_=nv_sr_sm, Categories: Star Wars
[main] INFO com.testtask.Main - URL: http://www.starwars.com, Categories: Star Wars
[main] INFO com.testtask.Main - URL: https://edition.cnn.com/sport, Categories: Basketball
```

Enjoy! :)


