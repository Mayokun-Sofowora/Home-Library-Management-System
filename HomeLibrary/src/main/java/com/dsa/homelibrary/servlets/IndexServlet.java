import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "NavigationServlet", urlPatterns = {"/navigate"})
public class IndexServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String destination = request.getParameter("destination");

        if (destination != null && !destination.isEmpty()) {
            switch (destination) {
                case "locations":
                    response.sendRedirect(request.getContextPath() + "/locations.html");
                    break;
                case "about":
                    response.sendRedirect(request.getContextPath() + "/about.html");
                    break;
                case "contact":
                    response.sendRedirect(request.getContextPath() + "/contact.html");
                    break;
                default:
                    // redirect to index
                    response.sendRedirect(request.getContextPath() + "/index.html");
                    break;
            }
        } else {
            // redirect to index
            response.sendRedirect(request.getContextPath() + "/index.html");
        }
    }
}
