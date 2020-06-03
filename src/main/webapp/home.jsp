<%--
  Created by IntelliJ IDEA.
  User: moroz
  Date: 28.03.2020
  Time: 21:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>$Title$</title>
    <style>
      @import url("style.css");
      .rightimg  {
        float: right; /* Выравнивание по правому краю  */
        margin: 7px 0 7px 7px; /* Отступы вокруг картинки */
      }
      body {
        background: #c7b39b url(background/mainBackground.jpg); /* Цвет фона и путь к файлу */
        color: #fff; /* Цвет текста */
      }
      .descr { visibility: hidden; }
      .b:hover + .descr {
        visibility: visible;
      }
      .size{
        width: 130px;
        height: 50px;
      }
    </style>
  </head>
  <body>
  <div class="descr"><h3>Я дуже кохаю Марічку</h3></div>
  <div class="six" align="center"><h1 align="center">
    <span>Розклад міської електрички</span></h1></div>
  <a href="http://www.ntu.edu.ua/" target="_blank">
    <img style="padding-left: 50px; padding-top: -200px;" src="picturesAndPages/ntu_logo4.png" border="0" height="67" width="83" alt="logo"/>
  </a>
  <div>
    <img src="picturesAndPages/schema.jpg" alt="Схема" width="1062" height="530" class="rightimg"/>
  <p>
    <a href="picturesAndPages/darnitsya/darnitsya.jsp" target="_blank">
      <img src="picturesAndPages/darnitsya/darnitsya.png" alt="darnitsya" class="size"/>
    </a>
      <a href="picturesAndPages/livoberezhna/livoberejna.jsp" target="_blank">
        <img src="picturesAndPages/livoberezhna/livoberejna.png" alt="livoberejna" class="size"/>
      </a>
  </p>
    <p>
      <a href="picturesAndPages/troyeshchyna/troyeshchina.jsp" target="_blank">
        <img src="picturesAndPages/troyeshchyna/troyeshchina.png" alt="troyeshchina" class="size"/>
      </a>
      <a href="picturesAndPages/troyeshchyna2/troyeshchynaTwo.jsp" target="_blank">
        <img src="picturesAndPages/troyeshchyna2/troyeshchina2.png" alt="troyeshchina2" class="size"/>
      </a>
    </p>
    <p>
      <a href="picturesAndPages/pochayna/pochayna.jsp" target="_blank">
        <img src="picturesAndPages/pochayna/pochayna.png" alt="pochayna" class="size"/>
      </a>
      <a href="picturesAndPages/zenit/zenit.jsp" target="_blank">
        <img src="picturesAndPages/zenit/zenit.png" alt="zenit" class="size"/>
      </a>
    </p>
    <p>
      <a href="picturesAndPages/vyshgorodska/vyshgorodska.jsp" target="_blank">
        <img src="picturesAndPages/vyshgorodska/vyshgorodska.png" alt="vyshgorodska" class="size"/>
      </a>
      <a href="picturesAndPages/syrets/syrets.jsp" target="_blank">
        <img src="picturesAndPages/syrets/syrets.png" alt="syrets" class="size"/>
      </a>
    </p>
    <p>
      <a href="picturesAndPages/rubezhivskyy/rubezhivskyy.jsp" target="_blank">
        <img src="picturesAndPages/rubezhivskyy/rubezhivskyy.png" alt="rubezhivskyy" class="size"/>
      </a>
      <a href="picturesAndPages/borshchahivka/borshchagivka.jsp" target="_blank">
        <img src="picturesAndPages/borshchahivka/borshchagivka.png" alt="borshchagivka" class="size"/>
      </a>
    </p>
    <p>
      <a href="picturesAndPages/kievVolynskyy/kievVolynskyy.jsp" target="_blank">
        <img src="picturesAndPages/kievVolynskyy/kievVolynskyy.png" alt="kievVolynskyy" class="size"/>
      </a>
      <a href="karavayeviDachi/karavayeviDachi.jsp" target="_blank">
        <img src="karavayeviDachi/karavaeviDachi.png" alt="karavayeviDachi" class="size"/>
      </a>
    </p>
    <p>
      <a href="picturesAndPages/kievPasazhyrskyy/kievPasagirskyy.jsp" target="_blank">
        <img src="picturesAndPages/kievPasazhyrskyy/kievPassagirskyy.png" alt="kievPassagirskyy" class="size"/>
      </a>
      <a href="picturesAndPages/vydubichi/vydubichi.jsp" target="_blank">
        <img src="picturesAndPages/vydubichi/vydubichi.png" alt="vydubichi" class="size"/>
      </a>
    </p>
    <p>
      <a href="picturesAndPages/liviyBereh/liviyBereh.jsp" target="_blank">
        <img src="picturesAndPages/liviyBereh/liviyBereh.png" alt="liviyBereh" class="size"/>
      </a>
      <a href="picturesAndPages/darnitsya/darnitsya.jsp" target="_blank">
        <img src="picturesAndPages/darnitsya/darnitsya.png" alt="darnitsya" class="size"/>
      </a>
    </p>
  </div>
  </body>
</html>
