# Simple Restlet Wrapper Library

A hazzle-free library for making basic HTTP requests. For instance, to get the HTML of this projects frontpage as a String, you could do this:
    
    try {
        String html = Http.get("http://github.com/josteinaj/simple-restlet/").asText();
    } catch (HttpException e) {
        // ...
    }

Below is the basic API.

## Class no.nlb.http.Http

### Fields

* `static boolean debug` - Set to true to enable debugging

### Methods

* `static HttpResponse get(String url)` - Send a GET request.
* `static HttpResponse post(String url, Document xml)` - POST an XML document.
* `static HttpResponse post(String url, Map<String,File> parts)` - POST a multipart request.
* `static HttpResponse post(String url, String text)` - POST a string.
* `static String url(String url, Map<String,String> parameters)` - Add parameters to a URL.

## Class no.nlb.http.HttpResponse

### Fields

* `String contentType`
* `int status`
* `String statusDescription`
* `String statusName`

### Methods

* `InputStream asStream()` - Returns the response body as a InputStream.
* `String asText()` - Returns the response body as a String.
* `Document asXml()` - Returns the response body as an XML Document.
