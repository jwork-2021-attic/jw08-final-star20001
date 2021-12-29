
姓名：刘睿哲

学号：202220008

[TOC]

## 开发目标

我写这个游戏是为了尝试实现课上学到的东西，但因为时间匆忙加自己拖延，所以就没有精进扩展，只为了完成基本内容和练习课上基本知识点。

## 设计理念

### 关于玩法

游戏思路：本次游戏设计主要是在给的框架和ui下进行修改扩展。我在creature里设计了boss类型的怪物，有更高的血量攻击，并且它的ai也和普通不同，会主动找玩家打架。游戏流程是清理完四个小怪后boss会出现和玩家决斗，从游戏开始到杀死boss通关所用的时间为玩家得分，用时越短越好。

玩家具备的特点：

* 可以自由移动以及攻击
* 可以查看已经猎杀的普通怪物数量

普通monster应当具备以下特点

* 可以攻击玩家，但会主动逃离玩家
* 血少攻低，基本碰了就死，所以他们一直在逃跑
* 杀死四个普通monster会召唤黄色boss

boss应当具备以下特点

* 可以攻击玩家
* 会主动接近玩家去猎杀
* 杀死boss后通关游戏


### 关于UI

UI我选用了jw04的默认UI，只是在怪物选择上面进行了颜色修改和图形选择

### 关于服务器

多人联机为了简便操作，设计成玩家自行单机游玩，在通关后的win界面可以和服务器联机，发送自己的分数，可以查看自己分数是否在服务器排名前10。写了一个服务器Server需要在打开游戏时候额外窗口打开，接受玩家得分数据并且写在txt里进行记录。


### 关于多线程

除了主线程，我还创建了以下线程：

* 用于刷新屏幕的线程，其每一秒刷新十次画面（经过试验，不影响输出效果）
* 玩家线程，其用于接收输入（移动和攻击），并更新玩家信息（hp和得分）
* 各个bot线程，每个线程对应一个bot，设计在了怪物的AI里

### 关于怪物AI

我在怪物的AI里设计了线程

在creatureAI里定义了run

```java
 @Override
    public void run() {
    }
```
然后在普通怪物和boss的ai里继承了creatureai，然后进行override

普通怪物的AI会进行随机走位

```java
@Override
    public void run(){
        while(this.creature.hp()>0){
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Random rand = new Random();
            switch(rand.nextInt(4)){
                case 0:
                    this.creature.moveBy(1,0);
                    break;
                case 1:
                    this.creature.moveBy(-1,0);
                    break;
                case 2:
                    this.creature.moveBy(0,1);
                    break;
                case 3:
                    this.creature.moveBy(0,-1);
                    break;
            }
        }
    }
```

boss的ai会主动找寻玩家

```java
 @Override
    public void run(){
        while(this.creature.hp()>0){
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int dx, dy;
            dx = getdirection(this.creature.x(), player.x());
            dy = getdirection(this.creature.y(), player.y());
            if (dx == 0 || dy == 0) {
                this.creature.moveBy(dx, dy);
            }
            else {
                Random rand = new Random();
                switch (rand.nextInt(2)) {
                case 0:
                    this.creature.moveBy(dx, 0);
                    break;
                case 1:
                    this.creature.moveBy(0, dy);
                    break;
                }
            }
        }
    }
```

## 技术问题

### 关于线程和屏幕刷新

在主函数部分进行刷屏

```java
 public void keyTyped(KeyEvent e) {
    }
    public static void main(String[] args) {
        ApplicationMain app = new ApplicationMain();
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.setVisible(true);

        new Thread(
                ()->{
                        while (true){
                            try {
                                Thread.sleep(50);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            app.repaint();
                        }
                }
        ).start();
        
    }
```

根据jw04的模板，主函数调用一个screen类，一开始是startscreen，然后根据操作返回playscreen。在playscreen中，我调用所有怪物的AI进行更新。

```java
private  Creature[] creaturelist;
    private void createCreatures(CreatureFactory creatureFactory) {
        this.player = creatureFactory.newPlayer(this.messages);

        creaturelist = new Creature[4];
        for (int i = 0; i < 4; i++) {
            creaturelist[i] = creatureFactory.newMonster();
            Thread t = new Thread(creaturelist[i].getAI());
            t.start();
        }

        
    }
```

### 关于通信部分

在最后通关后进入winscreen，在里面我调用一个客户端client和服务器server进行沟通。

```java
private void getresult() {
        try {
            Client client = new Client(score);
            this.result = client.getResult();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }
```

client发送现在得分，之后接受server的返回，会返回自己是否是top10中的一个。

```java
public Client(long score) throws IOException {
        String s = String.valueOf(score);
        byte[] data = s.getBytes();
        InetAddress add = InetAddress.getByName("localhost");
        int port = 8800;
        DatagramPacket packet = new DatagramPacket(data, data.length, add, port);
        DatagramSocket socket = new DatagramSocket();
        socket.send(packet);
        byte[] data2 = new byte[100];
        DatagramPacket packet2 = new DatagramPacket(data2, data2.length);
        System.out.println("Waiting");
        socket.receive(packet2);
        result = new String(data2, 0, packet2.getLength());
        System.out.println("Receive");
        socket.close();
    }
```

## 工程问题

无

## 一点感受

上述的过程看起来并不是十分复杂，但是我却花费了大量的时间来实现。感觉自己已经尽可能的简化要求，只想搞个简单的玩意交差，但发现即使再简单也有很多小坑等着我。最后debug了好久，也对课堂知识印象更加深刻。也不说以后是否有空会完善吧，我想应该是没有了，不过我以后一定会经常想起java作业这个，搞了好久才搞通的毫无难度但又很繁琐的小玩意，会让我更努力的去写任何程序，即使第一眼看起来很简答。




