package com.tankgame;

/**
 * Created by Vincent on 2016/3/22 0022.
 */

import java.util.Vector;
import java.io.*;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;


class Tank
{
    //??????¡Á?¡À¨º
    int x=0;
    int y=0;
    //??????¡¤??¨°(0?¨°????1?¨°????2?¨°????3?¨°¡Á¨®)
    int direct=(int)(Math.random()*4);

    //??????????
    int speed=1;

    //?¡§????????¡¤???¡Á?
    boolean isLive=true;

    //??????????
    int color;



    public Tank(int x,int y)
    {
        this.x=x;
        this.y=y;
    }
    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }
    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }
    public int getDirect() {
        return direct;
    }
    public void setDirect(int direct) {
        this.direct = direct;
    }
    public int getSpeed() {
        return speed;
    }
    public void setSpeed(int speed) {
        this.speed = speed;
    }
    public int getColor() {
        return color;
    }
    public void setColor(int color) {
        this.color = color;
    }
}

//?????????¨¤
class Hero extends Tank
{
    //?¡§????????????¡Á???
    Vector<Bullet> ss=new Vector<Bullet>();
    Bullet bt=null;



    public Hero(int x,int y)
    {
        super(x,y);
    }

    //?¨°??¡Á???
    public void fire()
    {

        switch(this.getDirect())
        {
            case 0:
                bt=new Bullet(x,y-15,0);
                ss.add(bt);
                break;
            case 1:
                bt=new Bullet(x+15,y,1);
                ss.add(bt);
                break;
            case 2:
                bt=new Bullet(x,y+15,2);
                ss.add(bt);
                break;
            case 3:
                bt=new Bullet(x-15,y,3);
                ss.add(bt);
                break;
        }
        AePlayWave apw2=new AePlayWave("D:\\AppLibs\\TankGame_Java\\fire.wav");
        apw2.start();
        Thread t=new Thread(bt);
        t.start();

    }

    //?????¨°??????
    public void moveUp()
    {
        if(y>15)
        {
            y-=3*speed;
        }
    }
    //?????¨°??????
    public void moveRight()
    {
        if(x<785)
        {
            x+=3*speed;
        }
    }
    //?????¨°??????
    public void moveDown()
    {
        if(y<585)
        {
            y+=3*speed;
        }
    }
    //?????¨°¡Á¨®????
    public void moveLeft()
    {
        if(x>15)
        {
            x-=3*speed;
        }
    }
}


//¡Á???
class Bullet implements Runnable
{
    //?¡§??¡Á???¡Á?¡À¨º
    int x=0;
    int y=0;

    int direct;

    //¡Á?????¡¤?????
    boolean isLive=true;

    //?¡§??¡Á?????????
    int bulletspeed=1;

    //???¨¬
    public Bullet(int x,int y,int direct)
    {
        this.x=x;
        this.y=y;
        this.direct=direct;
    }

    public void run()
    {
        while(true)
        {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            switch(direct)
            {
                case 0:
                    y-=bulletspeed;
                    break;
                case 1:
                    x+=bulletspeed;
                    break;
                case 2:
                    y+=bulletspeed;
                    break;
                case 3:
                    x-=bulletspeed;
                    break;
            }
            if(!isLive)
            {
                break;
            }
            if(y<-1||y>601||x<-1||x>801)
            {
                isLive=false;
                break;
            }
        }
    }



    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}


//???????????¨¤
class Enemy extends Tank implements Runnable
{

    //?¡§????????¡¤???¡Á?
//	boolean isLive=true;

    //?¡§????????????¡Á???
    Vector<Bullet> ss=new Vector<Bullet>();



    //?¡§???????¨°??????¡¤?????Mypanel???????¡À????????????????
    Vector<Enemy> ets=new Vector<Enemy>();

    public Enemy(int x, int y)
    {
        super(x, y);
    }


    //????Mypanel?????¡À????????????????
    public void setEnemyNumber(Vector<Enemy> vv)
    {
        this.ets=vv;
    }

    //??????????????¡¤??¨¤¡Á?
    public boolean isTouchEnemyTank()
    {
        //???????????????????????¨¤¡Á?
        boolean b=false;

        switch(this.direct)
        {
            //¡¤??¨°?¨°??
            case 0:
            case 2:
                for(int i=0;i<ets.size();i++)
                {
                    Enemy en=ets.get(i);
                    if(en!=this)
                    {
                        //????¡À???????¡¤???¡Á¨®??¡Á??¨°???????????¨¤¡Á?
                        if(x-10>en.x-15&&x-10<en.x+15&&y-15>en.y-10&&y-15<en.y+10)
                        {
                            return true;
                        }
                        if(this.x+10>en.x-15&&this.x+10<en.x+15&&this.y-15>en.y-10&&this.y-15<en.y+10)
                        {
                            return true;
                        }
                        if(this.x-10>en.x-15&&this.x-10<en.x+15&&this.y>en.y-10&&this.y<en.y+10)
                        {
                            return true;
                        }
                        if(this.x+10>en.x-15&&this.x+10<en.x+15&&this.y>en.y-10&&this.y<en.y+10)
                        {
                            return true;
                        }
                        if(this.x-10>en.x-15&&this.x-10<en.x+15&&this.y+15>en.y-10&&this.y+15<en.y+10)
                        {
                            return true;
                        }
                        if(this.x+10>en.x-15&&this.x+10<en.x+15&&this.y+15>en.y-10&&this.y+15<en.y+10)
                        {
                            return true;
                        }

                        //????¡À???????¡¤???????¡Á??¨°???????????¨¤¡Á?
                        if(this.x-10>en.x-10&&this.x-10<en.x+10&&this.y-15>en.y-15&&this.y-15<en.y+15)
                        {
                            return true;
                        }
                        if(this.x+10>en.x-10&&this.x+10<en.x+10&&this.y-15>en.y-15&&this.y-15<en.y+15)
                        {
                            return true;
                        }
                        if(this.x-10>en.x-10&&this.x-10<en.x+10&&this.y+15>en.y-15&&this.y+15<en.y+15)
                        {
                            return true;
                        }
                        if(this.x+10>en.x-10&&this.x+10<en.x+10&&this.y+15>en.y-15&&this.y+15<en.y+15)
                        {
                            return true;
                        }
                    }
                }

                break;
            case 1:
            case 3:
                for(int i=0;i<ets.size();i++)
                {
                    Enemy en=ets.get(i);
                    if(en!=this)
                    {
                        //????¡À???????¡¤???????¡Á??¨°?????????????¨¤¡Á?
                        if(this.x-15>en.x-10&&this.x-15<en.x+10&&this.y-10>en.y-15&&this.y-10<en.y+15)
                        {
                            return true;
                        }
                        if(this.x>en.x-10&&this.x<en.x+10&&this.y-10>en.y-15&&this.y-10<en.y+15)
                        {
                            return true;
                        }
                        if(this.x+15>en.x-10&&this.x+15<en.x+10&&this.y-10>en.y-15&&this.y-10<en.y+15)
                        {
                            return true;
                        }
                        if(this.x-15>en.x-10&&this.x-15<en.x+10&&this.y+10>en.y-15&&this.y+10<en.y+15)
                        {
                            return true;
                        }
                        if(this.x>en.x-10&&this.x<en.x+10&&this.y+10>en.y-15&&this.y+10<en.y+15)
                        {
                            return true;
                        }
                        if(this.x+15>en.x-10&&this.x+15<en.x+10&&this.y+10>en.y-15&&this.y+10<en.y+15)
                        {
                            return true;
                        }


                        //????¡À???????¡¤???¡Á¨®??¡Á??¨°???????????¨¤¡Á?
                        if(this.x-15>en.x-15&&this.x-15<en.x+15&&this.y-10>en.y-10&&this.y-10<en.y+10)
                        {
                            return true;
                        }
                        if(this.x+15>en.x-15&&this.x+15<en.x+15&&this.y-10>en.y-10&&this.y-10<en.y+10)
                        {
                            return true;
                        }
                        if(this.x-15>en.x-15&&this.x-15<en.x+15&&this.y+10>en.y-10&&this.y+10<en.y+10)
                        {
                            return true;
                        }
                        if(this.x+15>en.x-15&&this.x+15<en.x+15&&this.y+10>en.y-10&&this.y+10<en.y+10)
                        {
                            return true;
                        }

                    }
                }
                break;
        }
        return b;
    }


    @Override
    public void run() {
        // TODO Auto-generated method stub
        while(true)
        {
            switch(direct)
            {
                case 0:
                    for(int i=0;i<50;i++)
                    {
                        //??????????¡Á????¡À?????¨°??????
                        if(y>15&&!this.isTouchEnemyTank())
                        {
                            y-=speed;
                        }else{
                            this.setDirect(2);
                            y+=speed;
                            break;
                        }
                        //???????????????¨¹??????
                        try {
                            Thread.sleep(50);
                        } catch (Exception e) {
                            // TODO: handle exception
                        }
                    }
                    break;
                case 1:
                    for(int i=0;i<50;i++)
                    {
                        if(x<785&&!this.isTouchEnemyTank())
                        {
                            x+=speed;
                        }else{
                            this.setDirect(3);
                            x-=speed;
                            break;
                        }
                        try {
                            Thread.sleep(50);
                        } catch (Exception e) {
                            // TODO: handle exception
                        }
                    }
                    break;
                case 2:
                    for(int i=0;i<50;i++)
                    {
                        if(y<585&&!this.isTouchEnemyTank())
                        {
                            y+=speed;
                        }else{
                            this.setDirect(0);
                            y-=speed;
                            break;
                        }
                        try {
                            Thread.sleep(50);
                        } catch (Exception e) {
                            // TODO: handle exception
                        }
                    }
                    break;
                case 3:
                    for(int i=0;i<50;i++)
                    {
                        if(x>15&&!this.isTouchEnemyTank())
                        {
                            x-=speed;
                        }else{
                            this.setDirect(1);
                            x+=speed;
                            break;
                        }
                        try {
                            Thread.sleep(50);
                        } catch (Exception e) {
                            // TODO: handle exception
                        }
                    }
                    break;
            }


            //?¨ª??¡Á???
            if(isLive)
            {
                if(ss.size()<5)
                {
                    Bullet bt=null;


                    switch(direct)
                    {
                        case 0:
                            bt=new Bullet(x,y-15,0);
                            ss.add(bt);
                            break;
                        case 1:
                            bt=new Bullet(x+10,y,1);
                            ss.add(bt);
                            break;
                        case 2:
                            bt=new Bullet(x,y+15,2);
                            ss.add(bt);
                            break;
                        case 3:
                            bt=new Bullet(x-10,y,3);
                            ss.add(bt);
                            break;
                    }
                    Thread t3=new Thread(bt);
                    t3.start();
                }
            }


            //???¨²??¡À?????????¡¤??¨°
            this.direct=(int)(Math.random()*4);


            //??????????¡¤???????
            if(isLive==false)
            {
                break;
            }
        }
    }
}


//?????????¨¤
class Recorder
{
    //?¡§??????????????
    private static int myLife=3;
    //?¡§????¡Á???????????????
    private static int enemyTankNum=10;
    //?¡§???????¡Â??????????????????
    private static int meHitEnemyNum=0;

    //?¡§???????¨°????¡¤???JPanel?¨¤???????¡À??????????????
    private static Vector<Enemy> ens=new Vector<Enemy>();

    //?¡§??????Hero???¨®??????myPanel?¨¤???¡À??????????????
    private static Hero myHero=null;

    //?¡§??????Nodes?¨°????¡À?????????????????
    private static Vector<Nodes> nodes=new Vector<Nodes>();



    private static FileWriter fw=null;
    private static BufferedWriter bw=null;

    private static FileReader fr=null;
    private static BufferedReader br=null;


    public static int getMyLife() {
        return myLife;
    }
    public static void setMyLife(int myLife) {
        Recorder.myLife = myLife;
    }
    public static int getEnemyTankNum() {
        return enemyTankNum;
    }
    public static void setEnemyTankNum(int enemyTankNum) {
        Recorder.enemyTankNum = enemyTankNum;
    }
    public static int getMeHitEnemyNum() {
        return meHitEnemyNum;
    }
    public static void setMeHitEnemyNum(int meHitEnemyNum) {
        Recorder.meHitEnemyNum = meHitEnemyNum;
    }
    public static Vector<Enemy> getEns() {
        return ens;
    }
    public static void setEns(Vector<Enemy> en) {
        ens = en;
    }
    public static Hero getMyHero() {
        return myHero;
    }
    public static void setMyHero(Hero myHero) {
        Recorder.myHero = myHero;
    }



    //????????????????1
    public static void enemyReduce()
    {
        enemyTankNum--;
    }
    //?????????¨²?¨¹????1
    public static void myLifeRnduce()
    {
        myLife--;
    }
    //???????????¡Â??????????????????1
    public static void hitedEnemyIncrease()
    {
        meHitEnemyNum++;
    }
    //??????????????????????(?????????????¨¹????)
    public static void SavaData()
    {
        try {
            File f=new File("D:\\AppLibs\\TankGame_Java\\TankGameScore.txt");
            if(!f.exists())
            {
                try {
                    f.createNewFile();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            else{
                System.out.println("??????????????");
            }
            fw=new FileWriter(f);
            bw=new BufferedWriter(fw);

            bw.write(meHitEnemyNum+"\r\n");

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{

            try {
                bw.close();
                fw.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }


    //???????????????????????¡¤?¡§?????????????????¨¹??????
    public static void saveAndExit()
    {
        try {
            File f=new File("D:\\AppLibs\\TankGame_Java\\TankGameScore.txt");
            if(!f.exists())
            {
                try {
                    f.createNewFile();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            else{
                System.out.println("??????????????");
            }
            fw=new FileWriter(f);
            bw=new BufferedWriter(fw);

            bw.write(meHitEnemyNum+"\r\n");
            //????????????????????
            bw.write(myHero.x+" "+myHero.y+" "+myHero.direct+"\r\n");

            for(int i=0;i<ens.size();i++)
            {
                Enemy en=ens.get(i);

                if(en.isLive==true)
                {
                    String enemyInformation="";
                    enemyInformation=en.x+" "+en.y+" "+en.direct;
                    bw.write(enemyInformation+"\r\n");
                }
            }


        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{

            try {
                bw.close();
                fw.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }


    //????????
    public static void ReadData()
    {
        try {
            fr=new FileReader("D:\\AppLibs\\TankGame_Java\\TankGameScore.txt");
            br=new BufferedReader(fr);
            String s="";
            try {
                s=br.readLine();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            meHitEnemyNum=Integer.parseInt(s);

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            try {
                br.close();
                fr.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

    }


    //????????????????????
    public static Vector<Nodes> ReadLastData()
    {
        try {
            try {
                fr=new FileReader("D:\\AppLibs\\TankGame_Java\\TankGameScore.txt");
            } catch (FileNotFoundException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            br=new BufferedReader(fr);
            String s="";
            try {
                s=br.readLine();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            meHitEnemyNum=Integer.parseInt(s);

            try {
                s=br.readLine();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            String myHeroNode[]=s.split(" ");
            myHero=new Hero(Integer.parseInt(myHeroNode[0]),Integer.parseInt(myHeroNode[1]));
            myHero.setDirect(Integer.parseInt(myHeroNode[2]));
            try {
                while((s=br.readLine())!=null)
                {
                    String enemyNode[]=s.split(" ");
                    Nodes node=new Nodes(Integer.parseInt(enemyNode[0]),Integer.parseInt(enemyNode[1]),Integer.parseInt(enemyNode[2]));
                    nodes.add(node);
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }finally{
            try {
                br.close();
                fr.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

        return nodes;
    }

}

//?¡§??????Node?¨¤¡À?????????????????

class Nodes
{
    int x;
    int y;
    int direct;
    public Nodes(int x,int y,int direct)
    {
        this.x=x;
        this.y=y;
        this.direct=direct;
    }
}


//??¡¤??¨´?????¨¤
class AePlayWave extends Thread {

    private String filename;
    public AePlayWave(String wavfile) {
        filename = wavfile;

    }

    public void run() {

        File soundFile = new File(filename);
        AudioInputStream audioInputStream = null;
        try {
            audioInputStream = AudioSystem.getAudioInputStream(soundFile);
        } catch (UnsupportedAudioFileException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        AudioFormat format = audioInputStream.getFormat();
        SourceDataLine auline = null;
        DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);

        try {
            auline = (SourceDataLine) AudioSystem.getLine(info);
            auline.open(format);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        auline.start();
        int nBytesRead = 0;
        //????????
        byte[] abData = new byte[512];

        try {
            while (nBytesRead != -1) {
                nBytesRead = audioInputStream.read(abData, 0, abData.length);
                if (nBytesRead >= 0)
                    auline.write(abData, 0, nBytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        } finally {
            auline.drain();
            auline.close();
        }

    }
}