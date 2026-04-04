<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@include file="../include/headerInventory.jsp" %>

<div class="container-fluid pt-4 px-4">
    <div class="bg-light rounded p-4">
        <div class="d-flex justify-content-between align-items-center mb-3">
            <h6 class="mb-0">Inventory receipt (GRN)</h6>
            <a class="btn btn-outline-primary btn-sm" href="${pageContext.request.contextPath}/inventory?view=history">Notification of request</a>
        </div>

        <c:if test="${not empty error}">
            <div class="alert alert-danger">${error}</div>
        </c:if>
        <c:if test="${param.msg == 'requested'}">
            <div class="alert alert-info">The order has been successfully submitted to the Admin for approval. Request code: #${param.id}</div>
        </c:if>
        <c:if test="${param.msg == 'approved'}">
            <div class="alert alert-success">Require #${param.id} Approved by Admin.</div>
        </c:if>

        <form action="${pageContext.request.contextPath}/inventory" method="post">
            <input type="hidden" name="view" value="in"/>

            <div class="row g-3 mb-3">
                <div class="col-md-4">
                    <label class="form-label">Perform by</label>
                    <input class="form-control" value="${currentStaffName}" readonly/>
                    <div class="form-text">The ticket code will be automatically generated based on the name of the person making the transaction.</div>
                </div>
                <div class="col-md-8">
                    <label class="form-label">Note</label>
                    <input class="form-control" name="note"/>
                </div>
            </div>

            <div class="table-responsive mb-3" style="overflow: visible;">
                <table class="table table-bordered align-middle mb-2">
                    <thead>
                        <tr class="text-success">
                            <th>Books (select from database or import new books)</th>
                            <th style="width:160px;">Author</th>
                            <th style="width:160px;">Publisher</th>
                            <th style="width:180px;">Inventory</th>
                            <th style="width:120px;">Quantity</th>
                            <th style="width:160px;">Import price (optional)</th>
                            <th style="width:100px;">Add</th>
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
                                    <option value="">-- Select --</option>
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
                <div class="small text-muted mb-2">List Add:</div>
                <div id="emptyAddedHint" class="text-muted small">No lines have been added yet.</div>
                <div class="table-responsive" style="overflow: visible;">
                    <table class="table table-sm table-striped align-middle mb-0" id="addedItemsTable" style="display:none;">
                        <thead>
                            <tr>
                                <th>Book</th>
                                <th style="width:160px;">Author</th>
                                <th style="width:160px;">Publisher</th>
                                <th style="width:180px;">Category</th>
                                <th style="width:120px;">Quantity</th>
                                <th style="width:160px;">Import price</th>
                                <th style="width:100px;">Delete</th>
                            </tr>
                        </thead>
                        <tbody id="addedItemsBody"></tbody>
                    </table>
                </div>
            </div>

            <div id="hiddenItemsContainer"></div>

            <div class="d-flex gap-2">
                <button class="btn btn-success" type="submit">Create receipt</button>
            </div>
        </form>
    </div>
</div>
<!-- Recent Sales End -->
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
<%@include file="../include/footerInventory.jsp" %>




