// ================= MENU FILTER =================
document.querySelectorAll(".menu a").forEach(item => {
    item.addEventListener("click", e => {
        e.preventDefault();
        const filter = item.dataset.filter;

        document.querySelectorAll(".book").forEach(book => {
            book.style.display =
                filter === "all" || book.dataset.category === filter
                    ? "block"
                    : "none";
        });
    });
});

// ================= CART TOGGLE =================
const cartIcon = document.getElementById("cartIcon");
const cartPage = document.getElementById("cartPage");
const booksSection = document.querySelector(".books");
const backBtn = document.getElementById("backToShop");

cartIcon.addEventListener("click", () => {
    booksSection.style.display = "none";
    cartPage.style.display = "block";
});

backBtn.addEventListener("click", () => {
    cartPage.style.display = "none";
    booksSection.style.display = "grid";
});

// ================= ADD TO CART =================
const cartItemsDiv = document.getElementById("cartItems");
const totalPriceEl = document.getElementById("totalPrice");

let cart = [];
let discountRate = 0; // % giảm giá

document.querySelectorAll(".add-cart").forEach(btn => {
    btn.addEventListener("click", () => {
        const book = btn.closest(".book");
        const name = book.dataset.name;
        const price = Number(book.dataset.price);

        const found = cart.find(i => i.name === name);
        if (found) found.qty++;
        else cart.push({ name, price, qty: 1 });

        renderCart();
        alert("✔ Đã thêm vào giỏ hàng");
    });
});

// ================= RENDER CART =================
function renderCart() {
    cartItemsDiv.innerHTML = "";

    cart.forEach((item, index) => {
        cartItemsDiv.innerHTML += `
            <div class="cart-item">
                <input type="checkbox" class="select-item" data-index="${index}">
                
                <div class="product">
                    <img src="https://via.placeholder.com/80x100">
                    <span>${item.name}</span>
                </div>

                <span>$${item.price}</span>
                <span>${item.qty}</span>
                <span>$${item.price * item.qty}</span>
            </div>
        `;
    });

    updateTotal();
}

// ================= TOTAL (CHỈ TÍNH SP ĐƯỢC TICK) =================
cartItemsDiv.addEventListener("change", e => {
    if (e.target.classList.contains("select-item")) {
        updateTotal();
    }
});

function updateTotal() {
    let total = 0;

    document.querySelectorAll(".select-item").forEach(cb => {
        if (cb.checked) {
            const item = cart[cb.dataset.index];
            total += item.price * item.qty;
        }
    });

    // áp dụng voucher
    total = total * (1 - discountRate);

    totalPriceEl.innerText = `Total: $${total.toFixed(2)}`;
}

// ================= VOUCHER =================
const applyBtn = document.getElementById("applyVoucher");
const voucherInput = document.getElementById("voucherInput");
const message = document.getElementById("voucherMessage");

applyBtn.addEventListener("click", () => {
    const code = voucherInput.value.trim().toUpperCase();

    if (code === "SALE10") {
        discountRate = 0.1;
        message.innerText = "✔ Giảm 10% tổng đơn hàng";
        message.style.color = "green";
    } else if (code === "FREESHIP") {
        discountRate = 0;
        message.innerText = "✔ Miễn phí vận chuyển";
        message.style.color = "green";
    } else {
        discountRate = 0;
        message.innerText = "✖ Mã giảm giá không hợp lệ";
        message.style.color = "red";
    }

    updateTotal();
});
// OPEN ACCOUNT MODAL

const accountIcon = document.getElementById("accountIcon");
const accountPage = document.getElementById("accountPage");

const booksPage = document.querySelector(".books");

// CLICK ACCOUNT
accountIcon.addEventListener("click", () => {
    booksPage.style.display = "none";
    cartPage.style.display = "none";
    accountPage.style.display = "block";
});

// TAB SWITCH
const tabs = document.querySelectorAll(".tab");
const contents = document.querySelectorAll(".tab-content");

tabs.forEach(tab => {
    tab.addEventListener("click", () => {
        tabs.forEach(t => t.classList.remove("active"));
        contents.forEach(c => c.classList.remove("active"));

        tab.classList.add("active");
        document.getElementById(tab.dataset.tab).classList.add("active");
    });
});




