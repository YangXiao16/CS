import java.sql.*;
import java.util.Scanner;

public class PersonalContactSystem {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/info";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "123456";

    public static void main(String[] args) {
        Connection connection = null;
        try {
            // 连接数据库
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            System.out.println("成功连接到数据库");

            // 创建Statement对象
            Statement statement = connection.createStatement();

            // 运行通讯录系统
            runContactSystem(statement);

            // 关闭Statement对象和数据库连接
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void runContactSystem(Statement statement) throws SQLException {
        // 进入循环，等待用户输入操作选项
        boolean exit = false;
        while (!exit) {
            System.out.println("\n个人通讯录系统");
            System.out.println("1. 查看联系人信息");
            System.out.println("2. 添加新的联系人");
            System.out.println("3. 修改联系人信息");
            System.out.println("4. 删除联系人");
            System.out.println("0. 退出系统");

            // 读取用户输入
            int choice = readUserChoice();

            switch (choice) {
                case 1:
                    viewContacts(statement);
                    break;
                case 2:
                    addContact(statement);
                    break;
                case 3:
                    updateContact(statement);
                    break;
                case 4:
                    deleteContact(statement);
                    break;
                case 0:
                    exit = true;
                    break;
                default:
                    System.out.println("无效的选项，请重新输入");
                    break;
            }
        }
    }

    private static int readUserChoice() {
        System.out.print("请输入您的选择: ");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }

    private static void viewContacts(Statement statement) throws SQLException {
        // 查询联系人信息
        String query = "SELECT * FROM contacts";
        ResultSet resultSet = statement.executeQuery(query);

        // 打印联系人信息
        System.out.println("\n联系人列表:");
        while (resultSet.next()) {
//            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String phone = resultSet.getString("phone");
            String address = resultSet.getString("address");
            System.out.println("姓名: " + name + ", 电话: " + phone + ", 地址: " + address);
        }

        resultSet.close();
    }

    private static void addContact(Statement statement) throws SQLException {
        // 读取新联系人信息
        Scanner scanner = new Scanner(System.in);
        System.out.print("请输入联系人姓名: ");
        String name = scanner.nextLine();
        System.out.print("请输入联系人电话: ");
        String phone = scanner.nextLine();
        System.out.print("请输入联系人地址: ");
        String address = scanner.nextLine();

        // 插入新联系人
        String insertQuery = "INSERT INTO contacts (name, phone, address) VALUES ('" + name + "', '" + phone + "', '" + address + "')";
        statement.executeUpdate(insertQuery);

        System.out.println("联系人已添加成功");
    }

    private static void updateContact(Statement statement) throws SQLException {
        // 读取要修改的联系人姓名
        Scanner scanner = new Scanner(System.in);
        System.out.print("请输入要修改的联系人姓名: ");
        String contactName = scanner.next();
        scanner.nextLine(); // 读取换行符

        // 查询联系人是否存在
        String query = "SELECT * FROM contacts WHERE name = '" + contactName + "'";
        ResultSet resultSet = statement.executeQuery(query);
        if (resultSet.next()) {
            // 提示用户选择要更新的字段
            System.out.println("请选择要更新的字段:");
            System.out.println("1. 姓名");
            System.out.println("2. 电话号码");
            System.out.println("3. 地址");
            int choice = scanner.nextInt();
            scanner.nextLine(); // 读取换行符

            // 根据选择更新相应的字段
            String updateQuery = "";
            switch (choice) {
                case 1:
                    System.out.print("请输入新的姓名: ");
                    String newName = scanner.nextLine();
                    updateQuery = "UPDATE contacts SET name = '" + newName + "' WHERE name = '" + contactName + "'";
                    break;
                case 2:
                    System.out.print("请输入新的电话号码: ");
                    String newPhone = scanner.nextLine();
                    updateQuery = "UPDATE contacts SET phone = '" + newPhone + "' WHERE name = '" + contactName + "'";
                    break;
                case 3:
                    System.out.print("请输入新的地址: ");
                    String newAddress = scanner.nextLine();
                    updateQuery = "UPDATE contacts SET address = '" + newAddress + "' WHERE name = '" + contactName + "'";
                    break;
                default:
                    System.out.println("无效的选择");
                    break;
            }

            if (!updateQuery.isEmpty()) {
                // 执行更新操作
                statement.executeUpdate(updateQuery);
                System.out.println("联系人已更新成功");
            }
        } else {
            System.out.println("联系人不存在");
        }

        resultSet.close();
    }

    private static void deleteContact(Statement statement) throws SQLException {
        // 读取要删除的联系人
        Scanner scanner = new Scanner(System.in);
        System.out.print("请输入要删除的联系人姓名: ");
        String contactName = scanner.nextLine();

        // 查询联系人是否存在
        String query = "SELECT * FROM contacts WHERE name = '" + contactName + "'";
        ResultSet resultSet = statement.executeQuery(query);
        if (resultSet.next()) {
            // 删除联系人
            String deleteQuery = "DELETE FROM contacts WHERE name = '" + contactName + "'";
            statement.executeUpdate(deleteQuery);

            System.out.println("联系人已删除成功");
        } else {
            System.out.println("联系人不存在");
        }

        resultSet.close();
    }
}
