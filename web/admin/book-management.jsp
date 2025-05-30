<%--
  Created by IntelliJ IDEA.
  User: ADMIN
  Date: 5/30/2025
  Time: 13:36
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<h2>Book Management</h2>
<p>Manage books: add, update, remove from library.</p>

<!-- Search bar -->
<form method="get" action="${pageContext.request.contextPath}/admin/books" class="d-flex mb-3">
  <input type="text" name="search" class="form-control me-2" placeholder="Search by title or author"
         value="${param.search}"/>
  <button type="submit" class="btn btn-outline-primary">Search</button>
</form>

<!-- Add Book Button -->
<div class="d-flex justify-content-end mb-2">
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
          onclick="openBookForm(${book.id}, '${book.title}', '${book.author}', '${book.isbn}', '${book.coverUrl}', '${book.category}', ${book.publishedYear}, ${book.totalCopies}, ${book.availableCopies}, '${book.status}')">
          <td>${loop.index + 1}</td>
          <td>${book.title}</td>
          <td>${book.author}</td>
          <td>${book.isbn}</td>
          <td>${book.category}</td>
          <td>${book.publishedYear}</td>
          <td>${book.totalCopies}</td>
          <td>${book.availableCopies}</td>
          <td>${book.status}</td>
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
            <input type="text" class="form-control" id="isbn" name="isbn" maxlength="20" pattern="[0-9\-X]{10,17}">
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
        document.body.appendChild(form);
        form.submit();
      }
    }
  }
</script>


