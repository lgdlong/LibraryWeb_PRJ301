package controller;

import entity.Book;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.BookService;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "NewBookController", urlPatterns = {"/newBookController"})
public class NewBookController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        try {
            BookService bookService = new BookService();
            List<Book> newBooks = bookService.getNewBook(); // đã tối ưu với try-with-resources

            request.setAttribute("newBooks", newBooks);
            request.setAttribute("contentPage","/guest/guest.jsp");
            request.getRequestDispatcher("/guest/layout.jsp").forward(request, response);

        } catch (Exception e) {
            System.err.println("Error in NewBookController: " + e.getMessage());
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Có lỗi xảy ra khi lấy sách mới.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        doGet(request, response); // dùng lại doGet để tránh lặp code
    }

    @Override
    public String getServletInfo() {
        return "Controller hiển thị danh sách sách mới xuất bản gần đây.";
    }
}
