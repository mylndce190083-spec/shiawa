<%-- 
    Document   : edit
    Created on : Jan 31, 2026, 10:50:37 AM
    Author     : BA LIEM
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="../include/headerAdmin.jsp" %>
<!-- Recent Sales Start -->
<div class="container-fluid pt-4 px-4">
    <div class="bg-light text-center rounded p-4">
        <div class="d-flex align-items-center justify-content-between mb-4">
            <!--<h6 class="mb-0">User List</h6>-->
            <div class="d-flex justify-content-between align-items-center">
                <h6 class="mb-0">Edit Book</h6>
            </div>                         
        </div>

        <form action="${pageContext.request.contextPath}/book-admin" method="post" enctype="multipart/form-data">
            <div class="row">
                <!-- LEFT: IMAGE MANAGEMENT -->
                <div class="col-md-4">

                    <h6>Book Images</h6>

                    <!-- Upload form riêng -->
<!--                                <form action="${pageContext.request.contextPath}/book_img"
                          method="post"
                          enctype="multipart/form-data"
                          class="mb-3">

                        <input type="hidden" name="action" value="upload">
                        <input type="hidden" name="bookId" value="${book.bookId}">

                        <input type="file" name="image" class="form-control mb-2" required>

                        <button type="submit" class="btn btn-success w-100">
                            <i class="fa fa-upload me-2"></i>Upload Image
                        </button>
                    </form>-->

                    <!-- Danh sách ảnh -->
                    <c:forEach var="img" items="${bookImages}">
                        <c:if test="${not empty img.imageUrl}">
                            <div class="border rounded p-2 mb-3 text-center">

                                <img src="${pageContext.request.contextPath}/image?file=${img.imageUrl}"
                                     class="img-fluid rounded mb-2"
                                     style="max-height:150px"
                                     onerror="this.src='assets/img/no-image.png'">
                                <input type="hidden"
                                       name="deleteImageIds"
                                       id="delete_${img.imageId}"
                                       value="">

                                <div class="d-flex justify-content-between">

                                    <!-- Set Primary -->
                                    <div>
                                        <!--                                                        <input type="hidden" name="action" value="setPrimary">
                                                                                                <input type="hidden" name="imageId" value="${img.imageId}">
                                                                                                <input type="hidden" name="bookId" value="${book.bookId}">
                                                                                                <button type="submit"
                                                                                                        class="btn btn-sm ${img.primary ? 'btn-warning' : 'btn-outline-warning'}"
                                        ${img.primary ? 'disabled' : ''}>
                                    <i class="fa fa-star"></i>
                                </button>-->
                                        <button type="button"
                                                onclick="setPrimary(${img.imageId}, this)"
                                                class="btn btn-sm ${img.primary ? 'btn-warning' : 'btn-outline-warning'}">
                                            <i class="fa fa-star"></i>
                                        </button>


                                    </div>

                                    <!-- Delete -->
                                    <div>
                                        <!--                                                        <input type="hidden" name="action" value="delete">
                                                                                                <input type="hidden" name="imageId" value="${img.imageId}">
                                                                                                <input type="hidden" name="bookId" value="${book.bookId}">
                                                                                                <button type="submit" class="btn btn-danger btn-sm">
                                                                                                    <i class="fa fa-trash"></i>
                                                                                                </button>-->
                                        <button type="button"
                                                onclick="markDelete(${img.imageId}, this)"
                                                class="btn btn-danger btn-sm">
                                            <i class="fa fa-trash"></i>
                                        </button>


                                    </div>

                                </div>

                            </div>
                        </c:if>
                    </c:forEach>

                </div>
                <!-- Book Information -->
                <div class="col-md-8">

                    <input type="hidden" name="view" value="edit">
                    <input type="hidden" name="bookId" value="${book.bookId}">

                    <table class="table table-bordered text-start">

                        <tr>
                            <th width="30%">Book ID</th>
                            <td>
                                <input class="form-control" value="${book.bookId}" disabled>
                            </td>
                        </tr>

                        <tr>
                            <th>Title</th>
                            <td>
                                <input class="form-control" name="title" value="${book.title}" required>
                            </td>
                        </tr>

                        <tr>
                            <th>Author</th>
                            <td>
                                <input class="form-control" name="author" value="${book.author}" required>
                            </td>
                        </tr>

                        <tr>
                            <th>Category</th>
                            <td>
                                <select name="categoryId" class="form-select">
                                    <c:forEach var="c" items="${categoryList}">
                                        <option value="${c.categoryId}"
                                                ${c.categoryId == book.categoryId ? 'selected' : ''}>
                                            ${c.categoryName}
                                        </option>
                                    </c:forEach>
                                </select>
                            </td>
                        </tr>

                        <tr>
                            <th>Price</th>
                            <td>
                                <input type="number" step="0.01" class="form-control"
                                       name="price" value="${book.price}" required>
                            </td>
                        </tr>

                        <tr>
                            <th>Stock</th>
                            <td>
                                <input type="number" class="form-control"
                                       name="stock" value="${book.stock}" required>
                            </td>
                        </tr>

                        <tr>
                            <th>Status</th>
                            <td>
                                <div class="form-check form-check-inline">
                                    <input class="form-check-input" type="radio"
                                           name="isActive" value="true"
                                           ${book.isActive ? 'checked' : ''}>
                                    <label class="form-check-label">Active</label>
                                </div>
                                <div class="form-check form-check-inline">
                                    <input class="form-check-input" type="radio"
                                           name="isActive" value="false"
                                           ${!book.isActive ? 'checked' : ''}>
                                    <label class="form-check-label">Inactive</label>
                                </div>
                            </td>
                        </tr>

                        <tr>
                            <th>Description</th>
                            <td>
                                <textarea name="description" rows="4"
                                          class="form-control">${book.description}</textarea>
                            </td>
                        </tr>

                        <tr>
                            <th>Upload Images</th>
                            <td>
                                <input type="file" name="images" multiple class="form-control">
                            </td>
                        </tr>

                    </table>

                    <!-- Save & Back Button -->
                    <div class="mt-3">
                        <button type="submit" class="btn btn-primary">
                            <i class="fa fa-save me-2"></i>Save Changes
                        </button>

                        <a href="${pageContext.request.contextPath}/book-admin"
                           class="btn btn-secondary ms-2">
                            Cancel
                        </a>
                    </div>


                </div>
            </div>
        </form>

    </div>
</div>
<!-- Recent Sales End -->

<script>
    function previewImage(input) {
        if (input.files && input.files[0]) {
            const reader = new FileReader();
            reader.onload = function (e) {
                document.getElementById('previewImg').src = e.target.result;
            }
            reader.readAsDataURL(input.files[0]);
        }
    }
</script>

<script>
    let selectedPrimary = null;

    function setPrimary(imageId, btn) {

        // Bỏ màu tất cả sao
        document.querySelectorAll('.fa-star').forEach(icon => {
            icon.parentElement.classList.remove('btn-warning');
            icon.parentElement.classList.add('btn-outline-warning');
        });

        // Set màu cho cái đang chọn
        btn.classList.remove('btn-outline-warning');
        btn.classList.add('btn-warning');

        selectedPrimary = imageId;

        // Nếu chưa có input hidden thì tạo
        let input = document.getElementById("primaryImageInput");
        if (!input) {
            input = document.createElement("input");
            input.type = "hidden";
            input.name = "primaryImageId";
            input.id = "primaryImageInput";
            document.querySelector("form[action*='/book']").appendChild(input);
        }

        input.value = imageId;
    }

    function markDelete(imageId, btn) {

        // Ẩn ảnh ngay lập tức
        btn.closest(".border").style.display = "none";

        // Set hidden value
        document.getElementById("delete_" + imageId).value = imageId;
    }
</script>
<%@include file="../include/footerAdmin.jsp" %>