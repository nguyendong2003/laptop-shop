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
                <title>Create product</title>
                <link href="/css/styles.css" rel="stylesheet" />
                <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
                <script>
                    $(document).ready(() => {
                        const imageUploadFile = $("#imageUploadFile");
                        imageUploadFile.change(function (e) {
                            const imgURL = URL.createObjectURL(e.target.files[0]);
                            $("#productPreview").attr("src", imgURL);
                            $("#productPreview").css({ "display": "block" });
                        });
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
                                <h1 class="mt-4">Manage product</h1>
                                <ol class="breadcrumb mb-4">
                                    <li class="breadcrumb-item"><a href="/admin"> Dashboard</a></li>
                                    <li class="breadcrumb-item active">Product</li>
                                </ol>

                                <div class="mt-1">
                                    <div class="row">
                                        <div class="col-md-6 col-12 mx-auto">
                                            <h3>Create a product</h3>
                                            <hr />
                                            <form:form action="/admin/product/create" method="post"
                                                enctype="multipart/form-data" modelAttribute="newProduct" class="row">
                                                <!-- Define Variable -->
                                                <c:set var="errorName">
                                                    <form:errors path="name" class="invalid-feedback" />
                                                </c:set>
                                                <c:set var="errorPrice">
                                                    <form:errors path="price" class="invalid-feedback" />
                                                </c:set>
                                                <c:set var="errorDetailDescription">
                                                    <form:errors path="detailDescription" class="invalid-feedback" />
                                                </c:set>
                                                <c:set var="errorShortDescription">
                                                    <form:errors path="shortDescription" class="invalid-feedback" />
                                                </c:set>
                                                <c:set var="errorQuantity">
                                                    <form:errors path="quantity" class="invalid-feedback" />
                                                </c:set>
                                                <!--  -->


                                                <div class="mb-3 col-12 col-md-6">
                                                    <label for="" class="form-label">Name:</label>
                                                    <form:input type="text"
                                                        class="form-control ${not empty errorName ? 'is-invalid' : ''}"
                                                        path="name" oninput="this.classList.remove('is-invalid');" />
                                                    ${errorName}
                                                </div>
                                                <div class="mb-3 col-12 col-md-6">
                                                    <label for="" class="form-label">Price:</label>
                                                    <form:input type="number"
                                                        class="form-control ${not empty errorPrice ? 'is-invalid' : ''}"
                                                        path="price" oninput="this.classList.remove('is-invalid');" />
                                                    ${errorPrice}
                                                </div>
                                                <div class="mb-3 col-12">
                                                    <label for="" class="form-label">Detail description:</label>
                                                    <form:textarea type="text" rows="3" style="resize: none;"
                                                        class="form-control ${not empty errorDetailDescription ? 'is-invalid' : ''}"
                                                        path="detailDescription"
                                                        oninput="this.classList.remove('is-invalid');" />
                                                    ${errorDetailDescription}
                                                </div>
                                                <div class="mb-3 col-12 col-md-6">
                                                    <label for="" class="form-label">Short description:</label>
                                                    <form:input type="text"
                                                        class="form-control ${not empty errorShortDescription ? 'is-invalid' : ''}"
                                                        path="shortDescription"
                                                        oninput="this.classList.remove('is-invalid');" />
                                                    ${errorShortDescription}
                                                </div>
                                                <div class="mb-3 col-12 col-md-6">
                                                    <label for="" class="form-label">Quantity:</label>
                                                    <form:input type="number"
                                                        class="form-control ${not empty errorQuantity ? 'is-invalid' : ''}"
                                                        path="quantity"
                                                        oninput="this.classList.remove('is-invalid');" />
                                                    ${errorQuantity}
                                                </div>
                                                <div class="mb-3 col-12 col-md-6">
                                                    <label for="" class="form-label">Factory:</label>
                                                    <form:select class="form-select" path="factory">
                                                        <form:option value="APPLE">Apple</form:option>
                                                        <form:option value="SAMSUNG">Samsung</form:option>
                                                        <form:option value="ASUS">Asus</form:option>
                                                        <form:option value="LENOVO">Lenovo</form:option>
                                                        <form:option value="ACER">Acer</form:option>
                                                        <form:option value="MSI">MSI</form:option>
                                                    </form:select>
                                                </div>
                                                <div class="mb-3 col-12 col-md-6">
                                                    <label for="" class="form-label">Target:</label>
                                                    <form:select class="form-select" path="target">
                                                        <form:option value="GAMING">Gaming</form:option>
                                                        <form:option value="SINHVIEN-VANPHONG">Sinh viên - Văn phòng
                                                        </form:option>
                                                        <form:option value="THIET-KE-DO-HOA">Thiết kế đồ hoạ
                                                        </form:option>
                                                        <form:option value="MONG-NHE">Mỏng nhẹ</form:option>
                                                        <form:option value="DOANH-NHAN">Doanh nhân</form:option>
                                                    </form:select>
                                                </div>
                                                <div class="mb-3 col-12">
                                                    <label for="imageUploadFile" class="form-label">Image:</label>
                                                    <input name="imageUploadFile" type="file" class="form-control"
                                                        id="imageUploadFile" accept=".png, .jpg," />

                                                    <!-- <c:set var="errorImage">
                                                        <form:errors path="image" class="invalid-feedback" />
                                                    </c:set>
                                                    <label for="" class="form-label">Image:</label>
                                                    <form:input type="file" accept=".png, .jpg," id="imageUploadFile"
                                                        name="imageUploadFile2"
                                                        class="form-control ${not empty errorImage ? 'is-invalid' : ''}"
                                                        path="image" oninput="this.classList.remove('is-invalid');" />
                                                    ${errorImage} -->
                                                </div>
                                                <div class="col-12 mb-3 d-flex justify-content-center">
                                                    <img style="display: none; height: 200px; width: 200px; object-fit: cover; border-radius: 100%;"
                                                        id="productPreview" alt="productPreview" />
                                                </div>
                                                <div class="col-12 mb-5">
                                                    <button type="submit" class="btn btn-primary">Create</button>
                                                </div>
                                            </form:form>
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