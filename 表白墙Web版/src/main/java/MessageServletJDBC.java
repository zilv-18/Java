import com.fasterxml.jackson.databind.ObjectMapper;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: 人生如戏
 * Date: 2022/3/8
 * Time: 17:04
 */

@WebServlet("/messageDB")
public class MessageServletJDBC extends HttpServlet {
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json; charset=utf8");
        List<Message> messageList = load();
        objectMapper.writeValue(resp.getWriter(), messageList);
    }

    private List<Message> load() {
        List<Message> messageList = new ArrayList<>();
        // 从数据库读取数据
        // 1.建立数据库连接
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = DBUtil.getConnection();
            String sql = "select * from message";
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Message message = new Message();
                message.from = resultSet.getString("from");
                message.to = resultSet.getString("to");
                message.message = resultSet.getString("message");
                messageList.add(message);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                DBUtil.close(connection, statement, resultSet);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return messageList;
    }

    // 这个方法用来处理从客户端提交数据给服务器
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Message message = objectMapper.readValue(req.getInputStream(), Message.class);
//        messageList.add(message);

        // 在这里进行写数据库的操作
        try {
            save(message);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        resp.setContentType("application/json; charset=utf8");
        resp.getWriter().write("{\"OK\": 1}");
    }

    private void save(Message message) throws SQLException {
        System.out.println("向数据库写入数据!");

        // 1. 先和数据库建立连接
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            // 1.先和数据库建立连接
            connection = DBUtil.getConnection();
            // 2.构造拼装 SQL
            String sql = "insert into message values(?,?,?)";
            statement = connection.prepareStatement(sql);
            statement.setString(1, message.from);
            statement.setString(2, message.to);
            statement.setString(3, message.message);
            // 3. 执行 SQL
            int ret = statement.executeUpdate();
            if (ret == 1) {
                System.out.println("插入成功");
            } else {
                System.out.println("插入失败");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(connection, statement, null);
        }
    }
}
