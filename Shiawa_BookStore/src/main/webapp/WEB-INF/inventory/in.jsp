<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<head>
    <meta charset="utf-8">
    <title>DASHBOARD ADMIN</title>
    <meta content="width=device-width, initial-scale=1.0" name="viewport">
    <meta content="" name="keywords">
    <meta content="" name="description">

    <!-- Favicon -->
    <link href="${pageContext.request.contextPath}/assets/img/favicon.ico" rel="icon">

    <!-- Google Web Fonts -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Heebo:wght@400;500;600;700&display=swap" rel="stylesheet">

    <!-- Icon Font Stylesheet -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.10.0/css/all.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.1/font/bootstrap-icons.css" rel="stylesheet">

    <!-- Libraries Stylesheet -->
    <link href="assets/lib/owlcarousel/assets/owl.carousel.min.css" rel="stylesheet">
    <link href="assets/lib/tempusdominus/css/tempusdominus-bootstrap-4.min.css" rel="stylesheet" />

    <!-- Customized Bootstrap Stylesheet -->
    <link href="assets/css/bootstrap.min.css" rel="stylesheet">

    <!-- Template Stylesheet -->
    <link href="assets/css/style.css" rel="stylesheet">
</head>

<body>
    <div class="container-fluid position-relative bg-white d-flex p-0">
        <!-- Spinner Start -->
        <div id="spinner"
             class="show bg-white position-fixed translate-middle w-100 vh-100 top-50 start-50 d-flex align-items-center justify-content-center">
            <div class="spinner-border text-primary" style="width: 3rem; height: 3rem;" role="status">
                <span class="sr-only">Loading...</span>
            </div>
        </div>
        <!-- Spinner End -->


        <!-- Sidebar Start -->
        <div class="sidebar pe-4 pb-3">
            <nav class="navbar bg-light navbar-light">
                <a href="index.jsp" class="navbar-brand mx-4 mb-3">
                    <h3 class="text-primary"><img class="rounded-circle" src="${pageContext.request.contextPath}/assets/img/logo.jpg" alt="" style="width: 40px; height: 40px;">  SHIAWA</h3>
                </a>
                <div class="d-flex align-items-center ms-4 mb-4">
                    <div class="position-relative">
                        <img class="rounded-circle" src="${not empty profile.avatar ? pageContext.request.contextPath.concat(profile.avatar) : pageContext.request.contextPath.concat('/assets/img/user.jpg')}" alt="" style="width: 40px; height: 40px; object-fit: cover;">
                        <div class="bg-success rounded-circle border border-2 border-white position-absolute end-0 bottom-0 p-1"></div>
                    </div>
                    <div class="ms-3">
                        <h6 class="mb-0">${not empty profile.fullName ? profile.fullName : 'Inventory'}</h6>
                        <span>Staff</span>
                    </div>
                </div>

                <div class="navbar-nav w-100">
                    <a href="${pageContext.request.contextPath}/inventory" 
                       class="nav-item nav-link ${pageContext.request.requestURI.contains('/inventory') ? 'active' : ''}">
                        <i class="fa fa-book me-2">
                        </i>Book</a>
                    <a href="${pageContext.request.contextPath}/inventory?view=in" class="nav-item nav-link">
                        <i class="fa fa-arrow-down me-2"></i>Nhập kho
                    </a>
                    <a href="${pageContext.request.contextPath}/inventory?view=report" class="nav-item nav-link">
                        <i class="fa fa-chart-line me-2"></i>Lịch sử nhập/xuất
                    </a>
                    <a href="${pageContext.request.contextPath}/staff/profile" class="nav-item nav-link">
                        <i class="fa fa-user me-2"></i>Hồ sơ
                    </a>
                </div>
            </nav>
        </div>
        <!-- Sidebar End -->


        <!-- Content Start -->
        <div class="content">
            <!-- Navbar Start -->
            <nav class="navbar navbar-expand bg-light navbar-light sticky-top px-4 py-0">
                <a href="index.jsp" class="navbar-brand d-flex d-lg-none me-4">
                    <h2 class="text-primary mb-0"><i class="fa fa-hashtag"></i></h2>
                </a>
                <a href="#" class="sidebar-toggler flex-shrink-0">
                    <button type="button" class="btn btn-success rounded-pill m-2">
                        <i class="fa fa-bars"></i>
                    </button>
                </a>

                <div class="navbar-nav align-items-center ms-auto">

                </div>
            </nav>
            <!-- Navbar End -->


            <!-- Recent Sales Start -->
            <div class="container-fluid pt-4 px-4">
                <div class="bg-light rounded p-4">
                    <div class="d-flex justify-content-between align-items-center mb-3">
                        <h6 class="mb-0">Phiếu nhập kho (GRN)</h6>
                        <a class="btn btn-outline-primary btn-sm" href="${pageContext.request.contextPath}/inventory?view=history">Thông báo yêu cầu</a>
                    </div>

                    <c:if test="${not empty error}">
                        <div class="alert alert-danger">${error}</div>
                    </c:if>
                    <c:if test="${param.msg == 'requested'}">
                        <div class="alert alert-info">Phiếu nhập đã gửi Admin duyệt thành công. Mã yêu cầu: #${param.id}</div>
                    </c:if>
                    <c:if test="${param.msg == 'approved'}">
                        <div class="alert alert-success">Yêu cầu #${param.id} đã được Admin đồng ý.</div>
                    </c:if>

                    <form action="${pageContext.request.contextPath}/inventory" method="post">
                        <input type="hidden" name="view" value="in"/>

                        <div class="row g-3 mb-3">
                            <div class="col-md-4">
                                <label class="form-label">Mã phiếu</label>
                                <input class="form-control" name="txnCode" placeholder="Để trống sẽ tự tạo"/>
                            </div>
                            <div class="col-md-8">
                                <label class="form-label">Ghi chú</label>
                                <input class="form-control" name="note"/>
                            </div>
                        </div>

                        <div class="table-responsive mb-3" style="overflow: visible;">
                            <table class="table table-bordered align-middle mb-2">
                                <thead>
                                    <tr class="text-success">
                                        <th>Sách (chọn từ DB hoặc nhập sách mới)</th>
                                        <th style="width:160px;">Tác giả</th>
                                        <th style="width:160px;">NXB</th>
                                        <th style="width:180px;">Thể loại</th>
                                        <th style="width:120px;">Số lượng</th>
                                        <th style="width:160px;">Giá nhập (optional)</th>
                                        <th style="width:100px;">Thêm</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr id="entryRow">
                                        <td>
                                            <div class="position-relative" style="z-index: 1200;">
                                                <input class="form-control" id="entryBookInput" type="text" placeholder="Nhập tên sách hoặc chọn từ gợi ý" autocomplete="off"/>
                                                <div id="bookSuggestBox" class="list-group position-absolute w-100 shadow" style="top: calc(100% + 4px); left: 0; z-index: 1300; max-height: 260px; overflow-y: auto; display: none; background: #fff; border: 1px solid #dee2e6; border-radius: .375rem;"></div>
                                            </div>
                                        </td>
                                        <td><input class="form-control" id="entryAuthor" type="text" placeholder="Tác giả"/></td>
                                        <td><input class="form-control" id="entryPublisher" type="text" placeholder="Nhà xuất bản"/></td>
                                        <td>
                                            <select class="form-select" id="entryCategory">
                                                <option value="">-- chọn --</option>
                                                <c:forEach var="c" items="${categories}">
                                                    <option value="${c.categoryId}">${c.categoryName}</option>
                                                </c:forEach>
                                            </select>
                                        </td>
                                        <td><input class="form-control" id="entryQty" type="number" min="1"/></td>
                                        <td><input class="form-control" id="entryUnitCost" type="number" step="0.01" min="0"/></td>
                                        <td><button class="btn btn-outline-primary w-100" type="button" id="btnAddRow">Add</button></td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>

                        <div class="mb-3 position-relative" style="z-index: 1;">
                            <div class="small text-muted mb-2">Danh sách đã Add:</div>
                            <div id="emptyAddedHint" class="text-muted small">Chưa có dòng nào được thêm.</div>
                            <div class="table-responsive" style="overflow: visible;">
                                <table class="table table-sm table-striped align-middle mb-0" id="addedItemsTable" style="display:none;">
                                    <thead>
                                        <tr>
                                            <th>Sách</th>
                                            <th style="width:160px;">Tác giả</th>
                                            <th style="width:160px;">NXB</th>
                                            <th style="width:180px;">Thể loại</th>
                                            <th style="width:120px;">Số lượng</th>
                                            <th style="width:160px;">Giá nhập</th>
                                            <th style="width:100px;">Xóa</th>
                                        </tr>
                                    </thead>
                                    <tbody id="addedItemsBody"></tbody>
                                </table>
                            </div>
                        </div>

                        <div id="hiddenItemsContainer"></div>

                        <div class="d-flex gap-2">
                            <button class="btn btn-success" type="submit">Tạo phiếu nhập</button>
                        </div>
                    </form>
                </div>
            </div>
            <!-- Recent Sales End -->
        </div>
        <!-- Content End -->


        <!-- Back to Top -->
        <a href="#" class="btn btn-lg btn-primary btn-lg-square back-to-top"><i class="bi bi-arrow-up"></i></a>
    </div>

    <!-- JavaScript Libraries -->
    <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script>
    <script src="assets/lib/chart/chart.min.js"></script>
    <script src="assets/lib/easing/easing.min.js"></script>
    <script src="assets/lib/waypoints/waypoints.min.js"></script>
    <script src="assets/lib/owlcarousel/owl.carousel.min.js"></script>
    <script src="assets/lib/tempusdominus/js/moment.min.js"></script>
    <script src="assets/lib/tempusdominus/js/moment-timezone.min.js"></script>
    <script src="assets/lib/tempusdominus/js/tempusdominus-bootstrap-4.min.js"></script>

    <!-- Template Javascript -->
    <script src="assets/js/main.js"></script>
    <script>
        const toggleBtn = document.getElementById('btnToggleProfile');
        const panel = document.getElementById('profilePanel');
        if (toggleBtn && panel) {
            toggleBtn.addEventListener('click', function () {
                const isHidden = panel.style.display === 'none' || panel.style.display === '';
                panel.style.display = isHidden ? 'block' : 'none';
            });
            if (window.location.search.includes('success=1')) {
                panel.style.display = 'block';
            }
        }
    </script>
</body>


<script>
    (function () {
        const addBtn = document.getElementById('btnAddRow');
        const entryBookInput = document.getElementById('entryBookInput');
        const bookSuggestBox = document.getElementById('bookSuggestBox');
        const entryAuthor = document.getElementById('entryAuthor');
        const entryPublisher = document.getElementById('entryPublisher');
        const entryCategory = document.getElementById('entryCategory');
        const entryQty = document.getElementById('entryQty');
        const entryUnitCost = document.getElementById('entryUnitCost');

        const addedItemsTable = document.getElementById('addedItemsTable');
        const addedItemsBody = document.getElementById('addedItemsBody');
        const emptyAddedHint = document.getElementById('emptyAddedHint');
        const hiddenItemsContainer = document.getElementById('hiddenItemsContainer');

        const knownBooks = [
    <c:forEach var="b" items="${books}" varStatus="s">
        { id: "${b.bookId}", title: "${b.title}", author: "${b.author}", publisher: "${b.publisher}", categoryId: "${b.category != null ? b.category.categoryId : ''}", categoryName: "${b.category != null ? b.category.categoryName : ''}" }<c:if test="${!s.last}">,</c:if>
    </c:forEach>
        ];

        function escapeHtml(str) {
            return (str || '')
                    .replace(/&/g, '&amp;')
                    .replace(/</g, '&lt;')
                    .replace(/>/g, '&gt;')
                    .replace(/"/g, '&quot;')
                    .replace(/'/g, '&#39;');
        }

        function getMatchedBookByTitle(inputTitle) {
            const normalized = (inputTitle || '').trim().toLowerCase();
            if (!normalized)
                return null;
            return knownBooks.find(function (b) {
                return (b.title || '').trim().toLowerCase() === normalized;
            }) || null;
        }

        function renderSuggestions(keyword) {
            const key = (keyword || '').trim().toLowerCase();
            const filtered = key
                    ? knownBooks.filter(function (b) {
                        return (b.title || '').toLowerCase().includes(key);
                    })
                    : knownBooks;

            if (!filtered.length) {
                bookSuggestBox.style.display = 'none';
                bookSuggestBox.innerHTML = '';
                return;
            }

            bookSuggestBox.innerHTML = filtered.map(function (b) {
                return '<button type="button" class="list-group-item list-group-item-action book-suggest-item" data-title="'
                        + escapeHtml(b.title) + '">#' + b.id + ' - ' + escapeHtml(b.title) + '</button>';
            }).join('');
            bookSuggestBox.style.display = 'block';
        }

        function getEntryValues() {
            const bookInput = entryBookInput.value.trim();
            const matchedBook = getMatchedBookByTitle(bookInput);
            const categoryOption = entryCategory.options[entryCategory.selectedIndex];
            return {
                bookLabel: bookInput,
                bookId: matchedBook ? matchedBook.id : '',
                newBookTitle: matchedBook ? '' : bookInput,
                author: entryAuthor.value.trim(),
                publisher: entryPublisher.value.trim(),
                categoryId: entryCategory.value,
                categoryName: categoryOption ? categoryOption.text : '',
                qty: entryQty.value.trim(),
                unitCost: entryUnitCost.value.trim()
            };
        }

        function validateEntry(values) {
            if (!values.bookLabel) {
                alert('Vui lòng nhập tên sách hoặc chọn từ gợi ý trước khi Add.');
                return false;
            }
            if (!values.qty || Number(values.qty) <= 0) {
                alert('Vui lòng nhập số lượng hợp lệ trước khi Add.');
                return false;
            }
            if (values.unitCost && Number(values.unitCost) < 0) {
                alert('Giá nhập không hợp lệ.');
                return false;
            }
            if (!values.bookId) {
                if (!values.author) {
                    alert('Vui lòng nhập tác giả cho sách mới.');
                    return false;
                }
                if (!values.publisher) {
                    alert('Vui lòng nhập nhà xuất bản cho sách mới.');
                    return false;
                }
                if (!values.categoryId) {
                    alert('Vui lòng chọn thể loại cho sách mới.');
                    return false;
                }
            }
            return true;
        }

        function clearEntryRow() {
            entryBookInput.value = '';
            entryAuthor.value = '';
            entryPublisher.value = '';
            entryCategory.value = '';
            entryQty.value = '';
            entryUnitCost.value = '';
            entryBookInput.focus();
        }

        function rebuildHiddenInputs() {
            hiddenItemsContainer.innerHTML = '';
            const rows = addedItemsBody.querySelectorAll('tr');
            rows.forEach(function (row) {
                hiddenItemsContainer.insertAdjacentHTML('beforeend',
                        '<input type="hidden" name="bookId" value="' + (row.dataset.bookId || '') + '">' +
                        '<input type="hidden" name="newBookTitle" value="' + (row.dataset.newBookTitle || '') + '">' +
                        '<input type="hidden" name="newBookAuthor" value="' + (row.dataset.newBookAuthor || '') + '">' +
                        '<input type="hidden" name="newBookPublisher" value="' + (row.dataset.newBookPublisher || '') + '">' +
                        '<input type="hidden" name="newBookCategoryId" value="' + (row.dataset.newBookCategoryId || '') + '">' +
                        '<input type="hidden" name="qty" value="' + (row.dataset.qty || '') + '">' +
                        '<input type="hidden" name="unitCost" value="' + (row.dataset.unitCost || '') + '">'
                        );
            });
        }

        function toggleAddedList() {
            const hasItems = addedItemsBody.querySelectorAll('tr').length > 0;
            addedItemsTable.style.display = hasItems ? '' : 'none';
            emptyAddedHint.style.display = hasItems ? 'none' : '';
        }

        addBtn.addEventListener('click', function () {
            const values = getEntryValues();
            if (!validateEntry(values))
                return;

            const tr = document.createElement('tr');
            tr.dataset.bookId = values.bookId;
            tr.dataset.newBookTitle = values.newBookTitle;
            tr.dataset.newBookAuthor = values.author;
            tr.dataset.newBookPublisher = values.publisher;
            tr.dataset.newBookCategoryId = values.categoryId;
            tr.dataset.newBookCategoryName = values.categoryName;
            tr.dataset.qty = values.qty;
            tr.dataset.unitCost = values.unitCost;

            tr.innerHTML =
                    '<td>' + escapeHtml(values.bookLabel) + '</td>' +
                    '<td>' + escapeHtml(values.author || '-') + '</td>' +
                    '<td>' + escapeHtml(values.publisher || '-') + '</td>' +
                    '<td>' + escapeHtml(values.categoryName || '-') + '</td>' +
                    '<td>' + values.qty + '</td>' +
                    '<td>' + (values.unitCost || '-') + '</td>' +
                    '<td><button type="button" class="btn btn-sm btn-outline-danger btn-remove-item">Delete</button></td>';

            addedItemsBody.appendChild(tr);
            rebuildHiddenInputs();
            toggleAddedList();
            clearEntryRow();
            bookSuggestBox.style.display = 'none';
        });

        entryBookInput.addEventListener('focus', function () {
            renderSuggestions(entryBookInput.value);
        });

        entryBookInput.addEventListener('input', function () {
            renderSuggestions(entryBookInput.value);
            const matched = getMatchedBookByTitle(entryBookInput.value);
            if (matched) {
                entryAuthor.value = matched.author || '';
                entryPublisher.value = matched.publisher || '';
                entryCategory.value = matched.categoryId || '';
            } else {
                entryAuthor.value = '';
                entryPublisher.value = '';
                entryCategory.value = '';
            }
        });

        bookSuggestBox.addEventListener('click', function (e) {
            const btn = e.target.closest('.book-suggest-item');
            if (!btn)
                return;
            entryBookInput.value = btn.dataset.title || '';
            const matched = getMatchedBookByTitle(entryBookInput.value);
            if (matched) {
                entryAuthor.value = matched.author || '';
                entryPublisher.value = matched.publisher || '';
                entryCategory.value = matched.categoryId || '';
            }
            bookSuggestBox.style.display = 'none';
            entryQty.focus();
        });

        document.addEventListener('click', function (e) {
            if (!bookSuggestBox.contains(e.target) && e.target !== entryBookInput) {
                bookSuggestBox.style.display = 'none';
            }
        });

        addedItemsBody.addEventListener('click', function (e) {
            if (e.target.classList.contains('btn-remove-item')) {
                const row = e.target.closest('tr');
                if (row) {
                    row.remove();
                    rebuildHiddenInputs();
                    toggleAddedList();
                }
            }
        });

        document.querySelector('form').addEventListener('submit', function (e) {
            if (addedItemsBody.querySelectorAll('tr').length === 0) {
                e.preventDefault();
                alert('Bạn cần Add ít nhất 1 dòng sản phẩm trước khi tạo phiếu nhập.');
            }
        });
    })();
</script>
<jsp:include page="/WEB-INF/include/footerAdmin.jsp"/>




