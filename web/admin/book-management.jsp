<%--
  Created by IntelliJ IDEA.
  User: ADMIN
  Date: 5/30/2025
  Time: 13:36
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<h2>Book Management</h2>
<p>Manage books: add, update, remove from library.</p>

<!-- Search bar -->
<form method="get" action="${pageContext.request.contextPath}/admin/books" class="d-flex mb-3">
  <input type="text" name="search" class="form-control me-2" placeholder="Search by title or author"
         value="${param.search}"/>
  <button type="submit" class="btn btn-outline-primary">Search</button>
</form>

<!-- Add Book and Sort Buttons -->
<div class="d-flex justify-content-end mb-2">
  <button class="btn btn-primary me-2" data-bs-toggle="modal" data-bs-target="#sortModal">Sort</button>
  <button class="btn btn-success" onclick="openBookForm(null)">Add Book</button>
</div>

<!-- Book Table -->
<div class="card">
  <div class="card-header">Book List</div>
  <div class="card-body">
    <table class="table table-bordered table-hover table-sm">
      <thead class="table-light">
      <tr>
        <th>#</th>
        <th>Title</th>
        <th>Author</th>
        <th>ISBN</th>
        <th>Category</th>
        <th>Published Year</th>
        <th>Total Copies</th>
        <th>Available Copies</th>
        <th>Status</th>
      </tr>
      </thead>
      <tbody>
      <c:forEach var="book" items="${bookList}" varStatus="loop">
        <tr
          onclick="openBookForm(${book.id}, '${fn:escapeXml(book.title)}', '${fn:escapeXml(book.author)}', '${fn:escapeXml(book.isbn)}', '${fn:escapeXml(book.coverUrl)}', '${fn:escapeXml(book.category)}', ${book.publishedYear}, ${book.totalCopies}, ${book.availableCopies}, '${fn:escapeXml(book.status)}')">
          <td>${loop.index + 1}</td>
          <td>${fn:escapeXml(book.title)}</td>
          <td>${fn:escapeXml(book.author)}</td>
          <td>${fn:escapeXml(book.isbn)}</td>
          <td>${fn:escapeXml(book.category)}</td>
          <td>${book.publishedYear}</td>
          <td>${book.totalCopies}</td>
          <td>${book.availableCopies}</td>
          <td>${fn:escapeXml(book.status)}</td>
        </tr>
      </c:forEach>
      </tbody>
    </table>
  </div>
</div>

<!-- Book Modal Form -->
<div class="modal fade" id="bookModal" tabindex="-1" aria-labelledby="bookModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <form method="post" action="${pageContext.request.contextPath}/admin/books">
        <input type="hidden" name="csrf_token" value="${sessionScope.csrf_token}">
        <div class="modal-header">
          <h5 class="modal-title" id="bookModalLabel">Edit Book</h5>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        <div class="modal-body">
          <input type="hidden" name="id" id="bookId">
          <div class="mb-3">
            <label for="title" class="form-label">Title</label>
            <input type="text" class="form-control" id="title" name="title" required maxlength="255">
          </div>
          <div class="mb-3">
            <label for="author" class="form-label">Author</label>
            <input type="text" class="form-control" id="author" name="author" required maxlength="255">
          </div>
          <div class="mb-3">
            <label for="isbn" class="form-label">ISBN</label>
            <input type="text" class="form-control" id="isbn" name="isbn" maxlength="20" 
                   pattern="^(?:ISBN(?:-1[03])?:? )?(?=[0-9X]{10}$|(?=(?:[0-9]+[- ]){3})[- 0-9X]{13}$|97[89][0-9]{10}$|(?=(?:[0-9]+[- ]){4})[- 0-9]{17}$)(?:97[89][- ]?)?[0-9]{1,5}[- ]?[0-9]+[- ]?[0-9]+[- ]?[0-9X]$"
                   title="Please enter a valid ISBN-10 or ISBN-13">
          </div>
          <div class="mb-3">
            <label for="coverUrl" class="form-label">Cover URL</label>
            <input type="text" class="form-control" id="coverUrl" name="coverUrl">
          </div>
          <div class="mb-3">
            <label for="category" class="form-label">Category</label>
            <input type="text" class="form-control" id="category" name="category">
          </div>
          <div class="mb-3">
            <label for="publishedYear" class="form-label">Published Year</label>
            <input type="number" class="form-control" id="publishedYear" name="publishedYear">
          </div>
          <div class="mb-3">
            <label for="totalCopies" class="form-label">Total Copies</label>
            <input type="number" class="form-control" id="totalCopies" name="totalCopies">
          </div>
          <div class="mb-3">
            <label for="availableCopies" class="form-label">Available Copies</label>
            <input type="number" class="form-control" id="availableCopies" name="availableCopies">
          </div>
          <div class="mb-3">
            <label for="status" class="form-label">Status</label>
            <select class="form-select" id="status" name="status" required>
              <option value="active">Active</option>
              <option value="inactive">Inactive</option>
            </select>
          </div>
        </div>
        <div class="modal-footer justify-content-between">
          <button type="button" class="btn btn-danger" id="bookDeleteBtn" onclick="submitBookDelete()">Delete</button>
          <div>
            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
            <button type="submit" class="btn btn-primary">Save</button>
          </div>
        </div>
      </form>
    </div>
  </div>
</div>

<!-- Sort Modal Form -->
<div class="modal fade" id="sortModal" tabindex="-1" aria-labelledby="sortModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <form method="get" action="${pageContext.request.contextPath}/admin/books" id="sortForm">
        <div class="modal-header">
          <h5 class="modal-title" id="sortModalLabel">Sort Books</h5>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        <div class="modal-body">
          <div class="mb-3">
            <label for="sortField" class="form-label">Sort By</label>
            <select class="form-select" id="sortField" name="sort" required>
              <option value="" disabled ${empty param.sort ? 'selected' : ''}>Select a field</option>
              <option value="title" ${param.sort == 'title' ? 'selected' : ''}>Title</option>
              <option value="author" ${param.sort == 'author' ? 'selected' : ''}>Author</option>
              <option value="publishedYear" ${param.sort == 'publishedYear' ? 'selected' : ''}>Published Year</option>
              <option value="totalCopies" ${param.sort == 'totalCopies' ? 'selected' : ''}>Total Copies</option>
              <option value="availableCopies" ${param.sort == 'availableCopies' ? 'selected' : ''}>Available Copies
              </option>
            </select>
          </div>
          <div class="mb-3">
            <label for="sortOrder" class="form-label">Order</label>
            <select class="form-select" id="sortOrder" name="order" required>
              <option value="asc" ${param.order == 'asc' || empty param.order ? 'selected' : ''}>Ascending</option>
              <option value="desc" ${param.order == 'desc' ? 'selected' : ''}>Descending</option>
            </select>
          </div>
          <!-- Preserve search parameter if it exists -->
          <c:if test="${not empty param.search}">
            <input type="hidden" name="search" value="${fn:escapeXml(param.search)}">
          </c:if>
        </div>
        <div class="modal-footer justify-content-between">
          <button type="button" class="btn btn-danger" onclick="removeSort()">Remove Sort</button>
          <div>
            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
            <button type="submit" class="btn btn-primary">Apply Sort</button>
          </div>
        </div>
      </form>
    </div>
  </div>
</div>

<script>
  function openBookForm(id, title, author, isbn, coverUrl, category, year, total, available, status) {
    const modal = new bootstrap.Modal(document.getElementById('bookModal'));

    document.getElementById('bookId').value = id || '';
    document.getElementById('title').value = title || '';
    document.getElementById('author').value = author || '';
    document.getElementById('isbn').value = isbn || '';
    document.getElementById('coverUrl').value = coverUrl || '';
    document.getElementById('category').value = category || '';
    document.getElementById('publishedYear').value = year || '';
    document.getElementById('totalCopies').value = total || '';
    document.getElementById('availableCopies').value = available || '';
    document.getElementById('status').value = status || 'active';

    document.getElementById('bookDeleteBtn').style.display = id ? 'inline-block' : 'none';

    modal.show();
  }

  function submitBookDelete() {
    if (confirm("Are you sure to delete this book?")) {
      const id = document.getElementById('bookId').value;
      if (id) {
        const form = document.createElement('form');
        form.method = 'post';
        form.action = "${pageContext.request.contextPath}/admin/books?delete=" + encodeURIComponent(id);
        
        // Add CSRF token
        const csrfInput = document.createElement('input');
        csrfInput.type = 'hidden';
        csrfInput.name = 'csrf_token';
        csrfInput.value = '${sessionScope.csrf_token}';
        form.appendChild(csrfInput);
        
        document.body.appendChild(form);
        form.submit();
      }
    }
  }

  function removeSort() {
    const form = document.getElementById('sortForm');
    // Clear sort and order fields
    document.getElementById('sortField').value = '';
    document.getElementById('sortOrder').value = 'asc';
    // Submit form with only search parameter (if exists)
    form.submit();
  }
</script>
