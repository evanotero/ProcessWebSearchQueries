Process Web Search Queries
==========================
Application that processes a list of urls and, by using a user-entered query,
displays all URLs that match the query in order from the highest score to the lowest.
Data is organized with my own priority queue using a max-heap.  Data for the
index representation of the webpage is organized using my own AVL tree.

The list of urls is scanned from a user created file.  The jsoup Java library is used
to handle the processing of HTML.

Created as a project in CS1102.

Usage
------
`java ProcessQueries <file> [max results]`
