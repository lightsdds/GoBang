import java.awt.*;
import javax.swing.*;

import javafx.scene.input.MouseDragEvent;
import java.awt.Graphics;
import java.awt.event.*;

public class Wuziqi extends JFrame { // 这个是棋盘 的 载体窗口
    WuziqiPad wuziqiPad = new WuziqiPad();

    public Wuziqi() {

        setVisible(true);
        setLayout(null);
        Label label = new Label("单击左键下棋，用右键击棋悔棋", Label.CENTER);
        add(label);
        label.setBounds(70, 55, 440, 20);
        label.setBackground(Color.yellow);
        add(wuziqiPad);
        wuziqiPad.setBounds(70, 90, 440, 440);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 550);
    }

    public static void main(String[] args) {
        Wuziqi wuziqi = new Wuziqi();

    }
}

class WuziqiPad extends JPanel implements ActionListener, MouseListener { // 创建棋盘
    Button startButton = new Button("开始");
    // 定义下棋的位置
    int x = 0, y = 0;
    int a, b;
    // 提示下棋顺序
    TextField t1 = new TextField("请黑子下棋");
    TextField t2 = new TextField();
    // 定义黑棋和白棋两颗棋子
    BlackChess black = new BlackChess(this);
    WhiteChess white = new WhiteChess(this);
    int chessColor = 1;// 设定棋子的颜色，1是黑色，-1是白色

    public WuziqiPad() { // 在构造方法中放置棋盘上面的组件
        setSize(440, 440);
        setBackground(Color.LIGHT_GRAY);
        setLayout(null);
        addMouseListener(this);
        add(startButton);
        startButton.setBounds(5, 5, 60, 30);
        startButton.addActionListener(this);
        add(t1);
        t1.setBounds(90, 5, 90, 30);
        add(t2);
        t2.setBounds(290, 5, 90, 30);
        t1.setEditable(false); // 设置选项为不可用 这样实现下棋提示的变换
        t2.setEditable(false);
        setVisible(true);
    }

    public void paint(Graphics g) { // Graphics类提供用于将对象绘制到显示设备的方法
        super.paint(g);
        // 绘制棋盘上的线
        for (int i = 40; i <= 400; i += 20) {
            g.drawLine(40, i, 400, i);
        }
        for (int j = 40; j <= 400; j += 20) {
            g.drawLine(j, 40, j, 400);
        }
        // 绘制棋盘上的五个小点
        g.fillOval(97, 97, 6, 6);
        g.fillOval(97, 337, 6, 6);
        g.fillOval(337, 97, 6, 6);
        g.fillOval(337, 337, 6, 6);
        g.fillOval(217, 217, 6, 6);
    }

    // 按下鼠标左键可以下棋子
    public void mousePressed(MouseEvent e) {
        if (e.getModifiers() == InputEvent.BUTTON1_MASK) { // BUTTON1_MASK是指鼠标的左键
            // 获取鼠标点击的位置
            x = (int) e.getX();
            y = (int) e.getY();
            a = (x + 10) / 20;
            b = (y + 10) / 20;
            // 要确保棋子落在网格上的位置是两条线的交叉点
        }
        // 在棋盘外面的位置鼠标下不了棋子
        if (x < 40 || y < 40 || x > 380 || y > 380) {
        } else {
            if (chessColor == 1) {
                this.add(black);
                black.setBounds(a * 20 - 10, b * 20 - 10, 20, 20);
                chessColor = chessColor * (-1);
                t1.setText(null);
                t2.setText("请白棋下子");
            } else if (chessColor == -1) {
                this.add(white);
                black.setBounds(a * 20 - 10, b * 20 - 10, 20, 20);
                chessColor = chessColor * (-1);
                t1.setText("请黑棋下子");
                t2.setText(null);
            }
        }
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseClicked(MouseEvent e) {
    }

    // 响应startButton事件
    public void actionPerformed(ActionEvent e) {
        this.removeAll();
        // 移除所有的之后重新添加物件
        add(startButton);
        startButton.setBounds(5, 5, 60, 30);
        add(t1);
        t1.setBounds(90, 5, 90, 30);
        add(t2);
        t2.setBounds(290, 5, 90, 30);
        t1.setText("请黑棋下子");
        t2.setText(null);
    }
}

// 创建黑色棋子
class BlackChess extends Canvas implements MouseListener {
    WuziqiPad pad = null;
    // 定义一个空的棋盘方便下面的鼠标方法可以与棋盘相联系 并且与棋盘进行联动

    public BlackChess(WuziqiPad p) {
        setSize(20, 20);
        // 定义棋子的大小
        pad = p;
        addMouseListener(this);
        setVisible(true);
    }

    // 绘制出棋子的样子
    public void paint(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillOval(0, 0, 20, 20);// 位置先随意定位
    }

    // 鼠标左键单击时可以让棋子下到键盘上的代码
    public void mousePressed(MouseEvent e) {
        // 悔棋操作 单击右键可以悔棋
        if (e.getModifiers() == InputEvent.BUTTON3_MASK) { // BUTTON3_MASK是指鼠标的右键的操作
            pad.remove(this);
            pad.chessColor = 1;
            pad.t2.setText(null);
            pad.t1.setText("请黑子下棋");
        }
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseClicked(MouseEvent e) {
    }
}

// 创建白色棋子
class WhiteChess extends Canvas implements MouseListener {
    // Canvas类可以绘制集合图形
    WuziqiPad pad = null;
    // 定义一个空的棋盘方便下面的鼠标方法可以与棋盘相联系 并且与棋盘进行联动

    public WhiteChess(WuziqiPad p) {
        setSize(20, 20);
        // 定义棋子的大小
        pad = p;
        addMouseListener(this);
        setVisible(true);
    }

    // 绘制出棋子的样子
    public void paint(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillOval(0, 0, 20, 20);// 位置先随意定位
    }

    // 鼠标左键单击时可以让棋子下到键盘上的代码
    public void mousePressed(MouseEvent e) {
        // 悔棋操作 单击右键可以悔棋
        if (e.getModifiers() == InputEvent.BUTTON3_MASK) { // InputEvent.BUTTON3_MASK是指单击右键的操作
            pad.remove(this);
            pad.chessColor = -1;
            pad.t2.setText("请白子下棋");
            pad.t1.setText(null);
        }
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseClicked(MouseEvent e) {
    }
}