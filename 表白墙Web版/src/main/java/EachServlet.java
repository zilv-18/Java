import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.PublicKey;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: 人生如戏
 * Date: 2022/3/10
 * Time: 11:48
 */

class Person {
    public String name;
    public String phone;

    public Person(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }
}

@WebServlet("/each")
public class EachServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=utf-8");
        List<Person> persons = new ArrayList<>();
        persons.add(new Person("小明", "666"));
        persons.add(new Person("小红", "777"));
        persons.add(new Person("小张", "888"));

        WebContext context = new WebContext(req, resp, this.getServletContext());
        context.setVariable("persons", persons);

//        engine.process("thymeleafEach",context, resp.getWriter());
        TemplateEngine engine = (TemplateEngine)getServletContext().getAttribute("engine");
        String html = engine.process("thymeleafEach", context);
        System.out.println(html);
        resp.getWriter().write(html);
    }
}
