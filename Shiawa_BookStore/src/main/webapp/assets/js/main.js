(function ($) {
    "use strict";

    // Spinner
    var spinner = function () {
        setTimeout(function () {
            if ($('#spinner').length > 0) {
                $('#spinner').removeClass('show');
            }
        }, 1);
    };
    spinner();


    // Back to top button
    $(window).scroll(function () {
        if ($(this).scrollTop() > 300) {
            $('.back-to-top').fadeIn('slow');
        } else {
            $('.back-to-top').fadeOut('slow');
        }
    });
    $('.back-to-top').click(function () {
        $('html, body').animate({scrollTop: 0}, 1500, 'easeInOutExpo');
        return false;
    });


    // Sidebar Toggler
    $('.sidebar-toggler').click(function () {
        $('.sidebar, .content').toggleClass("open");
        return false;
    });


    // Progress Bar
    $('.pg-bar').waypoint(function () {
        $('.progress .progress-bar').each(function () {
            $(this).css("width", $(this).attr("aria-valuenow") + '%');
        });
    }, {offset: '80%'});


    // Calender
    $('#calender').datetimepicker({
        inline: true,
        format: 'L'
    });


    // Testimonials carousel
    $(".testimonial-carousel").owlCarousel({
        autoplay: true,
        smartSpeed: 1000,
        items: 1,
        dots: true,
        loop: true,
        nav: false
    });


    // Worldwide Sales Chart
    var ctx1 = $("#worldwide-sales").get(0).getContext("2d");
    var myChart1 = new Chart(ctx1, {
        type: "bar",
        data: {
            labels: ["2016", "2017", "2018", "2019", "2020", "2021", "2022"],
            datasets: [{
                    label: "USA",
                    data: [15, 30, 55, 65, 60, 80, 95],
                    backgroundColor: "rgba(0, 156, 255, .7)"
                },
                {
                    label: "UK",
                    data: [8, 35, 40, 60, 70, 55, 75],
                    backgroundColor: "rgba(0, 156, 255, .5)"
                },
                {
                    label: "AU",
                    data: [12, 25, 45, 55, 65, 70, 60],
                    backgroundColor: "rgba(0, 156, 255, .3)"
                }
            ]
        },
        options: {
            responsive: true
        }
    });


    // Salse & Revenue Chart
    var ctx2 = $("#salse-revenue").get(0).getContext("2d");
    var myChart2 = new Chart(ctx2, {
        type: "line",
        data: {
            labels: ["2016", "2017", "2018", "2019", "2020", "2021", "2022"],
            datasets: [{
                    label: "Salse",
                    data: [15, 30, 55, 45, 70, 65, 85],
                    backgroundColor: "rgba(0, 156, 255, .5)",
                    fill: true
                },
                {
                    label: "Revenue",
                    data: [99, 135, 170, 130, 190, 180, 270],
                    backgroundColor: "rgba(0, 156, 255, .3)",
                    fill: true
                }
            ]
        },
        options: {
            responsive: true
        }
    });



    // Single Line Chart
    var ctx3 = $("#line-chart").get(0).getContext("2d");
    var myChart3 = new Chart(ctx3, {
        type: "line",
        data: {
            labels: [50, 60, 70, 80, 90, 100, 110, 120, 130, 140, 150],
            datasets: [{
                    label: "Salse",
                    fill: false,
                    backgroundColor: "rgba(0, 156, 255, .3)",
                    data: [7, 8, 8, 9, 9, 9, 10, 11, 14, 14, 15]
                }]
        },
        options: {
            responsive: true
        }
    });


    // Single Bar Chart
    var ctx4 = $("#bar-chart").get(0).getContext("2d");
    var myChart4 = new Chart(ctx4, {
        type: "bar",
        data: {
            labels: ["Italy", "France", "Spain", "USA", "Argentina"],
            datasets: [{
                    backgroundColor: [
                        "rgba(0, 156, 255, .7)",
                        "rgba(0, 156, 255, .6)",
                        "rgba(0, 156, 255, .5)",
                        "rgba(0, 156, 255, .4)",
                        "rgba(0, 156, 255, .3)"
                    ],
                    data: [55, 49, 44, 24, 15]
                }]
        },
        options: {
            responsive: true
        }
    });


    // Pie Chart
    var ctx5 = $("#pie-chart").get(0).getContext("2d");
    var myChart5 = new Chart(ctx5, {
        type: "pie",
        data: {
            labels: ["Italy", "France", "Spain", "USA", "Argentina"],
            datasets: [{
                    backgroundColor: [
                        "rgba(0, 156, 255, .7)",
                        "rgba(0, 156, 255, .6)",
                        "rgba(0, 156, 255, .5)",
                        "rgba(0, 156, 255, .4)",
                        "rgba(0, 156, 255, .3)"
                    ],
                    data: [55, 49, 44, 24, 15]
                }]
        },
        options: {
            responsive: true
        }
    });


    // Doughnut Chart
    var ctx6 = $("#doughnut-chart").get(0).getContext("2d");
    var myChart6 = new Chart(ctx6, {
        type: "doughnut",
        data: {
            labels: ["Italy", "France", "Spain", "USA", "Argentina"],
            datasets: [{
                    backgroundColor: [
                        "rgba(0, 156, 255, .7)",
                        "rgba(0, 156, 255, .6)",
                        "rgba(0, 156, 255, .5)",
                        "rgba(0, 156, 255, .4)",
                        "rgba(0, 156, 255, .3)"
                    ],
                    data: [55, 49, 44, 24, 15]
                }]
        },
        options: {
            responsive: true
        }
    });

    const cartIcon = document.getElementById("cartIcon");

    document.querySelectorAll(".add-cart-form").forEach(form => {

        form.addEventListener("submit", function (e) {

            e.preventDefault(); // ❌ chặn nhảy trang

            const formData = new FormData(this);

            // 🔥 Gửi AJAX đến CartController
            fetch(this.action, {
                method: "POST",
                body: formData
            })
                    .then(res => res.text())
                    .then(data => {

                        // ===== HIỆU ỨNG BAY =====
                        const product = this.closest(".book");
                        const img = product.querySelector("img");

                        if (img) {
                            const flyingImg = img.cloneNode(true);

                            const imgRect = img.getBoundingClientRect();
                            const cartRect = cartIcon.getBoundingClientRect();

                            flyingImg.style.position = "fixed";
                            flyingImg.style.left = imgRect.left + "px";
                            flyingImg.style.top = imgRect.top + "px";
                            flyingImg.style.width = imgRect.width + "px";
                            flyingImg.style.height = imgRect.height + "px";
                            flyingImg.style.transition = "all 0.8s ease";
                            flyingImg.style.zIndex = "9999";

                            document.body.appendChild(flyingImg);

                            setTimeout(() => {
                                flyingImg.style.left = cartRect.left + "px";
                                flyingImg.style.top = cartRect.top + "px";
                                flyingImg.style.width = "20px";
                                flyingImg.style.height = "20px";
                                flyingImg.style.opacity = "0.5";
                            }, 10);

                            setTimeout(() => {
                                flyingImg.remove();
                            }, 800);
                        }

                        // ===== TĂNG SỐ BADGE =====
                        let badge = document.getElementById("cartCount");

                        if (!badge) {
                            badge = document.createElement("span");
                            badge.id = "cartCount";
                            badge.className = "cart-badge";
                            badge.innerText = "1";
                            cartIcon.appendChild(badge);
                        } else {
                            badge.innerText = parseInt(badge.innerText) + 1;
                        }

                    });

        });

    });
    function addToCart(event, bookId) {
        event.preventDefault(); // chặn reload trang

        fetch("${pageContext.request.contextPath}/cart", {
            method: "POST",
            headers: {
                "Content-Type": "application/x-www-form-urlencoded"
            },
            body: "action=add&book_id=" + bookId
        })
                .then(response => response.text())
                .then(data => {

                    // tạo thông báo
                    let msg = document.createElement("div");
                    msg.innerText = "✅ Đã thêm vào giỏ hàng!";
                    msg.style.position = "fixed";
                    msg.style.top = "20px";
                    msg.style.right = "20px";
                    msg.style.background = "green";
                    msg.style.color = "white";
                    msg.style.padding = "10px 15px";
                    msg.style.borderRadius = "5px";
                    msg.style.zIndex = "9999";

                    document.body.appendChild(msg);

                    setTimeout(() => {
                        msg.remove();
                    }, 2000);
                });
    }
})(jQuery);

