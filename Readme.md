# Java GUI Multi Robot
> งานนี้เป็นส่วนนึงของวิชา css226 OPERATING SYSTEMS

โดย source code ของโปรแกรมนี้ทั้งหมดจะอยู่ภายในโฟรเดอร์ src โดยภายใน โฟรเดอร์นี้จะประกอบด้วย package มากมายที่ใช้ในการทำงานภายในโปรแกรมนี้

### explain package in project
-   `comtroller` เป็น package สำหรับรวม class ที่ใช้ในการควบคุมการทำงานส่วนมากเช่น ควบคุมเม้าท์ (MouseInput.class) คีย์บอร์ด (MouseInput.class) หรือมุมกล้องต่าง (Camera.class)

-   `entity` เป็น package สำหรับการทำ model ในสำหรับใช้ใน array object ต่างๆ (MyPosition.class)

-   `mainGame` เป็น package สำหรับ รวม class หลักในการทำงาน หรือเป็น class ที่ใช้ในการ run game เป็นหลัก

-   `net` เป็น package สำหรับ การทำงานบน socket โดยจะมี server.class ไว้คอยทำงาน server และมี MainWindow.class ไว้คออยสำหรับทำงานในฝั่ง client

-   `res` เป็น package สำหรับรวม image file ในการใช้ภายในโปรแกรม 


### how to compile and run your code.
1. เมื่อเปิด project มาให้เข้าไปที่ `folder src` ที่รวม package ที่ใช้ในการทำงาน  
2. เลือก `package net` แล้วเลือก Run file `Server.class` เป็นไฟล์แรกเพื่อเปิด server
```java
class Server {
    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = new ServerSocket(9999);
        Server server = new Server(serverSocket);
        server.startServer();
    }
}
```

**output console**

<img alt="plot" src="./src/res/forReadMe/img_serverfileRun.png" height="80"/>

3. หลังจากนั้นใน package เดียวกันให้ Run file `MainWindow.class` เพื่อทำงานในส่วน Client 

```java
public class MainWindow {
    public static void main(String[] args) {
        new MainWindow("Main window", 800, 650);
    }
}
```


* 3.1 หลังจากนั้นจะขึ้นหน้าต่างเกมสำหรับคลิ๊กเพื่อเริ่มเกม

<img alt="plot" src="./src/res/forReadMe/img_startgame.png" width="300"/>


* 3.1 หลังจากนั้นจะขึ้นหน้าต่างเกมสำหรับคลิ๊กเพื่อเริ่มเกม

<img alt="plot" src="./src/res/forReadMe/img_startgame.png" width="300"/>












