# SpannableString Study Repository

기존의 텍스트뷰는 하나의 스타일과 size 등등을 입힐 수 있었다면 SpannableString은 하나의 텍스트가 여러 유형의 스타일이 적용된 형태로 보여지는 텍스트입니다.



## 구현
TextView에는 보통  style, size, text, color 등의 설정을 하며 SpannableString에는 Image까지 입력할 수 있으므로 
총 5가지의 요소를 설계에 넣었습니다. 또한 SpannableTextProvider라는 object를 생성하여 원하는 곳에서 호출할 수 있도록 하였습니다.

![SpannableString Example](image/SpannableString_Example.png)


## 문제점

text의 요소들은 바로 적용이 되어 문제가 없으나 image가 제일 큰 문제였습니다. 기존의 drawable이 아닌 url을 통해 bitmap을 만들어야만
imagespan을 만들 수 있었는데 bitmap을 만드는 부분을 비동기 처리하여 이 때 충분한 시간이 있다면 문제가 없지만 
다른 부분에서 Provider를 호출하는 경우 비동기 처리되는 시간과 Provider가 호출되는 시간이 겹쳐서 처리가 잘 되지 않는 것을 확인할 수 있었습니다.