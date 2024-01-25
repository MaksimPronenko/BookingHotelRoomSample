# Проект BookingHotelRoomSample

Приложение является сегментом приложения условной туристической фирмы. Начало работы приложения предполагает, что ранее уже выбран конкретный отель.

На первом экране приложения пользователь видит полную информацию об отеле с изображениями, которые пролистываются горизонтально. Внизу экрана кнопка перехода к выбору номера.

Второй экран представляет собой вертикальный Recycler с информацией о комнатах. Каждый элемент с данными комнаты содержит горизонтальный Pager с фотографиями комнаты.

На каждом элементе есть кнопка выбора данной комнаты, по нажатию на которую происходит переход на экран бронирования.

На экране бронирования располагаются данные путёвки, ввод информации о заказчике (телефон и почта), а затем ввод данных о туристах.

Телефон вводится с помощью маски "+7 (***) ***-**-**". При вводе номера телефона числа подставляются в шаблон (ввод начинается без +7 или 8).

Почта проходит валидацию по стандартному шаблону.

Группа данных каждого туриста является элементом вертикального Recycler-а, причём количество туристов может увеличиваться, а данные по каждому могут свёртываться в строку.

Внизу экрана отображается информация о ценах, которая зависит от количества туристов, и кнопка оплаты. Оплата возможна при заполнении всех данных.

Если какая-то информация отсутствует или не прошла валидацию, выводится сообщение и поля подсвечиваются красным.

Если все данные введены корректно, то при нажатии на кнопку оплаты происходит переход на экран подтверждения заказа.

Разработал на основе технического задания и дизайн-макета в Figma:
https://www.figma.com/file/MF3rG9GQdSkL8cVaGSpclJ/Android?type=design&node-id=0-1&mode=design&t=coTiOzG81xY1KGMB-0.

Использованные в проекте технологии:

- Coroutines;
- паттерн MVVM;
- Hilt;
- Navigation;
- Retrofit;
- JSON;
- Glide.

<img src="https://github.com/MaksimPronenko/BookingHotelRoomSample/assets/105295402/0ca69cb8-f9a0-481d-8049-ba0ae56f18cc" width="270" height="585">
<img src="https://github.com/MaksimPronenko/BookingHotelRoomSample/assets/105295402/a762a9b8-744e-4ad1-9006-478857f6b93e" width="270" height="585">
<img src="https://github.com/MaksimPronenko/BookingHotelRoomSample/assets/105295402/19ec2079-9735-4a1b-96c6-14fcbe1dbc48" width="270" height="585">
<img src="https://github.com/MaksimPronenko/BookingHotelRoomSample/assets/105295402/f94dbb94-2bbd-4f1d-ba7d-3857694ea000" width="270" height="585">
<img src="https://github.com/MaksimPronenko/BookingHotelRoomSample/assets/105295402/1541319a-63f0-447c-bd0b-0c9914183a4a" width="270" height="585">
<img src="https://github.com/MaksimPronenko/BookingHotelRoomSample/assets/105295402/dbb2d70e-c55e-4857-97a8-103a1e81b235" width="270" height="585">
<img src="https://github.com/MaksimPronenko/BookingHotelRoomSample/assets/105295402/66155af0-723b-462f-8417-b0361773560d" width="270" height="585">
<img src="https://github.com/MaksimPronenko/BookingHotelRoomSample/assets/105295402/12dd9b4e-57a2-409d-99b9-890752a1ba5f" width="270" height="585">
<img src="https://github.com/MaksimPronenko/BookingHotelRoomSample/assets/105295402/2ceb4d6a-8c7f-455a-8d25-19bbcb3a9194" width="270" height="585">
<img src="https://github.com/MaksimPronenko/BookingHotelRoomSample/assets/105295402/7bf8604b-d02d-414e-bdb8-e6e1f80fb39b" width="270" height="585">
<img src="https://github.com/MaksimPronenko/BookingHotelRoomSample/assets/105295402/bbd8726c-7da6-415e-9eb3-f5298571f0b1" width="270" height="585">
