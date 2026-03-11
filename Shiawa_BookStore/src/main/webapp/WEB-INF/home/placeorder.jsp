<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Place Order</title>

        <link rel="stylesheet"
              href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">

        <style>
            body {
                font-family: Arial;
                background: #4CAF50;
            }

            .container {
                width: 1200px;
                margin: 30px auto;
                display: grid;
                grid-template-columns: 2fr 1fr;
                gap: 30px;
            }

            .box {

                background: white;

                padding: 20px;
                border-radius: 10px;
            }

            .order-header {
                display: grid;
                grid-template-columns: 3fr 1fr 1fr 1.2fr;
                font-weight: bold;
                padding: 12px 0;
                border-bottom: 2px solid #ddd;
                align-items: center;
            }



            .order-header span {
                text-align: center;
            }

            .order-header span:first-child {
                text-align: left;
            }

            .order-item {
                display: grid;
                grid-template-columns: 3fr 1fr 1fr 1.2fr;
                align-items: center;   /* 🔥 CĂN GIỮA THEO CHIỀU DỌC */
                padding: 15px 0;
                border-bottom: 1px solid #eee;
            }

            .product-info {
                display: flex;
                align-items: center;   /* 🔥 CĂN GIỮA ẢNH + TEXT */
                gap: 12px;
            }



            .qty {
                text-align: center;
                font-weight: 500;
            }

            .price {
                text-align: right;
                color: red;
                font-weight: bold;
                font-size: 15px;
                padding-right: 27px;
            }
            .order-item img {
                width: 90px;
            }



            select, input {
                width: 100%;
                padding: 8px;
                margin-bottom: 10px;
            }

            .summary-row {
                display: flex;
                justify-content: space-between;
                margin-bottom: 10px;
            }

            .total {
                font-size: 18px;
                color: red;
                font-weight: bold;
            }

            .btn {
                width: 100%;
                padding: 12px ;

                background: #e53935;

                color: white;
                border: none;
                border-radius: 6px;
                cursor: pointer;

            }
            button[name="action"][value="confirm"] {
                background-color: red;
                color: white;
                border: none;
                padding: 10px 18px;
                border-radius: 6px;
                cursor: pointer;
                font-weight: bold;
                width: 100%;
            }

            button[name="action"][value="confirm"]:hover {
                background-color: darkred;
            }

            .error-msg {
                color: #e53935;
                font-size: 13px;
                margin-top: 4px;
            }
            /* ===== VOUCHER BOX ===== */
            .voucher-section{
                margin:15px 0;
                padding:15px;
                background:#f9f9f9;
                border-radius:10px;
                border:1px solid #eee;
            }

            .voucher-title{
                font-weight:600;
                margin-bottom:10px;
                font-size:15px;
            }

            .voucher-box{
                display:flex;
                gap:10px;
            }

            .voucher-box input{
                flex:1;
                padding:10px;
                border:1px solid #ddd;
                border-radius:8px;
                font-size:14px;
                transition:0.2s;
            }

            .voucher-box input:focus{
                border-color:#4CAF50;
                outline:none;
                box-shadow:0 0 0 2px rgba(76,175,80,0.15);
            }

            .voucher-box button{
                background:#4CAF50;
                color:white;
                border:none;
                padding:10px 18px;
                border-radius:8px;
                font-weight:500;
                cursor:pointer;
                transition:0.2s;
            }

            .voucher-box button:hover{
                background:#43a047;
            }

            .voucher-success{
                margin-top:8px;
                color:#2e7d32;
                font-size:14px;
            }

            .voucher-error{
                margin-top:8px;
                color:#e53935;
                font-size:14px;
            }
            /* BOX HIỂN THỊ ĐỊA CHỈ */
            .address-box {
                background: #ffffff;
                border: 1px solid #e8e8e8;
                border-radius: 12px;
                padding: 18px 20px;
                display: flex;
                justify-content: space-between;
                align-items: flex-start;
                margin-bottom: 15px;
                position: relative;
                box-shadow: 0 4px 10px rgba(0,0,0,0.04);
                transition: 0.2s;
            }

            .address-box:hover{
                box-shadow: 0 6px 16px rgba(0,0,0,0.08);
            }

            /* thông tin */
            .address-info{
                display: flex;
                flex-direction: column;
                gap: 6px;
            }

            /* dòng thông tin */
            .address-info p{
                margin: 0;
                font-size: 14px;
                color: #444;
                display: flex;
                align-items: center;
                gap: 8px;
            }

            /* icon */
            .address-info i{
                color: #888;
                font-size: 13px;
                width: 18px;
            }

            /* tên */
            .name{
                font-size: 15px;

                font-weight: 700;   /* in đậm hơn */
                color: #222;
            }

            /* nút chỉnh sửa */
            .edit-btn{
                position: absolute;
                top: 10px;
                right: 14px;
                font-size: 12px;
                color: #e53935;
                cursor: pointer;
                padding: 4px 8px;
                border-radius: 5px;
                font-weight: 500;
                transition: 0.2s;
            }

            .edit-btn:hover{
                background: #ffeaea;
            }

            /* FORM CHỈNH SỬA */
            .edit-form {
                display: none;   /* ẨN BAN ĐẦU */
                background: #ffffff;
                border: 1px solid #ddd;
                border-radius: 8px;
                padding: 20px;
            }

            .edit-form select,
            .edit-form input {
                width: 100%;
                padding: 10px;
                margin-bottom: 12px;
                border: 1px solid #ccc;
                border-radius: 6px;
                font-size: 14px;
            }

            .edit-form select:focus,
            .edit-form input:focus {
                border-color: #007bff;
                outline: none;
            }

            .edit-form button {
                padding: 8px 16px;
                background: #dc3545;
                color: white;
                border: none;
                border-radius: 6px;
                cursor: pointer;
            }

            .edit-form button:hover {
                background: #c82333;
            }
            .payment-box{
                margin:12px 0;
                font-family: Arial, sans-serif;
            }

            .payment-box label{
                display:block;
                font-size:14px;
                font-weight:500;
                margin-bottom:6px;
                color:#333;
            }

            .payment-box select{
                width:240px;
                padding:8px 10px;
                border:1px solid #ddd;
                border-radius:6px;
                font-size:14px;
                background:#fff;
                cursor:pointer;
                transition:all 0.2s ease;
            }

            .payment-box select:hover{
                border-color:#ff6b00;
            }

            .payment-box select:focus{
                outline:none;
                border-color:#ff6b00;
                box-shadow:0 0 4px rgba(255,107,0,0.3);
            }
        </style>
    </head>

    <body>



        <div class="container">

            <!-- LEFT -->
            <div class="box">

                <h3>📦 SẢN PHẨM</h3>
                <!-- HEADER -->
                <div class="order-header">
                    <span>Tên sách</span>
                    <span>Số lượng</span>
                    <span>Đơn giá</span>
                    <span>Thành tiền</span>
                </div>
                <c:set var="hasOutOfStock" value="false"/>

                <c:forEach var="item" items="${orderItems}">
                    <div class="order-item">

                        <!-- CHECKBOX 
                        <input type="checkbox"
                               class="select-item"
                               name="selectedItem"
                               data-book-id="${item.bookId}"
                               data-price="${item.price}"
                               data-qty="${item.quantity}"/>-->

                        <div class="product-info">
                            <img src="/uploads/${item.book.urlImg}">
                            ${item.book.title}
                        </div>

                        <div class="qty">
                            ${item.quantity} x
                        </div>

                        <div class="price">
                            <fmt:formatNumber 
                                value="${item.price}" 
                                type="number"
                                groupingUsed="true"
                                maxFractionDigits="0"/> đ
                        </div>
                        <!-- THÀNH TIỀN -->
                        <div class="price">

                            <fmt:formatNumber 
                                value="${item.price * item.quantity}" 
                                type="number"
                                groupingUsed="true"
                                maxFractionDigits="0"/> đ
                        </div>
                        <c:if test="${item.book.stock == 0}">
                            <div style="color:red; font-weight:bold;">
                                Sản phẩm này đã hết hàng
                            </div>
                            <c:set var="hasOutOfStock" value="true"/>
                        </c:if>
                    </div>


                </c:forEach>



            </div>

            <!-- RIGHT -->
            <div class="box">

                <form id="checkoutForm" action="${pageContext.request.contextPath}/checkout" method="post">
                    <h3>📍 ĐỊA CHỈ NHẬN HÀNG</h3>
                    <div class="address-box" id="viewAddress">
                        <input type="hidden" name="isEditAddress" value="false" id="isEditAddress">

                        <div class="address-info">
                            <!-- TÊN -->
                            <p class="name">
                                <i class="fa-solid fa-user"></i>
                                ${sessionScope.customer.fullname}
                            </p>

                            <!-- SĐT -->
                            <p>
                                <i class="fa-solid fa-phone"></i>
                                ${sessionScope.customer.phone}
                            </p>

                            <!-- ĐỊA CHỈ -->
                            <p>
                                <i class="fa-solid fa-location-dot"></i>
                                ${sessionScope.customer.address}
                            </p>

                        </div>

                        <div class="edit-btn" onclick="showEditForm()">
                            <i class="fa-solid fa-pen"></i> Sửa
                        </div>
                        <!-- GỬI VỀ SERVER -->

                    </div>


                    <div class="edit-form" id="editForm">
                        <select name="province" id="province" >
                            <option value="">Chọn Tỉnh / Thành phố</option>
                        </select>
                        <c:if test="${not empty provinceError}">
                            <div class="error-msg">${provinceError}</div>
                        </c:if>


                        <select name="district" id="district" >
                            <option value="">Chọn Quận / Huyện</option>
                        </select>
                        <c:if test="${not empty districtError}">
                            <div class="error-msg">${districtError}</div>
                        </c:if>


                        <select name="ward" id="ward">
                            <option value="">Chọn Phường / Xã</option>
                        </select>
                        <c:if test="${not empty wardError}">
                            <div class="error-msg">${wardError}</div>
                        </c:if>


                        <input type="text" name="detailAddress"
                               value="${detailAddress}"
                               placeholder="Số nhà, tên đường" >
                        <c:if test="${not empty detailError}">
                            <div class="error-msg">${detailError}</div>
                        </c:if>
                        <input type="text" name="receiverName"
                               value="${receiverName}"
                               placeholder="Họ và tên người nhận" >
                        <c:if test="${not empty receiverNameError}">
                            <div class="error-msg">${receiverNameError}</div>
                        </c:if>


                        <input type="text" name="phone"
                               value="${phone}"
                               placeholder="Số điện thoại" >
                        <c:if test="${not empty phoneError}">
                            <div class="error-msg">${phoneError}</div>
                        </c:if>
                    </div>
                    <h3>🧾 TÓM TẮT ĐƠN HÀNG</h3>

                    <div class="summary-row">
                        <span>Thành tiền:</span>
                        <span id="subtotal">0 đ</span>
                    </div>

                    <div class="summary-row">
                        <span>Phí vận chuyển:</span>
                        <span>20.000 đ</span>
                    </div>
                    <div class="voucher-section">

                        <div class="voucher-title">
                            🎟 Mã giảm giá
                        </div>

                        <div class="voucher-box">
                            <input type="text"
                                   name="voucherCode"
                                   value="${voucherCode}"
                                   placeholder="Nhập mã giảm giá">

                            <button type="submit"
                                    name="action"
                                    value="applyVoucher">
                                Áp dụng
                            </button>
                        </div>

                        <c:if test="${not empty voucherError}">
                            <div class="voucher-error">
                                ${voucherError}
                            </div>
                        </c:if>

                        <c:if test="${not empty discount}">
                            <div class="voucher-success">
                                ✔ Đã áp dụng mã (- ${discount} đ)
                            </div>
                        </c:if>

                    </div>

                    <c:if test="${not empty voucherError}">
                        <div class="error-msg">${voucherError}</div>
                    </c:if>

                    <c:if test="${not empty discount}">
                        <div class="summary-row">
                            <span>Giảm giá:</span>
                            <span style="color:red;">- ${discount} đ</span>
                        </div>
                    </c:if>
                    <div class="payment-box">
                        <label for="paymentMethod">Phương thức thanh toán:</label>

                        <select name="paymentMethod" id="paymentMethod" required>
                            <option value="">Chọn phương thức</option>
                            <option value="COD">Thanh toán khi nhận hàng</option>
                            <option value="ONLINE">Thanh toán online (VNPAY)</option>
                        </select>


                    </div>

                    <div class="summary-row total">
                        <span>Tổng thanh toán:</span>
                        <span id="total">
                            ${subtotal + 20000 - discount} đ
                        </span>
                    </div>



                    <c:forEach var="item" items="${orderItems}">
                        <input type="hidden" name="selectedItem" value="${item.bookId}" />
                    </c:forEach>

                    <button type="submit"
                            name="action"
                            value="confirm"
                            ${hasOutOfStock ? "disabled" : ""}>

                        Đặt hàng
                    </button>

                </form>

            </div>

        </div>



        <!-- ================== JAVASCRIPT ================== -->

        <script>

            const province = document.getElementById("province");
            const district = document.getElementById("district");
            const ward = document.getElementById("ward");
            let provincesData = [];
            // 🔥 Giá trị giữ lại từ server
            let selectedProvince = "${selectedProvince != null ? selectedProvince : ''}";
            let selectedDistrict = "${selectedDistrict != null ? selectedDistrict : ''}";
            let selectedWard = "${selectedWard != null ? selectedWard : ''}";
            fetch("https://provinces.open-api.vn/api/?depth=3")
                    .then(res => res.json())
                    .then(data => {

                        provincesData = data;
                        province.innerHTML = "<option value=''>Chọn Tỉnh / Thành phố</option>";
                        data.forEach(p => {
                            let option = document.createElement("option");
                            option.value = p.name;
                            option.textContent = p.name;
                            province.appendChild(option);
                        });
                        if (selectedProvince && selectedProvince.trim() !== "") {

                            let matchProvince = provincesData.find(p =>
                                p.name.trim().toLowerCase() === selectedProvince.trim().toLowerCase()
                            );
                            if (matchProvince) {
                                province.value = matchProvince.name;
                                selectedProvince = matchProvince.name;
                                autoLoadDistrict();
                            }
                        }
                    });
            function autoLoadDistrict() {

                let p = provincesData.find(x => x.name === selectedProvince);
                if (!p)
                    return;
                district.disabled = false;
                district.innerHTML = "<option value=''>Chọn Quận / Huyện</option>";
                p.districts.forEach(d => {
                    let option = document.createElement("option");
                    option.value = d.name;
                    option.textContent = d.name;
                    district.appendChild(option);
                });
                if (selectedDistrict) {
                    district.value = selectedDistrict;
                    autoLoadWard();
                }
            }

            function autoLoadWard() {

                let p = provincesData.find(x =>
                    x.name.trim().toLowerCase() === selectedProvince.trim().toLowerCase()
                );
                if (!p)
                    return;
                let d = p.districts.find(x => x.name === selectedDistrict);
                if (!d)
                    return;
                ward.disabled = false;
                ward.innerHTML = "<option value=''>Chọn Phường / Xã</option>";
                d.wards.forEach(w => {
                    let option = document.createElement("option");
                    option.value = w.name;
                    option.textContent = w.name;
                    ward.appendChild(option);
                });
                if (selectedWard) {
                    ward.value = selectedWard;
                }
            }


            // Event khi người dùng tự chọn

            province.addEventListener("change", function () {
                selectedProvince = this.value;
                selectedDistrict = "";
                selectedWard = "";
                district.innerHTML = "<option value=''>Chọn Quận / Huyện</option>";
                ward.innerHTML = "<option value=''>Chọn Phường / Xã</option>";
                autoLoadDistrict();
            });
            district.addEventListener("change", function () {
                selectedDistrict = this.value;
                selectedWard = "";
                autoLoadWard();
            });
            // ===== TÍNH TỔNG TIỀN THEO CHECKBOX =====

            function formatCurrency(number) {
                return number.toLocaleString('vi-VN') + " đ";
            }

            function calculateSummary() {
                let subtotal = 0;
                const shipping = 20000;
                document.querySelectorAll(".price").forEach(item => {

                    // Lấy text ví dụ: "78.000 đ"
                    let priceText = item.innerText;
                    // XÓA TẤT CẢ KÝ TỰ KHÔNG PHẢI SỐ
                    priceText = priceText.replace(/\D/g, "");
                    subtotal += Number(priceText);
                });
                const total = subtotal + shipping;
                document.getElementById("subtotal").innerText = formatCurrency(subtotal);
                document.getElementById("total").innerText = formatCurrency(total);
            }

            window.onload = calculateSummary;

            document.getElementById("checkoutForm").addEventListener("submit", function (e) {

                const paymentMethod = document.getElementById("paymentMethod").value;

                if (paymentMethod === "ONLINE") {

                    e.preventDefault(); // chặn submit form

                    let totalText = document.getElementById("total").innerText;
                    let amount = totalText.replace(/\D/g, "");

                    fetch("ajaxServlet", {
                        method: "POST",
                        headers: {
                            "Content-Type": "application/x-www-form-urlencoded"
                        },
                        body: "amount=" + amount
                    })
                            .then(res => res.json())
                            .then(data => {
                                window.location.href = data.data;
                            });

                }

            });
            function showEditForm() {
                document.getElementById("viewAddress").style.display = "none";
                document.getElementById("editForm").style.display = "block";
                document.getElementById("isEditAddress").value = "true";
            }

            function cancelEdit() {
                document.getElementById("viewAddress").style.display = "flex";
                document.getElementById("editForm").style.display = "none";
            }
        </script>

    </body>
</html>