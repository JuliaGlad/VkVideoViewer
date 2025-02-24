## Vk Video Viewer
<img src =https://psv4.userapi.com/s/v1/d/zMtNZrF7E5_6CV-4WyQcDo6-G11XH09FReEipQ_KW5z7UNkx-oQbkfYUJJ6b_NZap9cE8E3n6ry03Gf_uTvHPBTp9fzhsFxA8Lf86Jg_6WyIdx_SDZP6KA/VVV_ICON.png>

!!ВНИМАНИЕ!! Pixabay API не работает без VPN.

Было очень трудно найти бесплатное API, соответстующее ТЗ задания, удалась найти Pixabay API, но оно требует VPN для корректного выполнения запросов

Дизайн приложения в Figma - https://www.figma.com/design/ePx3VPRF65dtxeklcFcIS2/VkVideoViewer?node-id=0-1&t=3ptgk3CSG10NAnRg-1

Приложение для просмотра видео с Pixabay API.

- Подгружается список видео, у каждого видео отображается миниатюра, назание, продолжительность, количество просмотров и скачиваний. При пролистывании страницы загружаются новые видео
  
- Можно "потянуть вниз" для обновления списка из видео с помощью SwipeRefreshLayout
  
- Реализован поиск по видео

<img src = https://psv4.userapi.com/s/v1/d/1rAQYPeOJ8jugfhCxPSd6Z4uVA3HomUB8coCVz0nPDNE1H2FYvA8YMDkby479RZ8b45ToGUuQHBEfacvTPNtBujhPoPRW4Dm7srEkXZa5EMZKUxUABw_-A/video_list_screenshots.png>
  
- По клику на элемент с видео происходит переходит на экран с медиа плеером для просмотра видео. На экране отображается проигрыватель с видео и список с рекомендуемыми видео для просмотра
  
- Реализован кастомный контроллер для ExoPlayer, с помощью которого можно останавливать и воспроизводить видео, изменять качество и скорость видео, а также переходить в полноэкранный режим
  
- При перевороте экрана с медиа плеером происходит переход в режим полноэкранного просмотра видео
  
- По клику на элемент из списка рекомендованных видео открывается плееер для просмотра выбранного ролика

 <img src = https://psv4.userapi.com/s/v1/d/jo8G4gRQF4r3xLBQ9VEuYwOOkTf9UtdlH0ykBOprsz2sQDN5a4N4dihGgz3e3_w7pcRKg7MKEm9IPSYCjEK_pxooAQg5sV5sCceElZCvdH03aEFEsT1dFg/video_player_screenshots.png>
  
- Реализован BottomNavigation с возможностью также просматривать красивые картинки с этого сайта. Картинки разделены на категории, которые представлены в виде табов
  
- По клику на картинку открывается экран, на котором можно посмотреть и приблизить выбранное изображение в высоком качестве

<img src = https://psv4.userapi.com/s/v1/d/XdwMREWYMiVtZ3CqpsHrDCW8_nwpE0E_qAjy9wCYEsxFFO1BnRMHUIxZ-3ysLmdL2WOETBar3m877oVWL1cXb4GsGBq8r3nhLWbMnmUXEv0Im3SwXKgshA/images_screenshots.png>

  
