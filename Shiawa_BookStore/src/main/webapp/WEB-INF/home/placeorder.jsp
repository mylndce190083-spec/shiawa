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
                grid-template-columns: 3.25fr 1fr 1.5fr;
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
                grid-template-columns: 4fr 1fr 1.5fr;
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
                    <span>Số lượng </span>
                    <span>Giá </span>
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
                            <img src="${pageContext.request.contextPath}/${item.book.urlImg}">
                            ${item.book.title}
                        </div>

                        <div>x ${item.quantity}</div>

                        <div class="price">
                            <fmt:formatNumber 
                                value="${item.price * item.quantity}" 
                                type="number" 
                                groupingUsed="true" 
                                maxFractionDigits="0" /> đ
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

                <form action="${pageContext.request.contextPath}/checkout" method="post">
                    <h3>📍 ĐỊA CHỈ NHẬN HÀNG</h3>

                    <select name="province" id="province" required>
                        <option value="">Chọn Tỉnh / Thành phố</option>
                    </select>
                    <c:if test="${not empty provinceError}">
                        <div class="error-msg">${provinceError}</div>
                    </c:if>


                    <select name="district" id="district" required>
                        <option value="">Chọn Quận / Huyện</option>
                    </select>
                    <c:if test="${not empty districtError}">
                        <div class="error-msg">${districtError}</div>
                    </c:if>


                    <select name="ward" id="ward" required>
                        <option value="">Chọn Phường / Xã</option>
                    </select>
                    <c:if test="${not empty wardError}">
                        <div class="error-msg">${wardError}</div>
                    </c:if>


                    <input type="text" name="detailAddress"
                           value="${detailAddress}"
                           placeholder="Số nhà, tên đường" required>
                    <c:if test="${not empty detailError}">
                        <div class="error-msg">${detailError}</div>
                    </c:if>
                    <input type="text" name="receiverName"
                           value="${receiverName}"
                           placeholder="Họ và tên người nhận" required>
                    <c:if test="${not empty receiverNameError}">
                        <div class="error-msg">${receiverNameError}</div>
                    </c:if>


                    <input type="text" name="phone"
                           value="${phone}"
                           placeholder="Số điện thoại" required>
                    <c:if test="${not empty phoneError}">
                        <div class="error-msg">${phoneError}</div>
                    </c:if>
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
            let selectedProvince = "${province}";
            let selectedDistrict = "${district}";
            let selectedWard = "${ward}";


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

                        // 🔥 Nếu có province cũ → tự load lại
                        if (selectedProvince) {
                            province.value = selectedProvince;
                            autoLoadDistrict();
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

                let p = provincesData.find(x => x.name === selectedProvince);
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

        </script>

    </body>
</html>