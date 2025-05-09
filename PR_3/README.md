# Практическое задание №3

## Введение

Данная работа была выполнена с функционалом и элементами дизайна, не предусмотренными в практическом задании для дополнительного изучения функционала среды разработки Android Studio. Для правильной эксплуатации приложения рекомендуется ознакомиться с данным руководством.

**ВАЖНО:** если изображения загружаются некорректно, необходимо загрузить файл и открыть его в другом визуализаторе.

## Главная страница

Главная страница расположена в модуле App и представляет собой макет ConstraintLayout, состоящий из нескольких элементов - текстовых полей, кнопок и типа отображения элементов. На экране представлены описания заданий и кнопки, ведущие на их решение. Чтобы вернуться обратно на главную страницу, необходимо нажать на верхней панели эмулятора кнопку «назад» (◁).

![](./screens/1.png)

## Задание №1: Передача данных с помощью Intent

В рамках выполнения данного задания был создан модуль IntentApp и в нем были реализованы 2 активности. В первую передаётся дата и время устройства и задаётся число - номер по списку. Затем при создании первой активности, сразу открывается вторая и выводит на экран квадрат номера по списку, текущую дату с временем устройства.

![](./screens/2.png)

Если нажать на кнопку «назад», можно вернуться в первую активность и увидеть только текущую дату с временем устройства.

![](./screens/3.png)

## Задание №2: Обмен данными с другими приложениями

В рамках выполнения данного задания был создан модуль Sharer, который сразу предлагает поделиться информацией с другими приложениями.

![](./screens/4.png)

Если нажать на кнопку «назад», то можно вернуться в первую активность и увидеть текст, который сразу и не заметишь при запуске модуля Sharer.

![](./screens/5.png)

## Задание №3: Получение данных из другой активности

В рамках выполнения данного задания был создан модуль FavoriteBook, в котором определено текстовое поле и кнопка для отправки данных. 

![](./screens/6.png)

При нажатии на кнопку, открывается вторая активность, в которой есть 2 поля для ввода текста и кнопка для отправки данных обратно в первую активность.

![](./screens/7.png)

При нажатии на кнопку отправки данных, возвращаемся обратно в первую активность и получаем информацию о любимой книге и цитатах с учётом введённых данных.

![](./screens/8.png)

## Задание №4: Вызов системных приложений

В рамках выполнения данного задания был создан модуль SystemIntentsApp, в котором было определено 3 кнопки с различным функционалом - позвонить по указанному номеру, открыть в браузере заданный сайт и открыть карту с заданными координатами.

![](./screens/9.png)

При нажатии на кнопки выполняется функционал, который был задан каждой из них через обработку нажатий setOnClickListener.

![](./screens/10.png)

![](./screens/11.png)

![](./screens/12.png)

## Задание №5: Реализация фрагментов приложения

В рамках выполнения данного задания был создан модуль SimpleFragmentApp, в котором была определены 2 кнопки, которые при нажатии открывают соответствующие фрагменты. 

![](./screens/13.png)

Первая кнопка открывает первый фрагмент, вторая кнопка открывает второй соответственно

![](./screens/14.png)

![](./screens/15.png)

Однако, если экран повернуть в горизонтальное положение, кнопки пропадут и оба фрагмента будут отображены на одном экране.

![](./screens/16.png)

## Задание №6: Контрольное задание

В рамках выполнения данного задания был создан модуль MireaProject по шаблону Navigation Drawer Activity. Внутри модуля было добавлено 2 фрагмента: DataFragment (с уникальной информацией) и WebViewFragment (со страницей браузера).

![](./screens/17.png)

![](./screens/18.png)

При открытии фрагментов из меню «гамбургера», можно ознакомиться с информацией о требованиях Material You и открыть страницу РТУ МИРЭА внутри приложения.

![](./screens/19.png)

![](./screens/20.png)
