import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: 人生如戏
 * Date: 2022/3/28
 * Time: 17:11
 */

@WebListener
public class ThymeleafConfig implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // 初始化 TemplateEngine
        ServletContext context = sce.getServletContext();
        // 1.创建 engine 实例
        TemplateEngine engine = new TemplateEngine();
        // 2.创建 resolver 实例
        ServletContextTemplateResolver resolver = new ServletContextTemplateResolver(context);
        resolver.setPrefix("/WEB-INF/template/");
        resolver.setSuffix(".html");
        resolver.setCharacterEncoding("utf-8");
        engine.setTemplateResolver(resolver);
        // 3.把创建好的 engine 实例给放到 ServletContext 中
        context.setAttribute("engine",engine);
        System.out.println("TemplateEngine 初始化完毕!");

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
