# SpannableString Study Repository

기존의 텍스트뷰는 하나의 스타일과 size 등등을 입힐 수 있었다면 SpannableString은 하나의 텍스트가 여러 유형의 스타일이 적용된 형태로 보여지는 텍스트입니다.



## 구현
TextView에는 보통  style, size, text, color 등의 설정을 하며 SpannableString에는 Image까지 입력할 수 있으므로 
총 5가지의 요소를 설계에 넣었습니다. 또한 SpannableTextProvider라는 object를 생성하여 원하는 곳에서 호출할 수 있도록 하였습니다.

![SpannableString Example](image/SpannableString_Example.png)


