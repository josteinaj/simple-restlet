# Simple Restlet Wrapper Library

A hazzle-free library for making basic HTTP requests. Below is the basic API.

## Class no.nlb.http.Http

### Fields

| Modifier and Type | Field | Description |
| `static boolean` | *`debug`* | Set to true to enable debugging |

### Methods

| Modifier and Type | Method | Description |
| `static HttpResponse` | *`get(String url)`* | Send a GET request. |
| `static HttpResponse` | *`post(String url, Document xml)`* | POST an XML document. |
| `static HttpResponse` | *`post(String url, Map<String,File> parts)`* | POST a multipart request. |
| `static HttpResponse` | *`post`*(String url, String text)`* | POST a string. |
| `static String` | *`url(String url, Map&lt;String,String&gt; parameters)`* | Add parameters to a URL. |

## Class no.nlb.http.HttpResponse

### Fields

| Modifier and Type | Field |
| `String` | *`contentType`* |
| `int` | *`status`* |
| `String` | *`statusDescription`* |
| `String` | *`statusName`* |

### Methods

| Modifier and Type | Method | Description |
| `InputStream` | *`asStream()`* | Returns the response body as a InputStream. |
| `String` | *`asText()`* | Returns the response body as a String. |
| `Document` | *`asXml()`* | Returns the response body as an XML Document. |