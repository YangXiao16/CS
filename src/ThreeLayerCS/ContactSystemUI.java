package ThreeLayerCS;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ContactSystemUI extends JFrame {
    // UI组件
    private JButton viewButton;
    private JButton addButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JTextArea contactList;

    // 业务逻辑层实例
    private ContactSystem contactSystem;

    public ContactSystemUI() {
        // 设置JFrame
        setTitle("个人联系系统");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // 初始化业务逻辑层
        contactSystem = new ContactSystem();

        // 创建UI组件
        JPanel buttonPanel = new JPanel(new FlowLayout());
        viewButton = new JButton("查看联系人");
        addButton = new JButton("添加联系人");
        updateButton = new JButton("更新联系人");
        deleteButton = new JButton("删除联系人");
        contactList = new JTextArea(10, 40);
        contactList.setEditable(false);

        // 添加组件到JFrame
        add(buttonPanel, BorderLayout.NORTH);
        add(new JScrollPane(contactList), BorderLayout.CENTER);

        buttonPanel.add(viewButton);
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);

        // 为按钮注册动作监听器
        viewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                viewContacts();
            }
        });

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addContact();
            }
        });

        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateContact();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteContact();
            }
        });

        // 打包并显示JFrame
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void viewContacts() {
        String contacts = contactSystem.viewContacts();
        contactList.setText(contacts);
    }

    private void addContact() {
        String name = JOptionPane.showInputDialog("请输入联系人姓名：");
        String phone = JOptionPane.showInputDialog("请输入联系人电话号码：");
        String address = JOptionPane.showInputDialog("请输入联系人地址：");

        contactSystem.addContact(name, phone, address);
        JOptionPane.showMessageDialog(this, "联系人添加成功。");
    }

    private void updateContact() {
        String name = JOptionPane.showInputDialog("请输入要更新的联系人姓名：");
        String field = JOptionPane.showInputDialog("请输入要更新的字段（姓名、电话或地址）：");
        String value = JOptionPane.showInputDialog("请输入新值：");

        contactSystem.updateContact(name, field, value);
        JOptionPane.showMessageDialog(this, "联系人更新成功。");
    }

    private void deleteContact() {
        String name = JOptionPane.showInputDialog("请输入要删除的联系人姓名：");

        contactSystem.deleteContact(name);
        JOptionPane.showMessageDialog(this, "联系人删除成功。");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ContactSystemUI();
            }
        });
    }
}
