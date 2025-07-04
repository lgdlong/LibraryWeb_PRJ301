<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Overdue Books</title>
    <style>
        table {
            width: 100%;
            border-collapse: collapse;
        }

        th, td {
            padding: 10px;
            border: 1px solid #ccc;
        }

        th {
            background-color: #333;
            color: white;
        }

        .status-overdue {
            color: red;
            font-weight: bold;
        }
    </style>
</head>
<body>

    <h2>List of Overdue Books</h2>

    <c:if test="${empty overdueList}">
        <p>No overdue books found.</p>
    </c:if>

    <c:if test="${not empty overdueList}">
        <table>
            <thead>
                <tr>
                    <th>#</th>
                    <th>Book Title</th>
                    <th>Borrow Date</th>
                    <th>Due Date</th>
                    <th>Return Date</th>
                    <th>Status</th>
                    <th>Fine</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="record" items="${overdueList}" varStatus="loop">
                    <tr>
                        <td>${loop.index + 1}</td>
                        <td>${fn:escapeXml(record.bookTitle)}</td>
                        <td>${record.borrowDate}</td>
                        <td>${record.dueDate}</td>
                        <td>
                            <c:choose>
                                <c:when test="${record.returnDate != null}">
                                    ${record.returnDate}
                                </c:when>
                                <c:otherwise>
                                    <span style="color: gray;">Not returned</span>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <c:if test="${record.status == 'OVERDUE'}">
                                <span class="status-overdue">${record.status}</span>
                            </c:if>
                            <c:if test="${record.status != 'OVERDUE'}">
                                ${record.status}
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:if>

</body>
</html>
