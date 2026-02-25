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
                background: #f5f5f5;
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
                    </div>
                </c:forEach>

                <h3>📍 ĐỊA CHỈ NHẬN HÀNG</h3>

                <select name="province" id="province" required>
                    <option value="">Chọn Tỉnh / Thành phố</option>
                </select>

                <select name="district" id="district" disabled required>
                    <option value="">Chọn Quận / Huyện</option>
                </select>

                <select name="ward" id="ward" disabled required>
                    <option value="">Chọn Phường / Xã</option>
                </select>

                <input type="text" name="detail"
                       placeholder="Số nhà, tên đường" required>
                <input type="text" name="detail"
                       placeholder="số điện thoại" required>


            </div>

            <!-- RIGHT -->
            <div class="box">

                <h3>🧾 TÓM TẮT ĐƠN HÀNG</h3>

                <div class="summary-row">
                    <span>Thành tiền:</span>
                    <span id="subtotal">0 đ</span>
                </div>

                <div class="summary-row">
                    <span>Phí vận chuyển:</span>
                    <span>20.000 đ</span>
                </div>

                <div class="summary-row total">
                    <span>Tổng thanh toán:</span>
                    <span id="total">0 đ</span>
                </div>

                <form action="${pageContext.request.contextPath}/checkout" method="post">

                    <c:forEach var="item" items="${orderItems}">
                        <input type="hidden" name="selectedItem" value="${item.bookId}" />
                    </c:forEach>
                    <input type="text" name="address" required />
                    <button type="submit" name="action" value="confirm">
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
            // ===== LOAD TỈNH =====
            fetch("https://provinces.open-api.vn/api/?depth=3")
                    .then(res => {
                        if (!res.ok) {
                            throw new Error("Network response was not ok");
                        }
                        return res.json();
                    })
                    .then(data => {
                        provincesData = data;
                        province.innerHTML = "<option value=''>Chọn Tỉnh / Thành phố</option>";
                        data.forEach(p => {
                            let option = document.createElement("option");
                            option.value = p.name;
                            option.textContent = p.name;
                            province.appendChild(option);
                        });
                    })
                    .catch(err => {
                        console.error("Lỗi load tỉnh thành:", err);
                        alert("Không load được danh sách tỉnh thành!");
                    });
            // ===== CHỌN TỈNH =====
            province.addEventListener("change", function () {

                district.innerHTML = "<option value=''>Chọn Quận / Huyện</option>";
                ward.innerHTML = "<option value=''>Chọn Phường / Xã</option>";
                ward.disabled = true;
                let selectedProvince = provincesData.find(p => p.name === this.value);
                if (!selectedProvince) {
                    district.disabled = true;
                    return;
                }

                district.disabled = false;
                selectedProvince.districts.forEach(d => {
                    let option = document.createElement("option");
                    option.value = d.name;
                    option.textContent = d.name;
                    district.appendChild(option);
                });
            });
            // ===== CHỌN QUẬN =====
            district.addEventListener("change", function () {

                ward.innerHTML = "<option value=''>Chọn Phường / Xã</option>";
                let selectedProvince = provincesData.find(p => p.name === province.value);
                if (!selectedProvince)
                    return;
                let selectedDistrict = selectedProvince.districts.find(d => d.name === this.value);
                if (!selectedDistrict) {
                    ward.disabled = true;
                    return;
                }

                ward.disabled = false;
                selectedDistrict.wards.forEach(w => {
                    let option = document.createElement("option");
                    option.value = w.name;
                    option.textContent = w.name;
                    ward.appendChild(option);
                });
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