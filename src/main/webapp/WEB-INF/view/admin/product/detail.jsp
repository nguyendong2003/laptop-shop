<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
            <!DOCTYPE html>
            <html lang="en">

            <head>
                <meta charset="utf-8" />
                <meta http-equiv="X-UA-Compatible" content="IE=edge" />
                <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
                <meta name="description" content="LaptopShop project" />
                <meta name="author" content="" />
                <title>Product Detail</title>
                <link href="/css/styles.css" rel="stylesheet" />
                <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
                <script>
                    $(document).ready(() => {
                        // Load original image
                        const originalImage = "${product.image}";
                        if (originalImage) {
                            const imageURL = "/images/product/" + originalImage;
                            $("#productPreview").attr("src", imageURL);
                            $("#productPreview").css({ "display": "block" });
                        }
                    });
                </script>
                <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
            </head>

            <body class="sb-nav-fixed">
                <jsp:include page="../layout/header.jsp" />
                <div id="layoutSidenav">
                    <jsp:include page="../layout/sidebar.jsp" />
                    <div id="layoutSidenav_content">
                        <main>
                            <div class="container-fluid px-4">
                                <h1 class="mt-4">Manage products</h1>
                                <ol class="breadcrumb mb-4">
                                    <li class="breadcrumb-item"><a href="/admin"> Dashboard</a></li>
                                    <li class="breadcrumb-item active">Product</li>
                                </ol>

                                <div class="mt-5">
                                    <div class="row">
                                        <div class="col-12 mx-auto">
                                            <div class="d-flex justify-content-between">
                                                <h3>Product detail with ID = ${product.id}</h3>
                                            </div>

                                            <hr />

                                            <div class="card">
                                                <img id="productPreview" class="card-img-top" alt="Card image cap"
                                                    style="width: 300px; height: 300px; object-fit: cover; display: none;" />

                                                <div class="card-header">
                                                    Product information
                                                </div>
                                                <ul class="list-group list-group-flush">
                                                    <li class="list-group-item">ID: ${product.id}</li>
                                                    <li class="list-group-item">Name: ${product.name}</li>
                                                    <li class="list-group-item">Detail description:
                                                        ${product.detailDescription}
                                                    </li>
                                                    <li class="list-group-item">Short description:
                                                        ${product.shortDescription}</li>
                                                    <li class="list-group-item">Price: ${product.price}</li>
                                                    <li class="list-group-item">Quantity: ${product.quantity}</li>
                                                    <!-- <li class="list-group-item">
                                                        <img style="height: 200px; width: 200px; object-fit: cover; border-radius: 100%;"
                                                            src="/images/product/${product.image}" />
                                                    </li> -->
                                                </ul>
                                            </div>

                                            <a href="/admin/product" class="btn btn-success mt-3">Back</a>
                                        </div>

                                    </div>

                                </div>
                            </div>
                        </main>
                        <jsp:include page="../layout/footer.jsp" />
                    </div>
                </div>
                <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
                    crossorigin="anonymous"></script>
                <script src="/js/scripts.js"></script>
            </body>

            </html>