package com.javainsight.cloud;

import com.google.api.client.googleapis.GoogleUrl;
import com.google.api.client.util.Key;

/**
 * @author Yaniv Inbar
 */
public class YouTubeUrl extends GoogleUrl {

  /** Whether to pretty print HTTP requests and responses. */
  private static final boolean PRETTY_PRINT = true;

  static final String ROOT_URL = "https://spreadsheets.google.com/feeds";//"https://gdata.youtube.com/feeds/api";

  @Key
  String author;

  @Key("max-results")
  Integer maxResults = 2;

  YouTubeUrl(String encodedUrl) {
    super(encodedUrl);
    this.alt = "jsonc";
    this.prettyprint = PRETTY_PRINT;
  }

  private static YouTubeUrl root() {
    return new YouTubeUrl(ROOT_URL);
  }

  static YouTubeUrl forVideosFeed() {
    YouTubeUrl result = root();
   // result.getPathParts().add("videos");
    return result;
  }
}
