# Super Type Token
- Java의 Generic Type Parameter를 사용하는 경우
  - Generic은 타입 정보를 동적으로 변경하여 사용할 수 있도록 만들어진 것.
- (참고) default 생성자가 없는 경우 reflection을 이용하여 인스턴스를 생성할 수 없다.

### Generic Class
- 타입 파라미터를 가지는 클래스로 인스턴스 생성 시점에 실제 파라미터 타입을 정하여 클래스 내에 일괄 적용 가능함 (멤버 변수, 제네릭 메소드 등등)

### Generic Parameter Type
- Type Erasure 방식에 의해서 런타임시에는 Object 타입으로 변경되어 지워짐 
  - 컴파일러가 캐스팅 코드를 넣어주는 방식으로 구현됨
- Generic 은 컴파일 타임에 제한을 두어 타입 안정성을 최대한 제공하려는 목적을 가짐
  - 다만 런타임에 타입에 관련된 추가적인 정보가 필요한 Use case가 생길 수 있음

### Type Token
- 특정 클래스 타입 정보를 넘겨서 타입 안정성을 꾀하는 것을 Type Token이라고 한다. 타입 정보를 값으로 해서 넘기겠다는 뜻.
- Type Token 이라는 타입정보를 Generic Parameter로 사용하여 Type 체크 및 제한을 걸 수 잇는 기법임 (널리 쓰임)

### Type Token의 한계?
- Generic 정보가 없는 리터럴 형태인 경우는 유용하다.
- 클래스 리터럴으로는 타입 파라미터를 가져올 수 있는 방법이 없다. (ex. List 같은 녀석들)
- Java5 에 제네릭을 도입하면서 안정성을 꾀했지만 기존에 타입 토큰을 사용하는 곳에서는 사용할 수가 없엇던 것이다. (ex. Spring RestTemplate)

### Super Type Token?
- 상속을 통해서 (ex. 익명 클래스의 인스턴스로 만들어서) 인스턴스의 있는 슈퍼 클래스의 제네릭 타입 파타리터 정보를 전달하기 위한 용도

### Spring 의 ParameterizedTypeReference
- 3.2 이상부터 지원하는 스프링의 슈퍼 타입 토큰 기능 지원 클래스

### Spring RestTemplate
- 3.2 부터 RestTemplate 의 exchange 메소드에서 ParameterizedTypeReference 로 받을 수가 있다. 

# Spring Resolvable Type
- 4.0 에 추가된 것으로 reflection 의 Type을 추상화 시켜 손쉽게 접근할 수 있도록 만든 것. 
