import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class Game2048 extends JFrame implements KeyListener {
    private JPanel p;
    private JTable table;
    private JLabel label, scoreshow;
    Boolean hasBlank;//值为true时有空格子
    String[][] params;
    int score = 0;
    String gameResult="你输了！";
    public Game2048() {
        this.setTitle("2048");
        p = new JPanel();
        p.setLayout(null);
        label = new JLabel("SCORE:");
        scoreshow = new JLabel();
        scoreshow.setBounds(60, 0, 100, 50);
        label.setBounds(0, 0, 50, 50);
        table = new JTable(4, 4);
        table.setRowHeight(90);
        table.setEnabled(false);
        table.setBounds(0, 50, 400, 400);
        setFont(new Font("font", Font.PLAIN, 40));
        table.setBorder(new EtchedBorder(Color.BLACK, Color.LIGHT_GRAY));
        p.add(table);
        p.add(label);
        p.add(scoreshow);
        this.add(p);
        this.setLocation(500, 300);
        this.setSize(400, 438);
        this.setVisible(true);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addKeyListener(this);
        init();
    }

    public void init() {
        System.out.println("初始化init()");
        score = 0;
        hasBlank = true;
        params = new String[4][4];
        randomInsert();
        randomInsert();
        setTableValue();
    }

    public void randomInsert() {
        System.out.println(hasBlank);
        while (hasBlank) {
            int x = (int) (Math.random() * 4);
            int y = (int) (Math.random() * 4);
            if (params[x][y] == null) {
                if (Math.random() < 0.5d) {
                    params[x][y] = String.valueOf(2);
                } else {
                    params[x][y] = String.valueOf(4);
                }
                break;
            }
        }
    }

    public void hasBlank() {
        boolean empty=false;//是否跳出循环的标志
        hasBlank=false;
        System.out.println("把hasBlank置为false为了下次的判断");
        System.out.println("判断是否有空格子！hasBlank()");
        for (int i = 0; i < 4&&!empty; i++) {
            for (int j = 0; j < 4; j++) {
                System.out.println("我在hasBlank.for()这里卡住啦！ ");
                if (params[i][j] == null) {
                    empty=true;
                    hasBlank = true;
                    break;

                }
            }
        }

    }

    public void setTableValue() {
        DefaultTableModel model = (DefaultTableModel) this.table.getModel();
        int rowcount = model.getRowCount();
        while (rowcount > 0) {
            model.removeRow(0);
            rowcount--;
        }
//      showParams();
        for (int i = 0; i < 4; i++) {
            model.addRow(params[i]);
        }

        this.table.setModel(model);
        this.table.setFont(new Font("font", Font.PLAIN, 40));

        for (int i = 0; i < 4; i++) {
            table.getColumn(model.getColumnName(i)).setCellRenderer(
                    new MyTableCellRenderrer());
        }
        scoreshow.setText(score + "");
    }

    public String add(String a) {
        if (a != null) {
            return String.valueOf(Integer.valueOf(a) * 2);
        }
        return null;
    }

    public Boolean compara(String a, String b) {
        if (a == null) {
            if (b == null) {
                return true;
            } else {
                return false;
            }
        } else {
            if (b == null) {
                return false;
            } else {
                if (a.equals(b)) {
                    return true;
                } else {
                    return false;
                }
            }
        }
    }
//若返回值为false，则游戏结束
    public Boolean isGameOver() {
        boolean jump=false;//游戏是否结束的标志
        System.out.println("判断游戏是否结束！isGameOver()");
        if (hasBlank == false) {
            for (int i = 0; i < 3&&!jump; i++) {
                for (int j = 0; j < 3; j++) {
                    if (compara(params[i][j], params[i + 1][j])
                            || compara(params[i][j], params[i][j + 1])) {
                        jump=true;
                        break;

                    }
                }
            }
        }else{
            jump=true;
}
        return jump;
    }
//重新开始游戏
    public void start(){    
             System.out.println("重新开始游戏！start()");
         int isRestart = JOptionPane.showConfirmDialog(this,gameResult+" 是否重新开始？", "重新开始", JOptionPane.YES_NO_OPTION);                      if (isRestart == JOptionPane.YES_OPTION) {
                                init();
                        }

}

    public void up() {
        System.out.println("向上移动");
        for (int j = 0; j < 4; j++) {
            upclear();
            for (int i = 0; i < 3; i++) {
                if (params[0][j] != null) {
                    if (compara(params[i][j], params[i + 1][j])) {
                        params[i][j] = add(params[i][j]);
                        params[i + 1][j] = null;
                        if (params[i][j] != null)
                            score += Integer.valueOf(params[i][j]);
                    }
                }
            }
            upclear();
        }
    }

    public void down() {
        System.out.println("向下移动");
        for (int j = 0; j < 4; j++) {
            downclear();
            for (int i = 3; i > 0; i--) {
                if (params[3][j] != null) {
                    if (compara(params[i][j], params[i - 1][j])) {
                        params[i][j] = add(params[i][j]);
                        params[i - 1][j] = null;
                        if (params[i][j] != null)
                            score += Integer.valueOf(params[i][j]);

                    }
                }
            }
            downclear();
        }
    }

    public void left() {
        System.out.println("向左移动");
        for (int i = 0; i < 4; i++) {
            leftclear();
            for (int j = 0; j < 3; j++) {
                if (params[i][0] != null) {
                    if (compara(params[i][j], params[i][j + 1])) {
                        params[i][j] = add(params[i][j + 1]);
                        params[i][j + 1] = null;
                        if (params[i][j] != null)
                            score += Integer.valueOf(params[i][j]);
                    }
                }
            }
            leftclear();
        }
    }

    public void right() {
        System.out.println("向右移动");
        for (int i = 0; i < 4; i++) {
            rightclear();
            for (int j = 3; j > 0; j--) {
                if (params[i][3] != null) {
                    if (compara(params[i][j], params[i][j - 1])) {
                        params[i][j] = add(params[i][j]);
                        params[i][j - 1] = null;
                        if (params[i][j] != null)
                            score += Integer.valueOf(params[i][j]);
                    }
                }
            }
            rightclear();
        }
    }

    public void upclear() {
        System.out.println("清空向上");
        for (int j = 0; j < 4; j++) {
            for (int i = 0; i < 3; i++) {
                int k = i;
                while (k >= 0 && params[k][j] == null) {
                    params[k][j] = params[k + 1][j];
                    params[k + 1][j] = null;
                    k--;
                }
            }
        }
    }

    public void downclear() {
        System.out.println("清空向下");
        for (int j = 0; j < 4; j++) {
            for (int i = 3; i > 0; i--) {
                int k = i;
                while (k > 0 && params[k][j] == null) {
                    params[k][j] = params[k - 1][j];
                    params[k - 1][j] = null;
                    k--;
                }
            }
        }
    }

    public void leftclear() {
        System.out.println("清空左");
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 3; j++) {
                int k = j;
                while (k >= 0 && params[i][k] == null) {
                    params[i][k] = params[i][k + 1];
                    params[i][k + 1] = null;
                    k--;
                }
            }
        }
    }

    public void rightclear() {
        System.out.println("清空向右");
        for (int i = 0; i < 4; i++) {
            for (int j = 3; j > 0; j--) {
                int k = j;
                while (k > 0 && params[i][k] == null) {
                    params[i][k] = params[i][k - 1];
                    params[i][k - 1] = null;
                    k--;
                }
            }
        }
    }

    public Boolean canRorL() {
        if (hasBlank == false) {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (compara(params[i][j], params[i][j + 1])) {
                        return true;
                    }
                }
            }
            return false;
        }
        return true;
    }

    public Boolean canUorD() {
        if (hasBlank == false) {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (compara(params[i][j], params[i + 1][j])) {
                        return true;
                    }
                }
            }
            return false;
        }
        return true;
    }
//流程：
    public void go(){
        randomInsert();
        setTableValue();
        if(isGameOver()==false){
            System.out.println("游戏结束！");
            if(win()){
                System.out.println("你赢了！");
}else{
            System.out.println("你输了！");
}
            start();
}


}
//Interface的方法全部都要覆盖
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_UP) {
//          hasBlank();//先判断完是否能够移动并且移动完再次判断是否有空格子，不然当有相同的格子但是没有空格
//子时系统会认为无法移动，一直卡在判断是否有空格子这里
            if (canUorD()) {
                //hasBlank();
                up();
                hasBlank();
                go();
            }

        }
        if (key == KeyEvent.VK_DOWN) {
//          hasBlank();
            if (canUorD()) {
//              hasBlank();
                down();
                hasBlank();
                go();
            }

        }
        if (key == KeyEvent.VK_LEFT) {
//          hasBlank();
            if (canRorL()) {
    //          hasBlank();
                left();
                hasBlank();
                go();
            }

        }
        if (key == KeyEvent.VK_RIGHT) {
//          hasBlank();
            if (canRorL()) {
        //      hasBlank();
                right();
                hasBlank();
                go();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    	
    }

//返回值为true则游戏胜利
    public Boolean win(){
        System.out.println("判断是否赢了！win()");
        boolean winYes=false;
        for(int i=0;i<4&&!winYes;i++){
            for(int j=0;j<4;j++){
                int value=Integer.parseInt(params[i][j]);
                if(value>=2048){
                    winYes=true;
                    gameResult="你赢了！";
                    break;
                 }
            }
        }
        return winYes;
    }



    public static void main(String[] args) {
        new Game2048();
    }

    class MyTableCellRenderrer extends DefaultTableCellRenderer {
        // 设置单元格内容居中
        @Override
        public void setHorizontalAlignment(int alignment) {
            super.setHorizontalAlignment(SwingConstants.CENTER);
        }

        // 设置单元格颜色
        @Override
        public Component getTableCellRendererComponent(JTable table,
                Object value, boolean isSelected, boolean hasFocus, int row,
                int column) {
            Component component = super.getTableCellRendererComponent(table, value,
                    isSelected, hasFocus, row, column);
            int v = 0;
            if (value != null && value != "")
                v = Integer.valueOf(value.toString());
            switch (v) {
            case 2:
                setBackground(new Color(255, 237, 210));
                break;
            case 4:
                setBackground(new Color(255, 228, 180));
                break;
            case 8:
                setBackground(new Color(255, 218, 185));
                break;
            case 16:
                setBackground(new Color(249, 175, 116));
                break;
            case 32:
                setBackground(new Color(248, 149, 90));
                break;
            case 64:
                setBackground(new Color(249, 94, 50));
                break;
            case 128:
                setBackground(new Color(239, 207, 108));
                break;
            case 256:
                setBackground(new Color(239, 207, 99));
                break;
            case 512:
                setBackground(new Color(239, 203, 82));
                break;
            case 1024:
                setBackground(new Color(239, 199, 57));
                break;
            case 2048:
                setBackground(new Color(239, 195, 41));
                break;
            case 4096:
                setBackground(new Color(255, 100, 57));
                break;
            default:
                component.setBackground(Color.LIGHT_GRAY);
                break;
            }
            return component;
        }
    }

}
