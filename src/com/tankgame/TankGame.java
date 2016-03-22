package com.tankgame;

/**
 * Created by Vincent on 2016/3/22 0022.
 */
/**
 * ???????????¨®?????¡¤
 * ¡Á¡Â????????
 */
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;

public class TankGame extends JFrame implements ActionListener,Runnable{

    MyPanel mp=null;

    JMenuBar jmb=null;
    JMenu jm=null;


    JMenuItem jmi=null;
    JMenuItem jmi2=null;
    JMenuItem jmi3=null;
    JMenuItem jmi4=null;

    MyStartPanel myStartPanel=null;
    MyEndPanel myEndPanel=null;
    SecondLevelPanel secondLevelPanel=null;


    int flag=1;
    int times=0;

    //?¡§???????????????¨²?¨¹????¡À?????????Recorder?¨¤?????????????¨²?¨¹??
    int myTankLife=0;
    //¡Á¡Â??????
    int enemyTankNum;

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        TankGame tankgame=new TankGame();
        Thread t=new Thread(tankgame);
        t.start();
    }
    public TankGame()
    {



        //???¡§?????¡ã??????
        jmb=new JMenuBar();
        jm=new JMenu("play(P)");
        jm.setMnemonic('P');
        jmi=new JMenuItem("newGame(N)");
        jmi.setMnemonic('N');
        jmi.addActionListener(this);
        jmi.setActionCommand("newGame");
        jmi2=new JMenuItem("exitGame(E)");
        jmi2.setMnemonic('E');
        jmi2.addActionListener(this);
        jmi2.setActionCommand("exitGame");
        jmi3=new JMenuItem("saveExit(T)");
        jmi3.setMnemonic('T');
        jmi3.addActionListener(this);
        jmi3.setActionCommand("saveExit");
        this.setJMenuBar(jmb);
        jmi4=new JMenuItem("continueToPlay(C)");
        jmi4.setMnemonic('C');
        jmi4.addActionListener(this);
        jmi4.setActionCommand("continueToPlay");
        jmb.add(jm);
        jm.add(jmi);
        jm.add(jmi2);
        jm.add(jmi3);
        jm.add(jmi4);




        //?¨¨????????¡ã?
        myStartPanel=new MyStartPanel();
        this.add(myStartPanel);

        //?¨¨???¨¢????¡ã?
        myEndPanel=new MyEndPanel();

        //?¨¨??????????????¡ã?
        secondLevelPanel=new SecondLevelPanel();





        Thread t1=new Thread(myStartPanel);
        t1.start();



        this.setSize(1200, 900);
        this.setLocation(300, 50);
        this.setTitle("BitRat");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);





    }
    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        if(e.getActionCommand().equals("newGame"))
        {
            //????????
            Recorder.ReadData();

            mp=new MyPanel("newGame");
            //????MyPanel
            Thread t2=new Thread(mp);
            t2.start();

            //????????¡ã?
            this.remove(myStartPanel);
            this.add(mp);

            this.addKeyListener(mp);
            //????????¡ã??¡§¡À?????
            this.setVisible(true);
        }
        else if(e.getActionCommand().equals("exitGame"))
        {

            Recorder.SavaData();

            System.exit(0);
        }
        else if(e.getActionCommand().equals("saveExit"))
        {
            //?¡§??Recorder?¨¤???¡À??¡ã?????????????????
            Recorder.setEns(mp.ets);

            Recorder.saveAndExit();

            System.exit(0);
        }
        else if(e.getActionCommand().equals("continueToPlay"))
        {

            mp=new MyPanel("continueToPlay");

            //????MyPanel
            Thread t3=new Thread(mp);
            t3.start();

            //????????¡ã?
            this.remove(myStartPanel);
            this.add(mp);

            this.addKeyListener(mp);
            //????????¡ã??¡§¡À?????
            this.setVisible(true);
        }
    }
    @Override
    public void run() {
        // TODO Auto-generated method stub
        while(true)
        {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            myTankLife=Recorder.getMyLife();
            enemyTankNum=Recorder.getEnemyTankNum();
            if(myTankLife<=0)
            {
                this.remove(mp);
                this.add(myEndPanel);

                Thread t4=new Thread(myEndPanel);
                t4.start();

                //????????¡ã??¡§¡À?????
                this.setVisible(true);
                //???????¡¤(¡À?????????remove?¨¢???¡¤???????¨¢???¨ª)
                break;
            }

            if(enemyTankNum<=0&&flag>0)
            {
                this.remove(mp);
                this.add(secondLevelPanel);

                Thread t5=new Thread(secondLevelPanel);
                t5.start();

                this.setVisible(true);
                //¡Á¡Â??????
                flag--;
            }
            if(flag<=0)
            {
                times++;
                if(times>=20)
                {
                    flag++;
                    Vector<Enemy> ens=new Vector<Enemy>();

                    this.remove(secondLevelPanel);

                    Recorder.setEnemyTankNum(3);
                    Recorder.setEns(ens);
                    MyPanel mp=new MyPanel("newGame");
                    this.add(mp);

                    this.addKeyListener(mp);

                    Thread t6=new Thread(mp);
                    t6.start();

                    this.setVisible(true);

                    break;
                }
            }
        }
    }

}

//?¡§????????¡ã??¨¤
class MyStartPanel extends JPanel implements Runnable
{
    int times=0;
    public void paint(Graphics g)
    {
        super.paint(g);
        g.fillRect(0, 0, 800, 600);

        //????¡Á????????????????????????????????????¡¤
        if(times%2==0)
        {
            g.setColor(Color.green);

            Font font=new Font("Î¢ÈíÑÅºÚ",Font.BOLD,55);
            g.setFont(font);

            g.drawString("Stage:1",300 ,280 );
        }
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        while(true)
        {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            times++;
            this.repaint();
        }
    }


}

//?¡§??????????????¡ã?
class SecondLevelPanel extends JPanel implements Runnable
{
    int times=0;
    public void paint(Graphics g)
    {
        super.paint(g);
        g.fillRect(0, 0, 800, 600);

        //????¡Á????????????????????????????????????¡¤
        if(times%2==0)
        {
            g.setColor(Color.green);

            Font font=new Font("Î¢ÈíÑÅºÚ",Font.BOLD,55);
            g.setFont(font);

            g.drawString("Stage:2",300 ,280 );
        }
    }
    @Override
    public void run() {
        // TODO Auto-generated method stub
        while(true)
        {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            times++;
            this.repaint();
        }
    }
}


//?¡§???¨¢????????¡ã?
class MyEndPanel extends JPanel implements Runnable
{
    int times=0;



    public void paint(Graphics g)
    {
        super.paint(g);
        g.fillRect(0, 0, 800, 600);

        if(times%2==0)
        {
            g.setColor(Color.green);
            Font font=new Font("Î¢ÈíÑÅºÚ",Font.BOLD,55);
            g.setFont(font);
            g.drawString("End", 350, 280);
        }
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        while(true)
        {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            times++;

            this.repaint();
        }
    }

}


class MyPanel extends JPanel implements KeyListener,Runnable
{


    //?¡§??????????
    Hero hero=null;
    //?¡§????????????
    Vector<Enemy> ets=new Vector<Enemy>();

    Vector<Nodes> nodes=new Vector<Nodes>();
    //?¡§????????????????





    public MyPanel(String flag)//flag???????????¡ì????¡Á??????????????????????¡¤
    {

        if(flag.equals("newGame"))
        {
            int ensize=Recorder.getEnemyTankNum();


            hero=new Hero(325,585);
            hero.setDirect(0);

            //????Recorder?¨¤???????????¡À??????
            Recorder.setMyHero(hero);

            //????????????????
            for(int i=0;i<ensize;i++)
            {
                //???¡§?????????????¨®
//				Enemy en=new Enemy((i+1)*100,100);
                Enemy en=new Enemy((int)(Math.random()*785),(int)(Math.random()*285));


                ets.add(en);

                //????Enemy?¨¤???????????¡À??????
                en.setEnemyNumber(ets);
                Recorder.setEnemyTankNum(i+1);

                Thread t2=new Thread(en);
                t2.start();

                //?¡§??????????¡Á???
                Bullet bt=null;

                //???????????¨ª??¡Á???
                switch(en.direct)
                {

                    case 0:
                        bt=new Bullet(en.x,en.y-15,0);
                        en.ss.add(bt);
                        break;
                    case 1:
                        bt=new Bullet(en.x+15,en.y,1);
                        en.ss.add(bt);
                        break;
                    case 2:
                        bt=new Bullet(en.x,en.y+15,2);
                        en.ss.add(bt);
                        break;
                    case 3:
                        bt=new Bullet(en.x-15,en.y,1);
                        en.ss.add(bt);
                        break;
                }
                Thread t3=new Thread(bt);
                t3.start();
            }



        }else{

            //????????????????????
            nodes=Recorder.ReadLastData();


            hero=new Hero(Recorder.getMyHero().x,Recorder.getMyHero().y);
            hero.setDirect(Recorder.getMyHero().direct);

            //????Recorder?¨¤???????????¡À??????
            Recorder.setMyHero(hero);

            for(int i=0;i<nodes.size();i++)
            {
                Nodes node=nodes.get(i);
                //???¡§?????????????¨®
                Enemy en=new Enemy(node.x,node.y);
                en.setDirect(node.direct);
                ets.add(en);

                //????Enemy?¨¤???????????¡À??????
                en.setEnemyNumber(ets);


                Thread t2=new Thread(en);
                t2.start();

                //?¡§??????????¡Á???
                Bullet bt=null;

                //???????????¨ª??¡Á???
                switch(en.direct)
                {

                    case 0:
                        bt=new Bullet(en.x,en.y-15,0);
                        en.ss.add(bt);
                        break;
                    case 1:
                        bt=new Bullet(en.x+15,en.y,1);
                        en.ss.add(bt);
                        break;
                    case 2:
                        bt=new Bullet(en.x,en.y+15,2);
                        en.ss.add(bt);
                        break;
                    case 3:
                        bt=new Bullet(en.x-15,en.y,1);
                        en.ss.add(bt);
                        break;
                }
                Thread t3=new Thread(bt);
                t3.start();
            }
        }

        AePlayWave apw1=new AePlayWave("D:\\AppLibs\\TankGame_Java\\start.wav");
        apw1.start();

    }

    public void paint(Graphics g)
    {
        super.paint(g);
        g.fillRect(0, 0, 800, 600);

        //?????¨¢??????
        this.RecordInfo(g);

        //??¡Á?????????
        if(this.hero.isLive)
        {
            //??????????
            this.drawTank(hero.getX(), hero.getY(), g, hero.getDirect(), 0);
            //??¡Á?????¡Á???
            for(int i=0;i<hero.ss.size();i++)
            {
                Bullet bt=hero.ss.get(i);

                //???¨¬????¡Á???
                if(bt!=null&&bt.isLive)
                {
                    this.drawBullet(bt.getX(), bt.getY(), g);
                }
                else
                {
                    this.hero.ss.remove(bt);
                }
            }
        }


        //????????????
        for(int i=0;i<ets.size();i++)
        {
            //????????????
            Enemy en=ets.get(i);
            //??????????????¡¤???¡Á?
            if(en!=null&&en.isLive)
            {
                this.drawTank(en.getX(),en.getY(), g, en.getDirect(), 1);

                //????????????¡Á???
                for(int j=0;j<en.ss.size();j++)
                {
                    //????????¡Á???
                    Bullet bt=en.ss.get(j);

                    //????¡Á?????¡¤???¡Á?
                    if(bt.isLive==true)
                    {
                        this.drawBullet(bt.x, bt.y, g);
                    }else{
                        en.ss.remove(bt);
                    }
                }


            }
            else
            {
                this.ets.remove(en);
            }
        }


    }

    //??????
    public void drawTank(int x,int y,Graphics g,int direct,int color)
    {
        switch(color)
        {
            case 0:
                g.setColor(Color.yellow);
                break;
            case 1:
                g.setColor(Color.red);
                break;
        }
        switch(direct)
        {
            //???¨°????????
            case 0:
                g.fill3DRect(x-10, y-15, 5, 30, false);
                g.fill3DRect(x+5, y-15, 5, 30, false);
                g.fill3DRect(x-5, y-10, 10, 20, false);
                g.fillOval(x-5, y-5, 10, 10);
                g.drawLine(x, y, x, y-15);
                break;
            //???¨°????????
            case 1:
                g.fill3DRect(x-15, y-10, 30, 5, false);
                g.fill3DRect(x-15, y+5, 30, 5, false);
                g.fill3DRect(x-10, y-5, 20, 10, false);
                g.fillOval(x-5, y-5, 10, 10);
                g.drawLine(x, y, x+15, y);
                break;
            //???¨°????????
            case 2:
                g.fill3DRect(x-10, y-15, 5, 30, false);
                g.fill3DRect(x+5, y-15, 5, 30, false);
                g.fill3DRect(x-5, y-10, 10, 20, false);
                g.fillOval(x-5, y-5, 10, 10);
                g.drawLine(x, y, x, y+15);
                break;
            //???¨°¡Á¨®??????
            case 3:
                g.fill3DRect(x-15, y-10, 30, 5, false);
                g.fill3DRect(x-15, y+5, 30, 5, false);
                g.fill3DRect(x-10, y-5, 20, 10, false);
                g.fillOval(x-5, y-5, 10, 10);
                g.drawLine(x, y, x-15, y);
                break;
        }
    }


    //??¡Á???
    public void drawBullet(int x,int y,Graphics g)
    {
        g.setColor(Color.gray);
        g.fillOval(x-2, y-2, 4, 4);
    }



    //????¡Á?????¡¤??¡Â??????
    public void hitEnemy(Bullet bt,Tank tank)
    {
        //??????????????¡¤??¨°
        switch(tank.direct)
        {

            case 0:
            case 2:
                //¡Á????¡Â??????
                if(bt.x>tank.x-10&&bt.x<tank.x+10&&bt.y>tank.y-15&&bt.y<tank.y+15)
                {
                    bt.isLive=false;
                    tank.isLive=false;
                    AePlayWave apw2=new AePlayWave("D:\\AppLibs\\TankGame_Java\\bomb.wav");
                    apw2.start();
                }
                break;
            case 1:
            case 3:
                //¡Á????¡Â??????
                if(bt.x>tank.x-15&&bt.x<tank.x+15&&bt.y>tank.y-10&&bt.y<tank.y+10)
                {
                    bt.isLive=false;
                    tank.isLive=false;
                    AePlayWave apw2=new AePlayWave("D:\\AppLibs\\TankGame_Java\\bomb.wav");
                    apw2.start();
                }
                break;
        }
    }



    //??????????????¡Á?????¡¤??¡Â????????????
    public void hitEnemyTank()
    {
        for(int i=0;i<this.hero.ss.size();i++)
        {
            //????????¡Á???
            Bullet bullet=hero.ss.get(i);
            //????¡Á?????¡¤???¡Á?
            if(bullet.isLive)
            {
                for(int j=0;j<ets.size();j++)
                {
                    //????????????????
                    Enemy en=ets.get(j);
                    //??????????????¡¤???¡Á?
                    if(en.isLive)
                    {
                        this.hitEnemy(bullet, en);
                        if(en.isLive==false)
                        {
                            //?¨¢????????????????????
                            Recorder.enemyReduce();
                            //¡À??¡Â????????????????
                            Recorder.hitedEnemyIncrease();
                        }
                    }
                }
            }
        }
    }


    //??????????¡Á?????¡¤??¡Â??????????
    public boolean hitMyTank()
    {
        for(int i=0;i<ets.size();i++)
        {
            Enemy en=ets.get(i);
            if(en.isLive)
            {
                for(int j=0;j<en.ss.size();j++)
                {
                    Bullet bt=en.ss.get(j);
                    if(bt.isLive)
                    {
                        this.hitEnemy(bt, hero);
                        if(hero.isLive==false)
                        {
                            //?¨¢?????????????????¨²?¨¹??????
                            Recorder.myLifeRnduce();
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    //???????¡¤??????¡À????¨¢??????
    public void RecordInfo(Graphics g)
    {
        //???¨¢???????????????????????¨¢??
        this.drawTank(100, 650, g, 0, 1);
        //????????????????
        this.drawTank(200, 650, g, 0, 0);
        //?????????????¡Â??????????
        this.drawTank(850, 150, g, 0, 1);

        g.setColor(Color.black);
        g.drawString(Recorder.getEnemyTankNum()+"", 130, 650);
        g.drawString(Recorder.getMyLife()+"", 230, 650);
        g.drawString(Recorder.getMeHitEnemyNum()+"",880, 150);


        g.drawRect(950, 250, 50, 50);
        g.drawRect(950, 320, 50, 50);
        g.drawRect(880, 320, 50, 50);
        g.drawRect(1020, 320, 50, 50);
        g.drawRect(950, 450, 50, 50);

        Font font1=new Font("Î¢ÈíÑÅºÚ",Font.BOLD,40);
        g.setFont(font1);
        g.drawString("W", 965, 290);
        g.drawString("A", 895, 360);
        g.drawString("S", 965, 360);
        g.drawString("D", 1035, 360);
        g.drawString("J", 965, 490);

        Font font2=new Font("Î¢ÈíÑÅºÚ",Font.BOLD,25);
        g.setFont(font2);
        g.drawString("Best Score:", 830, 100);
        g.drawString("Bited:", 830, 250);
        g.drawString("Key:", 830, 430);

    }



    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void keyPressed(KeyEvent e) {
        // TODO Auto-generated method stub


        //?¨°??
        if(e.getKeyCode()==KeyEvent.VK_W)
        {
            this.hero.setDirect(0);
            this.hero.moveUp();
        }
        //?¨°??
        else if(e.getKeyCode()==KeyEvent.VK_D)
        {
            this.hero.setDirect(1);
            this.hero.moveRight();
        }
        //?¨°??
        else if(e.getKeyCode()==KeyEvent.VK_S)
        {
            this.hero.setDirect(2);
            this.hero.moveDown();
        }
        //?¨°¡Á¨®
        else if(e.getKeyCode()==KeyEvent.VK_A)
        {
            this.hero.setDirect(3);
            this.hero.moveLeft();
        }


        //?????¡§?¨°??¡Á?????
        else if(e.getKeyCode()==KeyEvent.VK_J)
        {

            if(this.hero.ss.size()<=20)
            {
                this.hero.fire();
            }
        }

        this.repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        while(true)
        {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            //??????????????¡Á?????¡¤??¡Â??????????
            this.hitEnemyTank();


            //??????????????¡Á?????¡¤?????????????
            if(this.hitMyTank()==true&&Recorder.getMyLife()!=0)
            {
                this.hero.isLive=true;
                this.hero=new Hero(325,585);
                this.hero.setDirect(0);
            }else{
//				break;

            }
            this.repaint();
        }

    }
}