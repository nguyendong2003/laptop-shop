<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

        <!DOCTYPE html>
        <html lang="en">

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Document</title>
            <!-- Latest compiled and minified CSS -->
            <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">

            <!-- Latest compiled JavaScript -->
            <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
            <!-- jquery -->
            <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>

            <link rel="stylesheet" href="/css/demo.css">
        </head>

        <body>
            <h1>Hello from jsp</h1>
            <h1>
                <%= request.getAttribute("m10") %>
            </h1>
            <h1>
                <%= request.getAttribute("neymar") %>
            </h1>
            <!-- JSTL -->
            <h1>
                <c:out value="${m10}" />
            </h1>
            <h1>
                <c:out value="${neymar}" />
            </h1>

            <!-- JSP -->
            <h1>
                ${m10}
            </h1>
            <h1>
                ${neymar}
            </h1>

            <button>Submit</button>

        </body>

        </html>