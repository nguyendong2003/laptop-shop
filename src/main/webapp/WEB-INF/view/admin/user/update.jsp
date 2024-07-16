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
                <title>Update user</title>
                <link href="/css/styles.css" rel="stylesheet" />
                <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
                <script>
                    $(document).ready(() => {
                        // Load original image when error on field input
                        const originImage = "${originImage}"
                        if (originImage) {
                            const imageURL = "/images/avatar/" + originImage;
                            $("#avatarPreview").attr("src", imageURL);
                            $("#avatarPreview").css({ "display": "block" });
                        }

                        // Load original image
                        const originalImage = "${newUser.avatar}";
                        if (originalImage) {
                            const imageURL = "/images/avatar/" + originalImage;
                            $("#avatarPreview").attr("src", imageURL);
                            $("#avatarPreview").css({ "display": "block" });
                        }

                        // Upload image 
                        const avatarFile = $("#avatarFile");
                        avatarFile.change(function (e) {
                            const imgURL = URL.createObjectURL(e.target.files[0]);
                            $("#avatarPreview").attr("src", imgURL);
                            $("#avatarPreview").css({ "display": "block" });
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
                                <h1 class="mt-4">Manage user</h1>
                                <ol class="breadcrumb mb-4">
                                    <li class="breadcrumb-item"><a href="/admin"> Dashboard</a></li>
                                    <li class="breadcrumb-item active">User</li>
                                </ol>

                                <div class="mt-5">
                                    <div class="row">
                                        <div class="col-md-6 col-12 mx-auto">
                                            <h3>Update user</h3>
                                            <hr />
                                            <form:form action="/admin/user/update" method="post"
                                                enctype="multipart/form-data" modelAttribute="newUser" class="row">
                                                <!-- Define Variable -->
                                                <c:set var="errorPhone">
                                                    <form:errors path="phone" class="invalid-feedback" />
                                                </c:set>
                                                <c:set var="errorFullName">
                                                    <form:errors path="fullName" class="invalid-feedback" />
                                                </c:set>
                                                <c:set var="errorAddress">
                                                    <form:errors path="address" class="invalid-feedback" />
                                                </c:set>
                                                <!--  -->
                                                <div class="mb-3 d-none">
                                                    <label for="" class="form-label">Id:</label>
                                                    <form:input type="text" class="form-control" path="id" />
                                                </div>
                                                <div class="mb-3 d-none">
                                                    <label for="" class="form-label">Password:</label>
                                                    <form:input type="text" class="form-control" path="password" />
                                                </div>
                                                <div class="mb-3 col-12">
                                                    <label for="" class="form-label">Email:</label>
                                                    <form:input type="text" class="form-control" path="email"
                                                        readonly="true" />
                                                </div>
                                                <!-- <div class="mb-3 col-12 col-md-6">
                                                    <label for="" class="form-label">Password:</label>
                                                    <form:input type="password"
                                                        class="form-control ${not empty errorPassword ? 'is-invalid' : ''}"
                                                        path="password"
                                                        oninput="this.classList.remove('is-invalid');" />
                                                    ${errorPassword}

                                                </div> -->
                                                <div class="mb-3 col-12 col-md-6">
                                                    <label for="" class="form-label">Phone number:</label>
                                                    <form:input type="number"
                                                        class="form-control ${not empty errorPhone ? 'is-invalid' : ''}"
                                                        path="phone" oninput="this.classList.remove('is-invalid');" />
                                                    ${errorPhone}
                                                </div>
                                                <div class="mb-3 col-12 col-md-6">
                                                    <label for="" class="form-label">Full name:</label>
                                                    <form:input type="text"
                                                        class="form-control ${not empty errorFullName ? 'is-invalid' : ''}"
                                                        path="fullName"
                                                        oninput="this.classList.remove('is-invalid');" />
                                                    ${errorFullName}
                                                </div>
                                                <div class="mb-3 col-12">
                                                    <label for="" class="form-label">Address:</label>
                                                    <form:input type="text"
                                                        class="form-control ${not empty errorAddress ? 'is-invalid' : ''}"
                                                        path="address" oninput="this.classList.remove('is-invalid');" />
                                                    ${errorAddress}
                                                </div>
                                                <div class="mb-3 col-12 col-md-6">
                                                    <label for="" class="form-label">Role:</label>
                                                    <form:select class="form-select" path="role.name">
                                                        <form:option value="USER">USER</form:option>
                                                        <form:option value="ADMIN">ADMIN</form:option>
                                                    </form:select>
                                                </div>
                                                <div class="mb-3 col-12 col-md-6">
                                                    <label for="avatarFile" class="form-label">Avatar:</label>
                                                    <input name="avatarUploadFile" type="file" class="form-control"
                                                        id="avatarFile" accept=".png, .jpg," />
                                                </div>
                                                <div class="col-12 mb-3 d-flex justify-content-center">
                                                    <img style="display: none; height: 300px; width: 300px; object-fit: cover;"
                                                        id="avatarPreview" alt="avatar preview" />
                                                </div>
                                                <div class="col-12 mb-5">
                                                    <button type="submit" class="btn btn-primary">Update</button>
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