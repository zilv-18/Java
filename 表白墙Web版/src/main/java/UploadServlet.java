import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: 人生如戏
 * Date: 2022/3/9
 * Time: 11:40
 */

/**
 * @MultipartConfig : 加上这个注解之后,Servlet 才能够正确的读取请求中的文件内容
 */
@MultipartConfig
@WebServlet("/upload")
public class UploadServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1. 从 req 对象中,读取出 Part 对象
        Part part = req.getPart("myImage");
        // 2.读取到 Part 对象中的一些参数
        System.out.println(part.getSubmittedFileName()); // 上传文件真实的文件名
        System.out.println(part.getContentType()); // 文件的类型
        System.out.println(part.getSize()); // 文件大小
        System.out.println(part.getName()); // 上传文件后的名字
        // 3. 把文件写入到指定目录中
        String path = "D:/Tmp/" + part.getSubmittedFileName();
        part.write(path);
        // 4. 返回一个响应,告诉客户端,上传成功
        resp.getWriter().write("upload ok");
    }
}
